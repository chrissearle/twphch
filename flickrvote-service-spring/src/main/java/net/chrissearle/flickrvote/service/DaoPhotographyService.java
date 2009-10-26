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

package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.dao.ImageDao;
import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.flickr.*;
import net.chrissearle.flickrvote.flickr.model.FlickrImage;
import net.chrissearle.flickrvote.flickr.model.FlickrImageStatus;
import net.chrissearle.flickrvote.flickr.model.FlickrImages;
import net.chrissearle.flickrvote.flickr.model.FlickrPhotographer;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.ChallengeState;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.ImageItemStatus;
import net.chrissearle.flickrvote.service.model.PhotographerItem;
import net.chrissearle.flickrvote.service.model.impl.ChallengeItemInstance;
import net.chrissearle.flickrvote.service.model.impl.ImageItemInstance;
import net.chrissearle.flickrvote.service.model.impl.ImageItemStatusInstance;
import net.chrissearle.flickrvote.service.model.impl.PhotographerItemInstance;
import net.chrissearle.flickrvote.twitter.TwitterService;
import net.chrissearle.flickrvote.twitter.TwitterServiceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Class DaoPhotographyService implements PhotographyService using the various DAO objects
 *
 * @author chris
 */
@Service("photographyService")
@Transactional
public class DaoPhotographyService implements PhotographyService {
    private Logger logger = Logger.getLogger(DaoPhotographyService.class);

    private final PhotographyDao photographyDao;
    private ChallengeDao challengeDao;
    private ImageDao imageDao;

    private ImageDAO flickrImageDao;
    private ImageTagSearchDAO flickrImageTagSearchDao;
    private UserDAO flickrUserDao;

    private FlickrService flickrService;
    private TwitterService twitterService;
    private FlickrLoginService flickrLoginService;

    /**
     * Constructor DaoPhotographyService creates a new DaoPhotographyService instance.
     *
     * @param photographyDao of type PhotographyDao
     * @param challengeDao   of type ChallengeDao
     * @param imageDao       of type ImageDao
     * @param flickrService  of type FlickrService
     * @param twitterService of type TwitterService
     */
    @Autowired
    public DaoPhotographyService(PhotographyDao photographyDao, ChallengeDao challengeDao, ImageDao imageDao,
                                 FlickrService flickrService, TwitterService twitterService, FlickrLoginService flickrLoginService,
                                 ImageDAO flickrImageDao, UserDAO flickrUserDao, ImageTagSearchDAO flickrImageTagSearchDao) {
        this.photographyDao = photographyDao;
        this.challengeDao = challengeDao;
        this.imageDao = imageDao;

        this.flickrService = flickrService;
        this.flickrLoginService = flickrLoginService;
        this.twitterService = twitterService;

        this.flickrImageDao = flickrImageDao;
        this.flickrImageTagSearchDao = flickrImageTagSearchDao;
        this.flickrUserDao = flickrUserDao;
    }

    /**
     * Method setAdministrator sets the administration flag of a user.
     *
     * @param id        user ID
     * @param adminFlag boolean
     */
    public void setAdministrator(String id, Boolean adminFlag) {
        Photographer photographer = photographyDao.findById(id);

        if (photographer != null) {
            photographer.setAdministrator(adminFlag);
            photographyDao.persist(photographer);
        }
    }

    /**
     * Method isAdministrator checks if administration flag is set.
     *
     * @param username - username of the user
     * @return Boolean
     */
    public Boolean isAdministrator(String username) {
        Photographer photographer = photographyDao.findByUsername(username);

        return photographer != null && photographer.isAdministrator();
    }

    /**
     * Method retrieveAndStorePhotographer causes flickr to grab a photographer and will save/update the local db.
     *
     * @param id - flickr ID
     * @return the photographer retrieved. Null if none found.
     */
    public PhotographerItem retrieveAndStorePhotographer(String id) {
        // Check to see if present
        Photographer photographer = photographyDao.findById(id);

        FlickrPhotographer flickrPhotographer = flickrUserDao.getUser(id);

        if (photographer == null) {
            photographer = new Photographer(flickrPhotographer.getToken(), flickrPhotographer.getUsername(),
                    flickrPhotographer.getRealname(), flickrPhotographer.getFlickrId(), flickrPhotographer.getIconUrl());
        } else {
            photographer.setUsername(flickrPhotographer.getUsername());
            photographer.setFullname(flickrPhotographer.getRealname());
            photographer.setIconUrl(flickrPhotographer.getIconUrl());
        }

        photographyDao.persist(photographer);

        return new PhotographerItemInstance(photographer);
    }

    /**
     * Method checkLoginAndStore causes flickr to check that a given login has succeeded. The photographer will be stored/updated.
     *
     * @param frob - the frob string from flickr.
     * @return the photographer
     */
    public PhotographerItem checkLoginAndStore(String frob) {
        FlickrPhotographer flickrPhotographer = flickrLoginService.authenticate(frob);

        // Check to see if present
        String flickrId = flickrPhotographer.getFlickrId();

        Photographer photographer = photographyDao.findById(flickrId);

        if (photographer == null) {
            photographer = new Photographer();

            photographer.setId(flickrId);
            photographer.setAdministrator(false);
        }

        photographer.setFullname(flickrPhotographer.getRealname());
        photographer.setUsername(flickrPhotographer.getUsername());
        photographer.setToken(flickrPhotographer.getToken());
        photographer.setIconUrl(flickrPhotographer.getIconUrl());

        photographyDao.persist(photographer);

        return new PhotographerItemInstance(photographer);
    }

