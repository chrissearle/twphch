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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
            tags[0] = tag;

            SearchParameters params = new SearchParameters();
            params.setTags(tags);

            List<Photo> photos = photosInterface.search(params, 500, 1);

            List<FlickrImage> results = new ArrayList<FlickrImage>(photos.size());

            PeopleInterface peopleInterface = flickr.getPeopleInterface();

            for (Photo p : photos) {
                User user = peopleInterface.getInfo(p.getOwner().getId());

                String name = user.getRealName();

                if (name == null || "".equals(name)) {
                    name = user.getUsername();
                }

                results.add(new FlickrImage(p.getId(), name, p.getTitle(), p.getUrl(), p.getMediumUrl()));
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
}
