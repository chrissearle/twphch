/*
 * Copyright 2009 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.ImageItemStatus;
import net.chrissearle.flickrvote.service.model.PhotographerItem;

import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * Interface PhotographyService provides photography/image business logic
 *
 * @author chris
 */
public interface PhotographyService {
    /**
     * Method getGoldWinners returns all images with first place.
     *
     * @return list of images with rank 1.
     */
    Set<ImageItem> getGoldWinners();

    /**
     * Method setAdministrator sets the administration flag of a user.
     *
     * @param id        user ID
     * @param adminFlag boolean
     */
    void setAdministrator(String id, Boolean adminFlag);

    /**
     * Method isAdministrator checks if administration flag is set.
     *
     * @param username - username of the user
     * @return Boolean
     */
    Boolean isAdministrator(String username);

    /**
     * Method retrieveAndStorePhotographer causes flickr to grab a photographer and will save/update the local db.
     *
     * @param id - flickr ID
     * @return the photographer retrieved. Null if none found.
     */
    PhotographerItem retrieveAndStorePhotographer(String id);

    /**
     * Method checkLoginAndStore causes flickr to check that a given login has succeeded. The photographer will be stored/updated.
     *
     * @param frob - the frob string from flickr.
     * @return the photographer
     */
    public PhotographerItem checkLoginAndStore(String frob);

    /**
     * Method getChallengeImages retrieves all images for a challenge,
     *
     * @param tag of type String
     * @return ChallengeItem
     */
    ChallengeItem getChallengeImages(String tag);

    /**
     * Method getLoginUrl returns the loginUrl for flickr authentication.
     *
     * @return the loginUrl for flickr authentication.
     */
    URL getLoginUrl();

    /**
     * Method retrieveAndStoreImage causes a flickr retrieval of the image (and photographer). Saves/updates to the local db.
     *
     * @param id  flickr ID of image
     * @param tag challenge tag
     * @return ImageItem - null if no image found
     */
    ImageItem retrieveAndStoreImage(String id, String tag);

    /**
     * Method setScore - sets the final vote count of an image.
     *
     * @param imageId of type String
     * @param score   of type Long
     */
    void setScore(String imageId, Long score);

    /**
     * Method getImagesForPhotographer retrieves all images for a given photographer
     *
     * @param id photographer id
     * @return Set<ImageItem>
     */
    Set<ImageItem> getImagesForPhotographer(String id);

    /**
     * Method freezeChallenge - takes the current challenge - performs a flickr search and stores the results locally.
     */
    void freezeChallenge();

    /**
     * Method setTwitter sets a photographer's twitter ID
     *
     * @param id      photograher ID
     * @param twitter twitter ID
     * @return PhotographerItem - the updated photographer
     */
    PhotographerItem setTwitter(String id, String twitter);

    /**
     * Method getPhotographers returns all photographers.
     *
     * @return all photographers.
     */
    List<PhotographerItem> getPhotographers();

    /**
     * Method findById finds a photographer based on photographer ID
     *
     * @param id photographer ID
     * @return PhotographerItem - null if none found.
     */
    PhotographerItem findById(String id);

    /**
     * Method checkSearch runs a search for the given challenge at flickr and checks for multiple entries, date errors etc.
     *
     * @param tag - tag of an existing challenge
     * @return Set<ImageItemStatus>
     */
    Set<ImageItemStatus> checkSearch(String tag);

    /**
     * Method checkTwitterExists checks to see if the given twitter ID exists at twitter.
     *
     * @param twitter of type String
     * @return boolean
     */
    boolean checkTwitterExists(String twitter);

    /**
     * Method findImageById finds an image based on id
     *
     * @param imageId of type String
     * @return ImageItem - null if none found.
     */
    ImageItem findImageById(String imageId);
}