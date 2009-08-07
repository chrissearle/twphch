package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;

import java.net.URL;
import java.util.List;
import java.util.Map;

public interface PhotographyService {
    void setAdministrator(String id, Boolean adminFlag);

    Boolean isAdministrator(String username);

    PhotographerInfo retrieveAndStorePhotographer(String id);

    public PhotographerInfo checkLoginAndStore(String frob);

    ChallengeItem getChallengeImages(String tag);

    @Deprecated
    List<FlickrImage> searchImagesByTag(String tag);

    URL getLoginUrl();

    ImageInfo retrieveAndStoreImage(String id, String tag);

    void setScore(String imageId, Long score);

    List<ImageInfo> getImagesForPhotographer(String id);

    void freezeChallenge();

    List<ImageInfo> getGoldWinners();

    void setTwitter(String id, String twitter);

    List<PhotographerInfo> getPhotographers();

    PhotographerInfo findById(String id);

    Map<String, String> checkSearch(String tag);

    boolean checkTwitterExists(String twitter);
}