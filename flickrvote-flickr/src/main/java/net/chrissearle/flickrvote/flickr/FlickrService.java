package net.chrissearle.flickrvote.flickr;

import java.net.URL;
import java.util.List;

public interface FlickrService {
    URL getLoginUrl() throws FlickrServiceException;

    FlickrAuth authenticate(String frob) throws FlickrServiceException;

    FlickrAuth checkAuthenticate(String token) throws FlickrServiceException;

    List<FlickrImage> searchImagesByTag(String tag) throws FlickrServiceException;

    FlickrAuth getUserByFlickrId(String id);

    FlickrImage getImageByFlickrId(String id);
}
