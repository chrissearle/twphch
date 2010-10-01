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

package net.chrissearle.flickrvote.web.stats;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.Comparators;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.*;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowPhotographerChartAction extends ActionSupport implements RequestAware, SessionAware {
    private String tag;
    private Boolean small = false;

    private List<DisplayImage> images;

    private Photographer photographerInfo;

    private List<DisplayChallengeSummary> challenges;

    private final Logger logger = Logger.getLogger(ShowPhotographerChartAction.class.getName());

    private Long height = 0L;

    @Autowired
    private transient PhotographyService photographyService;

    @Autowired
    private transient ChallengeService challengeService;

    private Map<String, Object> requestMap;
    private Map<String, Object> session;

    public String stats() throws Exception {
        initializePhotographerInfo();

        this.challenges = new ArrayList<DisplayChallengeSummary>();

        for (ChallengeSummary challengeSummary : challengeService.getChallengesByType(ChallengeType.CLOSED)) {
            challenges.add(new DisplayChallengeSummary(challengeSummary));
        }

        Collections.sort(this.challenges, new Comparator<Challenge>() {
            public int compare(Challenge o1, Challenge o2) {
                return o2.getChallengeTag().compareTo(o1.getChallengeTag());
            }
        });

        if (this.small) {
            this.height = (long) (18 * challenges.size()) + 20;
        } else {
            this.height = (long) (30 * challenges.size()) + 140;
        }

        this.images = new ArrayList<DisplayImage>();

        final Map<String, DisplayImage> map = new HashMap<String, DisplayImage>();

        for (ImageItem image : photographyService.getImagesForPhotographer(photographerInfo.getPhotographerId())) {
            map.put(image.getChallenge().getTag(), new DisplayImage(image));
        }

        for (Challenge challenge : this.challenges) {
            if (map.containsKey(challenge.getChallengeTag())) {
                images.add(map.get(challenge.getChallengeTag()));
            } else {
                images.add(null);
            }
        }

        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        requestMap.put("hidePhotographerChart", true);

        initializePhotographerInfo();

        return SUCCESS;
    }

    private void initializePhotographerInfo() {
        this.photographerInfo = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);
    }

    public Boolean getSmall() {
        return this.small;
    }

    public void setSmall(String small) {
        this.small = "TRUE".equalsIgnoreCase(small);
    }

    public Photographer getPhotographer() {
        return this.photographerInfo;
    }

    public List<DisplayImage> getImages() {
        return this.images;
    }

    public List<DisplayChallengeSummary> getChallenges() {
        return this.challenges;
    }

    public Long getPhotographerChartHeight() {
        return this.height;
    }

    @Override
    public void setRequest(Map<String, Object> stringObjectMap) {
        this.requestMap = stringObjectMap;
    }

    @Override
    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }
}
