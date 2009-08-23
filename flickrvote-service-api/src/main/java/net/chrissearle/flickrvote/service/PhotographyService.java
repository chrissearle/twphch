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