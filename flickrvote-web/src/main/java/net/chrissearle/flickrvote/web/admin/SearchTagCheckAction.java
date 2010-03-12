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

package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.StatusCheckService;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.ImageItemStatus;
import net.chrissearle.flickrvote.service.model.Status;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class SearchTagCheckAction extends ActionSupport {
    @Autowired
    private StatusCheckService statusCheckService;

    @Autowired
    private ChallengeService challengeService;

    private String tag;
    private Set<DisplayImage> takenDateIssues;
    private List<DisplayImage> multipleImageIssues;
    private Challenge challenge;

    @Override
    public String execute() throws Exception {
        challenge = new DisplayChallengeSummary(challengeService.getChallengeSummary(tag));

        Set<ImageItemStatus> searchIssues = statusCheckService.checkSearch(tag);

        takenDateIssues = new HashSet<DisplayImage>();
        multipleImageIssues = new ArrayList<DisplayImage>();

        for (ImageItemStatus status : searchIssues) {
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

        Collections.sort(multipleImageIssues, new Comparator<DisplayImage>() {
            public int compare(DisplayImage o1, DisplayImage o2) {
                return o1.getPhotographerId().compareTo(o2.getPhotographerId());
            }
        });

        return SUCCESS;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<DisplayImage> getMultipleImageIssues() {
        return multipleImageIssues;
    }

    public Set<DisplayImage> getTakenDateIssues() {
        return takenDateIssues;
    }

    public Challenge getChallenge() {
        return challenge;
    }
}
