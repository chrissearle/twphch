package net.chrissearle.flickrvote.service;

import com.aetrion.flickr.people.User;

import java.net.URL;
import java.util.List;

public interface FlickrService {
    URL getLoginUrl() throws FlickrServiceException;

    void authenticate(String username, String frob) throws FlickrServiceException;

    User getUser(String username) throws FlickrServiceException;

    void echo() throws FlickrServiceException;

    List searchForPhotosWithTag(String tag) throws FlickrServiceException;

    void testAddToken(String username, String token);
}