    /**
     * Method getChallengeImages retrieves all images for a challenge,
     *
     * @param tag of type String
     * @return ChallengeItem
     */
    public ChallengeItem getChallengeImages(String tag) {
        Set<ImageItem> images = new HashSet<ImageItem>();

        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null) {
            return null;
        }

        Map<String, ImageItem> seenImages = new HashMap<String, ImageItem>();

        if (challenge.getVotingState() == ChallengeState.OPEN) {
            // Grab images from flickr
            FlickrImages flickrImages = flickrImageTagSearchDao.searchTag(tag, challenge.getStartDate());

            for (FlickrImage image : flickrImages.getImages()) {
                ImageItemInstance imageItem = new ImageItemInstance(image);
                images.add(imageItem);
                seenImages.put(image.getPhotographer().getFlickrId(), imageItem);
            }
        }

        // Grab images from db. Merge in if flickr images have already been fetched
        for (Image image : challenge.getImages()) {
            final String photographerId = image.getPhotographer().getId();

            if (seenImages.containsKey(photographerId)) {
                // The db has an image for a photographer that has been found by search.
                // This only happens when administrators add an image manually.
                // In this case - use the db image.
                images.remove(seenImages.get(photographerId));
            }

            images.add(new ImageItemInstance(image));
        }

