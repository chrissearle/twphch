package net.chrissearle.flickrvote.flickr;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.people.PeopleInterface;
import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.SearchParameters;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("flickrService")
public class FlickrJFlickrService implements FlickrService {
    private Logger logger = Logger.getLogger(FlickrJFlickrService.class);
    
    @Autowired
    protected Flickr flickr;

/*
    @Autowired
    private PhotographerService photographerService;
*/

    public URL getLoginUrl() throws FlickrServiceException {
        try {
            AuthInterface authInterface = flickr.getAuthInterface();

            String frob = authInterface.getFrob();

            return authInterface.buildAuthenticationUrl(Permission.READ, frob);
        } catch (MalformedURLException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        }
    }

    public FlickrAuth authenticate(String frob) throws FlickrServiceException {
        try {
            AuthInterface authInterface = flickr.getAuthInterface();

            Auth auth = authInterface.getToken(frob);

            return new FlickrAuth(auth.getUser().getId(), auth.getToken(),
                    auth.getUser().getUsername(), auth.getUser().getRealName());
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        }
    }

    public FlickrAuth checkAuthenticate(String token) throws FlickrServiceException {
        try {
            AuthInterface authInterface = flickr.getAuthInterface();

            Auth auth = authInterface.checkToken(token);

            return new FlickrAuth(auth.getUser().getId(), auth.getToken(),
                    auth.getUser().getUsername(), auth.getUser().getRealName());
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<FlickrImage> searchImagesByTag(String tag) throws FlickrServiceException {
        try {
            PhotosInterface photosInterface = flickr.getPhotosInterface();

            String[] tags = new String[1];
            tags[0] = new StringBuilder().append("#").append(tag).toString();

            SearchParameters params = new SearchParameters();
            params.setTags(tags);

            List<Photo> photos = photosInterface.search(params, 500, 1);

            List<FlickrImage> results = new ArrayList<FlickrImage>(photos.size());

            Set<String> seenPhotographers = new HashSet<String>();

            for (Photo photo : photos) {
                if (!seenPhotographers.contains(photo.getOwner().getId())) {
                    results.add(convertPhotoToFlickrImage(photo));
                    seenPhotographers.add(photo.getOwner().getId());
                }
            }

            return results;
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        }
    }

    public FlickrAuth getUserByFlickrId(String id) {
        try {
            User user = getUser(id);

            if (user != null) {
                return new FlickrAuth(id, null, user.getUsername(), user.getRealName());
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
                return convertPhotoToFlickrImage(photo);
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
        if (logger.isInfoEnabled()) {
            logger.info("Posting to forum TITLE: " + title + " TEXT: " + text);
        }
        // TODO post
    }

    public void postComment(String imageId, String comment) {
        if (logger.isInfoEnabled()) {
            logger.info("Posting to comment ID: " + imageId+ " COMMENT: " + comment);
        }
        // TODO post
    }

    private FlickrImage convertPhotoToFlickrImage(Photo photo) throws IOException, SAXException, FlickrException {
        User user = getUser(photo.getOwner().getId());

        String name = user.getRealName();

        if (name == null || "".equals(name)) {
            name = user.getUsername();
        }

        return new FlickrImage(photo.getId(), name, photo.getOwner().getId(), photo.getTitle(), photo.getUrl(), photo.getMediumUrl());
    }

    private User getUser(String id) throws IOException, SAXException, FlickrException {
        PeopleInterface peopleInterface = flickr.getPeopleInterface();

        return peopleInterface.getInfo(id);
    }
}
