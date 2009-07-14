package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;

import java.net.URL;
import java.util.List;

public interface PhotographyService {
    void addPhotographer(String token, String username, String fullname, String flickrId);

    void setAdministrator(String username, Boolean adminFlag);

    Boolean isAdministrator(String username);

    void retrieveAndStorePhotographer(String id);

    public PhotographerInfo checkLoginAndStore(String frob);

    List<FlickrImage> searchImagesByTag(String tag);

    URL getLoginUrl();

    void retrieveAndStoreImage(String id, String tag);
}