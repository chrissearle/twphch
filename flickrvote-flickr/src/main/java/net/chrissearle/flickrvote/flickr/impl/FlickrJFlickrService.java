/*
 * Copyright 2009 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.flickr.impl;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.people.PeopleInterface;
import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.SearchParameters;
import com.aetrion.flickr.photos.comments.CommentsInterface;
import net.chrissearle.flickrvote.flickr.*;
import net.chrissearle.mail.SimpleMailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

@Service("flickrService")
public class FlickrJFlickrService implements FlickrService {
    private Logger logger = Logger.getLogger(FlickrJFlickrService.class);

    protected Flickr flickr;

    protected String adminAuthToken;
    protected Boolean adminActiveFlag;

    private SimpleMailService mailService;

    @Autowired
    public FlickrJFlickrService(Flickr flickr, FlickrAdminAuthTokenHolder tokenHolder, SimpleMailService mailService) {
        this.flickr = flickr;
        this.adminAuthToken = tokenHolder.getAdminAuthToken();
        this.adminActiveFlag = tokenHolder.isActiveFlag();
        this.mailService = mailService;
    }

    protected FlickrJFlickrService() {
    }

    private Auth getAuthByToken(String token) throws IOException, SAXException, FlickrException {
        AuthInterface authInterface = flickr.getAuthInterface();

        return authInterface.checkToken(token);
    }

    public List<FlickrImage> searchImagesByTag(String tag, Date earliestDate) throws FlickrServiceException {
        try {
            List<Photo> photos = retrieveByTag(tag);

            Map<String, FlickrImage> seenPhotographers = new HashMap<String, FlickrImage>();

            for (Photo photo : photos) {
                FlickrImage image = convertPhotoToFlickrImage(photo);

                if (logger.isDebugEnabled()) {
                    logger.debug("Search result: " + image);
                }

                if (earliestDate != null && image.getTakenDate() != null && image.getTakenDate().getTime() < earliestDate.getTime()) {
                    if (logger.isInfoEnabled()) {
                        logger.info("Image was taken before challenge start: " + image.getFlickrId() + " " + image.getTakenDate());
                    }
                } else {
                    if (!seenPhotographers.containsKey(photo.getOwner().getId())) {
                        if (logger.isInfoEnabled()) {
                            logger.info("Image seen " + image.getFlickrId());
                        }
                        seenPhotographers.put(photo.getOwner().getId(), image);
                    } else {
                        if (logger.isInfoEnabled()) {
                            logger.info("Not replacing image " + seenPhotographers.get(photo.getOwner().getId()).getFlickrId() + " with " + image.getFlickrId());
                        }
                    }
                }
            }

            return new ArrayList<FlickrImage>(seenPhotographers.values());
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Photo> retrieveByTag(String tag) throws IOException, SAXException, FlickrException {
        PhotosInterface photosInterface = flickr.getPhotosInterface();

        String[] tags = new String[1];
        tags[0] = new StringBuilder().append("#").append(tag).toString();

        SearchParameters params = new SearchParameters();
        params.setTags(tags);

        // By grabbing the dates at this point - saves us a call to get image.
        // We don't bother asking for names here to save a call to get photographer since that is
        // still needed to get the user icon/avatar.
        params.setExtrasDateUpload(true);
        params.setExtrasDateTaken(true);

        List<Photo> photos = (List<Photo>) photosInterface.search(params, 500, 1);
        return photos;
    }

    public FlickrPhotographer getUserByFlickrId(String id) {
        try {
            User user = getUser(id);

            if (user != null) {
                FlickrPhotographer flickrPhotographer = new FlickrPhotographer(id, null, user.getUsername(), user.getRealName(), user.getBuddyIconUrl());

                if (logger.isInfoEnabled()) {
                    logger.info("Photographer retrieved " + flickrPhotographer.getUsername());
                }

                return flickrPhotographer;
            }

            return null;
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        }
    }

    public FlickrImage getImageByFlickrId(String id) {
        PhotosInterface photosInterface = flickr.getPhotosInterface();

        try {
            Photo photo = photosInterface.getPhoto(id);

            if (photo != null) {
                FlickrImage flickrImage = convertPhotoToFlickrImage(photo);

                if (logger.isInfoEnabled()) {
                    logger.info("Image retrieved " + flickrImage.getFlickrId());
                }

                return flickrImage;
            }

            return null;
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        }
    }

    public void postForum(String title, String text) {
        if (logger.isDebugEnabled()) {
            logger.debug("Posting to forum TITLE: " + title + " TEXT: " + text);
        }

        // The flickr API does not support group discussion posts. So all we can do is mail it.
        mailService.sendPost(title, text);
    }

    public void postComment(String imageId, String comment) {
        if (logger.isDebugEnabled()) {
            logger.debug("Posting comment check: " + adminActiveFlag);
        }

        if (adminActiveFlag) {
            if (logger.isDebugEnabled()) {
                logger.debug("Posting to comment ID: " + imageId + " COMMENT: " + comment);
            }

            if (logger.isInfoEnabled()) {
                logger.info("Image commented: " + imageId);
            }

            CommentsInterface commentsInterface = flickr.getCommentsInterface();

            RequestContext context = RequestContext.getRequestContext();

            try {
                context.setAuth(getAuthByToken(adminAuthToken));

                commentsInterface.addComment(imageId, comment);
            } catch (IOException e) {
                throw new FlickrServiceException(e);
            } catch (SAXException e) {
                throw new FlickrServiceException(e);
            } catch (FlickrException e) {
                throw new FlickrServiceException(e);
            }
        }

    }

    public Set<FlickrImageStatus> checkSearch(String tag, Date earliestDate) {
        try {
            List<Photo> photos = retrieveByTag(tag);

            Set<FlickrImageStatus> issues = new HashSet<FlickrImageStatus>();

            Map<String, FlickrImageStatus> seenPhotographers = new HashMap<String, FlickrImageStatus>();

            for (Photo photo : photos) {
                FlickrImage image = convertPhotoToFlickrImage(photo);
                FlickrPhotographer photographer = image.getPhotographer();

                if (earliestDate != null && image.getTakenDate() != null && image.getTakenDate().getTime() < earliestDate.getTime()) {

                    FlickrImageStatus status = new FlickrImageStatus(FlickrImageStatus.ImageStatus.TAKEN_DATE,
                            photographer, image);
                    issues.add(status);
                } else {
                    if (!seenPhotographers.containsKey(photo.getOwner().getId())) {
                        FlickrImageStatus status = new FlickrImageStatus(FlickrImageStatus.ImageStatus.MULTIPLE_IMAGES,
                                photographer, image);

                        seenPhotographers.put(photo.getOwner().getId(), status);
                    } else {
                        seenPhotographers.get(photo.getOwner().getId()).addImage(image);
                    }
                }
            }

            for (FlickrImageStatus status : seenPhotographers.values()) {
                if (status.getImageCount() > 1) {
                    issues.add(status);
                }
            }

            return issues;
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        }
    }

    private FlickrImage convertPhotoToFlickrImage(Photo photo) throws IOException, SAXException, FlickrException {
        User user = getUser(photo.getOwner().getId());

        FlickrPhotographer photographer = new FlickrPhotographer(user.getId(), null, user.getUsername(), user.getRealName(), user.getBuddyIconUrl());

        return new FlickrImage(photo.getId(), photographer, photo.getTitle(), photo.getUrl(), photo.getMediumUrl(), photo.getDateTaken(), photo.getDatePosted());
    }

    private User getUser(String id) throws IOException, SAXException, FlickrException {
        PeopleInterface peopleInterface = flickr.getPeopleInterface();

        User user = peopleInterface.getInfo(id);

        if (logger.isInfoEnabled()) {
            logger.info("User info fetched for " + user.getUsername());
        }

        return user;
    }
}
