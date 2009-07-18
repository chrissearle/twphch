package net.chrissearle.flickrvote.flickr;

import java.net.URL;
import java.util.Date;
import java.util.List;

public interface FlickrService {
    URL getLoginUrl() throws FlickrServiceException;

    FlickrAuth authenticate(String frob) throws FlickrServiceException;

    FlickrAuth checkAuthenticate(String token) throws FlickrServiceException;

    List<FlickrImage> searchImagesByTag(String tag) throws FlickrServiceException;

    FlickrAuth getUserByFlickrId(String id);

    FlickrImage getImageByFlickrId(String id);

    void postOpenVote(String tag, String name, Date endDate);

    void postNewChallenge(String tag, String name, Date votingOpenDate, Date endDate);

    void postResultsAndAddBadges(String tag, String name, List<FlickrImage> firstPlace, List<FlickrImage> secondPlace, List<FlickrImage> thirdPlace);
}
