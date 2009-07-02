package net.chrissearle.flickrvote.service.impl;

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
import com.aetrion.flickr.test.TestInterface;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.FlickrService;
import net.chrissearle.flickrvote.service.FlickrServiceException;
import net.chrissearle.flickrvote.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("flickrService")
public class InMemoryFlickrService implements FlickrService {
    @Autowired
    private Flickr flickr;

    @Autowired
    private PhotographerService photographerService;

    private Map<String, String> tokens = new HashMap<String, String>();

    public URL getLoginUrl() throws FlickrServiceException {
        try {
            AuthInterface authInterface = flickr.getAuthInterface();

            String frob = authInterface.getFrob();

            return authInterface.buildAuthenticationUrl(Permission.WRITE, frob);
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

    public Photographer authenticate(String frob) throws FlickrServiceException {
        try {
            AuthInterface authInterface = flickr.getAuthInterface();

            Auth auth = authInterface.getToken(frob);

            final Photographer photographer = new Photographer(null, auth.getToken(),
                    auth.getUser().getUsername(), auth.getUser().getRealName());

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

        try {
            if (tokens.containsKey(username)) {
                AuthInterface authInterface = flickr.getAuthInterface();

                return authInterface.checkToken(tokens.get(username)).getUser();
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

    public List searchForPhotosWithTag(String tag) throws FlickrServiceException {
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
    }

    public void testAddToken(String username, String token) {
        tokens.put(username, token);
    }
}