        return new ChallengeItemInstance(challenge.getTag(), challenge.getName(), images);
    }

    /**
     * Method retrieveAndStoreImage causes a flickr retrieval of the image (and photographer). Saves/updates to the local db.
     *
     * @param id  flickr ID of image
     * @param tag challenge tag
     * @return ImageItem - null if no image found
     */
    public ImageItem retrieveAndStoreImage(String id, String tag) {
        return retrieveAndStoreImage(id, tag, true);
    }

    /**
     * Method retrieveAndStoreImage causes a flickr retrieval of the image (and photographer). Saves/updates to the local db.
     *
     * @param id              flickr ID of image
     * @param tag             challenge tag
     * @param replaceExisting - if true will remove an image if retrieving an image with the same photographer for the same challenge.
     * @return ImageItem - null if no image found
     */
    protected ImageItem retrieveAndStoreImage(String id, String tag, boolean replaceExisting) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null)
            return null;

        Image image = imageDao.findById(id);

        FlickrImage flickrImage = flickrImageDao.getImage(id);

        if (image == null) {
            if (logger.isInfoEnabled()) {
                logger.info("Storing image " + flickrImage.getFlickrId());
            }
            String photographerId = flickrImage.getPhotographer().getFlickrId();
            Photographer photographer = photographyDao.findById(photographerId);

            if (photographer == null) {
                retrieveAndStorePhotographer(photographerId);

                photographer = photographyDao.findById(photographerId);
            }

            Image existing = checkForExistingImage(photographer, tag);

            if (existing != null) {
                // Found an existing image for this challenge by this photographer
                if (replaceExisting) {
                    // Normal functionality - remove the existing one - we're doing an update
                    photographer.removeImage(existing);
                    imageDao.remove(existing);
                } else {
                    // Special case - e.g. we don't want to replace admin overridden images at freeze.
                    return new ImageItemInstance(existing);
                }
            }

            image = new Image();
            image.setId(flickrImage.getFlickrId());
            image.setMediumImage(flickrImage.getImageUrl());
            image.setPage(flickrImage.getUrl());
            image.setTitle(flickrImage.getTitle());
            image.setPostedDate(flickrImage.getPostedDate());

            photographer.addImage(image);

            photographyDao.persist(photographer);

            challenge.addImage(image);

            challengeDao.persist(challenge);
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("Refreshing image " + flickrImage.getFlickrId());
            }

            image.setTitle(flickrImage.getTitle());
            image.setPostedDate(flickrImage.getPostedDate());
            image.setPage(flickrImage.getUrl());
            image.setMediumImage(flickrImage.getImageUrl());

            imageDao.persist(image);
        }

        return new ImageItemInstance(image);
    }

    /**
     * Method checkForExistingImage checks to see if the photographer has an image for the given challenge
     *
     * @param photographer of type Photographer
     * @param tag          of type String
     * @return Image
     */
    private Image checkForExistingImage(Photographer photographer, String tag) {
        for (Image image : photographer.getImages()) {
            if (image.getChallenge().getTag().equals(tag)) {
                return image;
            }
        }
        return null;
    }

    /**
     * Method setScore - sets the final vote count of an image.
     *
     * @param imageId of type String
     * @param score   of type Long
     */
    public void setScore(String imageId, Long score) {
        Image image = imageDao.findById(imageId);

        if (image != null) {
            image.setFinalVoteCount(score);
        }

        imageDao.persist(image);
    }

    /**
     * Method getImagesForPhotographer retrieves all images for a given photographer
     *
     * @param id photographer id
     * @return Set<ImageItem>
     */
    public Set<ImageItem> getImagesForPhotographer(String id) {
        Photographer photographer = photographyDao.findById(id);

        if (photographer == null || photographer.getImages() == null || photographer.getImages().size() == 0) {
            return null;
        }

        Set<ImageItem> images = new HashSet<ImageItem>();

        for (Image image : photographer.getImages()) {
            images.add(new ImageItemInstance(image));
        }

        return images;
    }

    /**
     * Method freezeChallenge - takes the current challenge - performs a flickr search and stores the results locally.
     */
    public void freezeChallenge() {
        if (logger.isInfoEnabled()) {
            logger.info("Freezing challenge");
        }

        Challenge challenge = challengeDao.getVotingChallenge();

        if (challenge == null) {
            return;
        }

        if (logger.isInfoEnabled()) {
            logger.info("Freezing challenge : " + challenge);
        }

        FlickrImages images = flickrImageTagSearchDao.searchTag(challenge.getTag(), challenge.getStartDate());

        for (FlickrImage image : images.getImages()) {
            retrieveAndStoreImage(image.getFlickrId(), challenge.getTag(), false);
        }
    }

    /**
     * Method getGoldWinners returns all images with first place.
     *
     * @return list of images with rank 1.
     */
    public Set<ImageItem> getGoldWinners() {
        Set<ImageItem> images = new HashSet<ImageItem>();

        for (Image image : imageDao.getImagesWithRank(1L)) {
            images.add(new ImageItemInstance(image));
        }

        return images;
    }

    /**
     * Method setTwitter sets a photographer's twitter ID
     *
     * @param id      photograher ID
     * @param twitter twitter ID
     * @return PhotographerItem - the updated photographer
     */
    public PhotographerItem setTwitter(String id, String twitter) {
        Photographer photographer = photographyDao.findById(id);

        if (photographer != null) {
            photographer.setTwitter(twitter);

            photographyDao.persist(photographer);

            try {
                twitterService.follow(twitter);
            } catch (TwitterServiceException tse) {
                if (logger.isEnabledFor(Level.WARN)) {
                    logger.warn("Unable to follow" + tse.getMessage(), tse);
                }
            }

            return new PhotographerItemInstance(photographer);
        }

        return null;
    }

    /**
     * Method getPhotographers returns all photographers.
     *
     * @return all photographers.
     */
    public List<PhotographerItem> getPhotographers() {
        List<PhotographerItem> photographers = new ArrayList<PhotographerItem>();

        for (Photographer photographer : photographyDao.all()) {
            photographers.add(new PhotographerItemInstance(photographer));
        }

        return photographers;
    }

    /**
     * Method findById finds a photographer based on photographer ID
     *
     * @param id photographer ID
     * @return PhotographerItem - null if none found.
     */
    public PhotographerItem findById(String id) {
        Photographer photographer = photographyDao.findById(id);

        if (photographer != null) {
            return new PhotographerItemInstance(photographer);
        }

        return null;
    }

    /**
     * Method checkSearch runs a search for the given challenge at flickr and checks for multiple entries, date errors etc.
     *
     * @param tag - tag of an existing challenge
     * @return Set<ImageItemStatus>
     */
    public Set<ImageItemStatus> checkSearch(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        Set<ImageItemStatus> results = new HashSet<ImageItemStatus>();

        for (FlickrImageStatus status : flickrService.checkSearch(tag, challenge.getStartDate())) {
            results.add(new ImageItemStatusInstance(status));
        }

        return results;
    }

    /**
     * Method checkTwitterExists checks to see if the given twitter ID exists at twitter.
     *
     * @param twitter of type String
     * @return boolean
     */
    public boolean checkTwitterExists(String twitter) {
        return twitterService.twitterExists(twitter);
    }

    /**
     * Method findImageById finds an image based on id
     *
     * @param imageId of type String
     * @return ImageItem - null if none found.
     */
    public ImageItem findImageById(String imageId) {
        Image image = imageDao.findById(imageId);

        if (image != null) {
            return new ImageItemInstance(image);
        }

        return null;
    }

    /**
     * Method getChallengeImages retrieves all images for a challenge for the given photographer
     *
     * @param tag            of type String
     * @param photographerId of type String
     * @return ImageItem
     */
    public Set<ImageItem> getChallengeImages(String tag, String photographerId) {
        Set<ImageItem> images = new HashSet<ImageItem>();

        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null) {
            return null;
        }

        if (challenge.getVotingState() != ChallengeState.OPEN) {
            return null;
        }

        // Grab images from flickr
        FlickrImages flickrImages = flickrImageTagSearchDao.searchTag(tag, challenge.getStartDate());

        for (FlickrImage image : flickrImages.getImages()) {
            if (image.getPhotographer().getFlickrId().equals(photographerId)) {
                ImageItem imageItem = new ImageItemInstance(image);
                images.add(imageItem);
            }
        }

        return images;
    }
}
