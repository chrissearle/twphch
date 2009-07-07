package net.chrissearle.flickrvote.flickr;

import net.chrissearle.flickrvote.flickr.FlickrServiceException;

import java.net.URL;

public interface FlickrService {
    URL getLoginUrl() throws FlickrServiceException;

    /*
    Photographer authenticate(String frob) throws FlickrServiceException;

    User getUser(String username) throws FlickrServiceException;

    void echo() throws FlickrServiceException;

    List<Photo> searchForPhotosWithTag(String tag) throws FlickrServiceException;
    */
}
