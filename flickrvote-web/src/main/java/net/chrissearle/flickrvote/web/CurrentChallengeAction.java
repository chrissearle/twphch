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

package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.TagSearchService;
import net.chrissearle.flickrvote.service.model.*;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import net.chrissearle.flickrvote.web.model.ListControl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class CurrentChallengeAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 2961184426759693084L;

    private transient Logger log = Logger.getLogger(CurrentChallengeAction.class);

    @Autowired
    private transient ChallengeService challengeService;

    @Autowired
    private transient TagSearchService tagSearchService;

    private List<DisplayImage> images;

    private Challenge challenge = null;

    private ListControl listControl = new ListControl(false, false, false, false);

    @Autowired
    private transient PhotographyService photographyService;

    @Override
    public String execute() throws Exception {
        if (challenge == null) {
            return "noCurrentChallenge";
        }

        return SUCCESS;
    }

    public String executeRss() {
        return "rss";
    }

    public void prepare() throws Exception {
        ChallengeSummary challengeSummary = null;

        // Get a list of all open challenges
        Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.OPEN);

        // Currently the web layer is designed for one current challengeSummary only
        if (challenges.size() > 0) {
            challengeSummary = challenges.iterator().next();
        }

        if (log.isDebugEnabled()) {
            log.debug("Current challengeSummary " + challengeSummary);
        }

        if (challengeSummary != null) {
            challenge = new DisplayChallengeSummary(challengeSummary);

            ImageItems imageItems = tagSearchService.searchByTagAndDate(challengeSummary.getTag(), challengeSummary.getStartDate());

            ChallengeItem challengeItem = photographyService.getChallengeImages(challengeSummary.getTag());

            final List<ImageItem> imagesUniquePhotographer = imageItems.getImagesUniquePhotographer(challengeItem.getImages());
            
            images = new ArrayList<DisplayImage>(imagesUniquePhotographer.size());

            for (ImageItem image : imagesUniquePhotographer) {
                images.add(new DisplayImage(image));
            }

            Collections.sort(images, new Comparator<DisplayImage>() {
                public int compare(DisplayImage o1, DisplayImage o2) {
                    return o2.getPostedDate().compareTo(o1.getPostedDate());
                }
            });
        }
    }

    public List<DisplayImage> getDisplayImages() {
        return images;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public ListControl getListControl() {
        return listControl;
    }
}
