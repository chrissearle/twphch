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
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 110326130351611401L;

    private transient Logger logger = Logger.getLogger(ImageAction.class.getName());

    @Autowired
    private transient PhotographyService photographyService;

    @Autowired
    private transient ChallengeService challengeService;

    private List<String> id;

    private String tag;

    private List<Challenge> challenges;

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    @Override
    public String execute() throws Exception {
        for (String imageId : id) {
            if (!"".equals(imageId)) {
                try {
                    ImageItem image = photographyService.retrieveAndStoreImage(imageId, tag);

                    addActionMessage("&laquo;" + image.getTitle() + "&raquo; av " + image.getPhotographer().getName() + " retrieved/updated.");
                } catch (FlickrServiceException e) {
                    addActionError("Error retrieving image " + imageId + ": " + e.getMessage());
                }
            }
        }

        return SUCCESS;
    }

    @Override
    public void validate() {
        boolean seenId = false;

        for (String imageId : id) {
            if (!"".equals(imageId)) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("Seen an ID " + imageId);
                }

                seenId = true;
            }
        }

        if (!seenId) {
            addActionError("Flickr ID must be provided");
        }

    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void prepare() throws Exception {

        challenges = new ArrayList<Challenge>();

        for (ChallengeSummary challenge : challengeService.getChallengesByType(ChallengeType.ALL)) {
            challenges.add(new DisplayChallengeSummary(challenge));
        }

        Collections.sort(challenges, new Comparator<Challenge>() {
            public int compare(Challenge o1, Challenge o2) {
                return o2.getChallengeTag().compareTo(o1.getChallengeTag());
            }
        });
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }
}
