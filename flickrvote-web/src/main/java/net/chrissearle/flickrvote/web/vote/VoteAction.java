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

package net.chrissearle.flickrvote.web.vote;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.*;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class VoteAction extends ActionSupport implements SessionAware, Preparable {
    private static final long serialVersionUID = 3592153769459598022L;

    private transient Logger logger = Logger.getLogger(VoteAction.class);

    private Map<String, Object> session;

    List<String> votes;

    private Challenge challenge;

    private List<DisplayImage> images;

    private Boolean voted;

    @Autowired
    private transient ChallengeService challengeService;

    @Autowired
    private transient PhotographyService photographyService;

    public void setVotes(List<String> votes) {
        if (logger.isDebugEnabled()) {
            logger.debug("setVotes: " + votes);
        }

        this.votes = votes;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        if (logger.isDebugEnabled()) {
            logger.debug("setSession");
        }

        this.session = stringObjectMap;
    }

    @Override
    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("execute");
        }

        Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        if (logger.isInfoEnabled()) {
            logger.info("Votes by: " + photographer.getPhotographerName() + " [" + photographer.getPhotographerId() + "] : " + votes);
        }

        for (String imageId : votes) {
            challengeService.vote(photographer.getPhotographerId(), imageId);
        }

        addActionMessage(getText("vote.thanks"));

        return SUCCESS;
    }

    @Override
    public void validate() {
        if (logger.isDebugEnabled()) {
            logger.debug("validate");
        }

        Set<ChallengeSummary> votingChallenges = challengeService.getChallengesByType(ChallengeType.VOTING);

        int voteCount = 5;

        if (votingChallenges.size() > 0) {
            ChallengeSummary challenge = votingChallenges.iterator().next();

            ChallengeItem challengeItem = photographyService.getChallengeImages(challenge.getTag());

            if (challengeItem.getImages().size() > 0) {
                if (challengeItem.getImages().size() <= voteCount) {
                    voteCount = challengeItem.getImages().size();

                    boolean seenPhotographer = false;

                    Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

                    for (ImageItem image : challengeItem.getImages()) {
                        if (image.getPhotographer().getName().equals(photographer.getPhotographerName())) {
                            seenPhotographer = true;

                            if (votes != null && votes.contains(image.getId())) {
                                addActionError(getText("vote.self.vote"));
                            }
                        }
                    }

                    if (seenPhotographer) {
                        voteCount--;
                    }
                }

                if (votes == null || votes.size() != voteCount) {
                    String[] params = new String[1];
                    params[0] = "" + voteCount;

                    addActionError(getText("vote.vote.count", params));
                }
            }
        }
    }

    @Override
    public String input() {
        if (logger.isDebugEnabled()) {
            logger.debug("input");
        }

        if (session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

            if (challengeService.hasVoted(photographer.getPhotographerId())) {
                return "alreadyVoted";
            }
        }

        return INPUT;
    }

    public void prepare() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("prepare");
        }

        voted = false;

        if (session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

            voted = challengeService.hasVoted(photographer.getPhotographerId());

            if (logger.isDebugEnabled()) {
                logger.debug("Setting voted to " + voted);
            }
        }

        Set<ChallengeSummary> votingChallenges = challengeService.getChallengesByType(ChallengeType.VOTING);

        if (votingChallenges.size() > 0) {
            challenge = new DisplayChallengeSummary(votingChallenges.iterator().next());

            ChallengeItem challengeItem = photographyService.getChallengeImages(challenge.getChallengeTag());

            images = new ArrayList<DisplayImage>(challengeItem.getImages().size());

            for (ImageItem image : challengeItem.getImages()) {
                images.add(new DisplayImage(image));
            }

            if (!voted) {
                Collections.sort(images, new Comparator<DisplayImage>() {
                    public int compare(DisplayImage o1, DisplayImage o2) {
                        return o2.getPostedDate().compareTo(o1.getPostedDate());
                    }
                });
            } else {
                Collections.sort(images, new Comparator<DisplayImage>() {
                    public int compare(DisplayImage o1, DisplayImage o2) {
                        return o1.getVoteCount().equals(o2.getVoteCount())
                                ? o2.getPostedDate().compareTo(o1.getPostedDate())
                                : o2.getVoteCount().compareTo(o1.getVoteCount());
                    }
                });
            }
        }
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public List<DisplayImage> getDisplayImages() {
        return images;
    }

    public String browse() throws Exception {
        return "browse";
    }

    public Boolean isVoted() {
        return voted;
    }

    public ListControl getListControl() {
        return new ListControl(false,
                false,
                voted && session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY),
                false);
    }
}
