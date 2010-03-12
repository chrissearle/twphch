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

package net.chrissearle.flickrvote.web.account;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.StatusCheckService;
import net.chrissearle.flickrvote.service.model.*;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Class CheckImageAction checks the current user's image (if any) for issues
 *
 * @author chris
 *         Created on Aug 23, 2009
 */
public class CheckImageAction extends ActionSupport implements SessionAware {
    @Autowired
    private PhotographyService photographyService;

    @Autowired
    private StatusCheckService statusCheckService;

    @Autowired
    private ChallengeService challengeService;
    private Set<DisplayImage> takenDateIssues;
    private List<DisplayImage> multipleImageIssues;
    private Map<String, Object> session;
    private DisplayImage image = null;

    @Override
    public String execute() throws Exception {
        Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.OPEN);

        if (challenges.size() > 0) {
            String currentChallengeTag = challenges.iterator().next().getTag();

            Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

            Set<ImageItem> images = photographyService.getChallengeImages(currentChallengeTag, photographer.getPhotographerId());

            if (images.size() > 0) {
                image = new DisplayImage(images.iterator().next());
            }

            Set<ImageItemStatus> issues = statusCheckService.checkSearch(currentChallengeTag);

            takenDateIssues = new HashSet<DisplayImage>();
            multipleImageIssues = new ArrayList<DisplayImage>();

            for (ImageItemStatus status : issues) {
                if (status.getImages().size() > 0) {

                    String photographerId = status.getImages().iterator().next().getPhotographer().getId();

                    if (photographerId.equals(photographer.getPhotographerId())) {
                        if (status.getStatus() == Status.TAKEN_DATE_TOO_EARLY) {
                            for (ImageItem image : status.getImages()) {
                                takenDateIssues.add(new DisplayImage(image));
                            }
                        }
                        if (status.getStatus() == Status.MULTIPLE_IMAGE_BY_SAME_PHOTOGRAPHER) {
                            for (ImageItem image : status.getImages()) {
                                multipleImageIssues.add(new DisplayImage(image));
                            }

                        }
                    }
                }
            }
        }

        return SUCCESS;
    }

    /**
     * Method setSession sets the struts session for this action
     *
     * @param stringObjectMap the session map
     */

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public List<DisplayImage> getMultipleImageIssues() {
        return multipleImageIssues;
    }

    public Set<DisplayImage> getTakenDateIssues() {
        return takenDateIssues;
    }

    public DisplayImage getEnteredImage() {
        return image;
    }
}
