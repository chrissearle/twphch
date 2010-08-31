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
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import net.chrissearle.flickrvote.web.model.Image;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowChallengeChartAction extends ActionSupport implements RequestAware {
    private String tag;
    private Boolean small = false;

    private List<Image> images;
    private Challenge challengeInfo;

    private Logger logger = Logger.getLogger(ShowChallengeChartAction.class.getName());

    @Autowired
    private transient PhotographyService photographyService;

    @Autowired
    private transient ChallengeService challengeService;
    private Map<String, Object> requestMap;

    public String stats() throws Exception {
        initializeChallengeInfo();

        ChallengeItem challenge = photographyService.getChallengeImages(tag);

        images = new ArrayList<Image>(challenge.getImages().size());

        for (ImageItem image : challenge.getImages()) {
            final DisplayImage displayImage = new DisplayImage(image);

            images.add(displayImage);

            if (logger.isLoggable(Level.INFO)) {
                logger.info("Saw " + displayImage.toString());
            }
        }

        Collections.sort(images, new Comparator<Image>() {
            public int compare(Image o1, Image o2) {
                return o1.getRank().compareTo(o2.getRank());
            }
        });

        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        requestMap.put("hideChallengeResults", true);
        
        initializeChallengeInfo();

        return SUCCESS;
    }

    private void initializeChallengeInfo() {
        ChallengeSummary challenge = challengeService.getChallengeSummary(tag);

        challengeInfo = new DisplayChallengeSummary(challenge);

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Saw " + challengeInfo.toString());
        }
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = "TRUE".equalsIgnoreCase(small);
    }

    public Challenge getChallenge() {
        return challengeInfo;
    }

    public List<Image> getImages() {
        return images;
    }

    @Override
    public void setRequest(Map<String, Object> stringObjectMap) {
        this.requestMap = stringObjectMap;
    }
}
