package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.PhotographerItem;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PhotographyService {
    Set<ImageItem> getGoldWinners();

    void setAdministrator(String id, Boolean adminFlag);

    Boolean isAdministrator(String username);

    PhotographerItem retrieveAndStorePhotographer(String id);

    public PhotographerItem checkLoginAndStore(String frob);

    ChallengeItem getChallengeImages(String tag);

    URL getLoginUrl();

    ImageItem retrieveAndStoreImage(String id, String tag);

    void setScore(String imageId, Long score);

    Set<ImageItem> getImagesForPhotographer(String id);

    void freezeChallenge();

    PhotographerItem setTwitter(String id, String twitter);

    List<PhotographerItem> getPhotographers();

    PhotographerItem findById(String id);

    Map<String, String> checkSearch(String tag);

    boolean checkTwitterExists(String twitter);

    ImageItem findImageById(String imageId);
}