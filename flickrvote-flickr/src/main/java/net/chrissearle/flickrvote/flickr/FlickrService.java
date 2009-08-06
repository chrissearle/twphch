package net.chrissearle.flickrvote.flickr;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FlickrService {
    URL getLoginUrl() throws FlickrServiceException;

    URL getLoginUrl(boolean write) throws FlickrServiceException;

    FlickrPhotographer authenticate(String frob) throws FlickrServiceException;

    FlickrPhotographer checkAuthenticate(String token) throws FlickrServiceException;

    List<FlickrImage> searchImagesByTag(String tag, Date earliestDate) throws FlickrServiceException;

    FlickrPhotographer getUserByFlickrId(String id);

    FlickrImage getImageByFlickrId(String id);

    void postForum(String title, String message);

    void postComment(String id, String comment);

    Map<String, String> checkSearch(String tag, Date earliestDate);
}
