package net.chrissearle.flickrvote.impl;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service("flickrService")
public class FlickrJFlickrService implements FlickrService {
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

/*    public Photographer authenticate(String frob) throws FlickrServiceException {
        try {
            AuthInterface authInterface = flickr.getAuthInterface();

            Auth auth = authInterface.getToken(frob);

            final Photographer photographer = new Photographer(null, auth.getToken(),
                    auth.getUser().getUsername(), auth.getUser().getRealName(), auth.getUser().getId());

            photographerService.persistUser(photographer);

            return photographer;
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        }
    }

    public User getUser(String username) throws FlickrServiceException {
        Photographer photographer = photographerService.getUser(username);

        try {
            if (photographer.getToken() != null) {
                AuthInterface authInterface = flickr.getAuthInterface();

                return authInterface.checkToken(photographer.getToken()).getUser();
            }

            return null;
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        }
    }

    public void echo() throws FlickrServiceException {
        try {
            TestInterface testInterface = flickr.getTestInterface();
            testInterface.echo(Collections.EMPTY_LIST);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Photo> searchForPhotosWithTag(String tag) throws FlickrServiceException {
        try {
            PhotosInterface photosInterface = flickr.getPhotosInterface();

            String[] tags = new String[1];
            tags[0] = tag;

            SearchParameters params = new SearchParameters();
            params.setTags(tags);

            // Now we call for each user - since the user objects are not correctly filled out.
            List<Photo> photos = photosInterface.search(params, 500, 1);

            Map<String, User> users = new HashMap<String, User>();

            PeopleInterface peopleInterface = flickr.getPeopleInterface();

            for (Photo p : photos) {
                String id = p.getOwner().getId();

                if (!users.containsKey(id)) {
                    users.put(id, peopleInterface.getInfo(id));
                }

                p.setOwner(users.get(id));
            }

            return photos;
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        }
    }*/
}
