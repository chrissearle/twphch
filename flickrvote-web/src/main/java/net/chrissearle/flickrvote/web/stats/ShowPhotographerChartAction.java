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

    private Logger logger = Logger.getLogger(ShowPhotographerChartAction.class.getName());

    private Long height = 0L;

    @Autowired
    private transient PhotographyService photographyService;

    @Autowired
    private transient ChallengeService challengeService;

    private Map<String, Object> requestMap;
    private Map<String, Object> session;

    public String stats() throws Exception {
        initializePhotographerInfo();

        challenges = new ArrayList<DisplayChallengeSummary>();

        for (ChallengeSummary challengeSummary : challengeService.getChallengesByType(ChallengeType.CLOSED)) {
            challenges.add(new DisplayChallengeSummary(challengeSummary));
        }

        Collections.sort(challenges, new Comparator<DisplayChallengeSummary>() {
            public int compare(DisplayChallengeSummary o1, DisplayChallengeSummary o2) {
                return o2.getChallengeTag().compareTo(o1.getChallengeTag());
            }
        });

        if (small) {
            height = (long) (18 * challenges.size()) + 20;
        } else {
            height = (long) (30 * challenges.size()) + 140;
        }

        images = new ArrayList<DisplayImage>();

        Map<String, DisplayImage> map = new HashMap<String, DisplayImage>();

        for (ImageItem image : photographyService.getImagesForPhotographer(photographerInfo.getPhotographerId())) {
            map.put(image.getChallenge().getTag(), new DisplayImage(image));
        }

        for (DisplayChallengeSummary challenge : challenges) {
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
        photographerInfo = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);
    }

    public Boolean getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = "TRUE".equalsIgnoreCase(small);
    }

    public Photographer getPhotographer() {
        return photographerInfo;
    }

    public List<DisplayImage> getImages() {
        return images;
    }

    public List<DisplayChallengeSummary> getChallenges() {
        return challenges;
    }

    public Long getPhotographerChartHeight() {
        return height;
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
