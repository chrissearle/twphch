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
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class VoteAction extends ActionSupport implements SessionAware, Preparable {
    Logger logger = Logger.getLogger(VoteAction.class);

    private Map<String, Object> session;

    List<String> votes;

    private Challenge challenge;

    private List<DisplayImage> images;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;

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

        if (logger.isDebugEnabled()) {
            logger.debug("Votes by: " + photographer.getPhotographerName() + " : " + votes);
        }

        for (String imageId : votes) {
            challengeService.vote(photographer.getPhotographerId(), imageId);
        }

        // FIXME i18n
        addActionMessage("Thankyou for your vote");

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
                                // FIXME i18n
                                addActionError("You may not vote for yourself");
                            }
                        }
                    }

                    if (seenPhotographer) {
                        voteCount--;
                    }
                }

                if (votes == null || votes.size() != voteCount) {
                    // FIXME i18n
                    addActionError("Incorrect number of votes - you must vote for " + voteCount + " photos");
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

        Set<ChallengeSummary> votingChallenges = challengeService.getChallengesByType(ChallengeType.VOTING);

        if (votingChallenges.size() > 0) {
            challenge = new DisplayChallengeSummary(votingChallenges.iterator().next());

            ChallengeItem challengeItem = photographyService.getChallengeImages(challenge.getChallengeTag());

            images = new ArrayList<DisplayImage>(challengeItem.getImages().size());

            for (ImageItem image : challengeItem.getImages()) {
                images.add(new DisplayImage(image));
            }

            Collections.sort(images, new Comparator<DisplayImage>() {
                public int compare(DisplayImage o1, DisplayImage o2) {
                    return o2.getPostedDate().compareTo(o1.getPostedDate());
                }
            });
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
}
