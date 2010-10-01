/*
 * Copyright 2010 Chris Searle
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
import net.chrissearle.flickrvote.web.model.*;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrentChallengeAction extends ActionSupport implements Preparable, SessionAware {
    private static final long serialVersionUID = 2961184426759693084L;

    private final transient Logger log = Logger.getLogger(CurrentChallengeAction.class.getName());

    @Autowired
    private transient ChallengeService challengeService;

    @Autowired
    private transient TagSearchService tagSearchService;

    private List<Image> images = new ArrayList<Image>();

    private Challenge challenge = null;

    private ListControl listControl = new ListControl(false, false, false, false, false, true);

    private Image currentPhotographerImage = null;

    @Autowired
    private transient PhotographyService photographyService;

    private Map<String, Object> session;

    private Boolean votingClosedFlag = false;


    @Override
    public String execute() throws Exception {
        if (null == this.challenge) {
            return "noCurrentChallenge";
        }

        return SUCCESS;
    }

    public String executeRss() {
        return "rss";
    }

    public void prepare() throws Exception {
        final Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        ChallengeSummary challengeSummary = null;

        // Get a list of all open challenges
        final Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.OPEN);

        // Currently the web layer is designed for one current challengeSummary only
        if (0 < challenges.size()) {
            challengeSummary = challenges.iterator().next();
        }

        if (log.isLoggable(Level.FINE)) {
            log.fine("Current challengeSummary " + challengeSummary);
        }

        if (null != challengeSummary) {
            this.challenge = new DisplayChallengeSummary(challengeSummary);

            final ImageItems imageItems = tagSearchService.searchByTagAndDate(challengeSummary.getTag(), challengeSummary.getStartDate());

            final ChallengeItem challengeItem = photographyService.getChallengeImages(challengeSummary.getTag());

            final List<ImageItem> imagesUniquePhotographer = imageItems.getImagesUniquePhotographer(challengeItem.getImages());

            for (ImageItem image : imagesUniquePhotographer) {
                final DisplayImage displayImage = new DisplayImage(image);

                images.add(displayImage);

                if (null != photographer && displayImage.getPhotographerId().equals(photographer.getPhotographerId())) {
                    this.currentPhotographerImage = displayImage;
                }
            }

            Collections.sort(this.images, new Comparator<Image>() {
                public int compare(Image o1, Image o2) {
                    return o2.getPostedDate().compareTo(o1.getPostedDate());
                }
            });
        }
    }

    public List<Image> getDisplayImages() {
        return this.images;
    }

    public Challenge getChallenge() {
        return this.challenge;
    }

    public ListControl getListControl() {
        return this.listControl;
    }

    public Boolean isShowEntryBox() {
        return true;
    }

    @Override
    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;

        if (session.containsKey(FlickrVoteWebConstants.VOTING_CLOSED_FLAG)) {
            this.votingClosedFlag = (Boolean) session.get(FlickrVoteWebConstants.VOTING_CLOSED_FLAG);
            session.remove(FlickrVoteWebConstants.VOTING_CLOSED_FLAG);
        }
    }

    public Image getCurrentPhotographerImage() {
        return this.currentPhotographerImage;
    }

    public Boolean isVotingClosed() {
        return this.votingClosedFlag;
    }
}
