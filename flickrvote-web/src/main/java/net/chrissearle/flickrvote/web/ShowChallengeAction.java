package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowChallengeAction {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;

    private Logger logger = Logger.getLogger(ShowChallengeAction.class);

    private String challengeTag;

    private List<DisplayImage> images;

    private Challenge challenge;

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Challenge ID " + challengeTag);
        }

        ChallengeSummary challengeSummary = challengeService.getChallengeSummary(challengeTag);

        if (logger.isDebugEnabled()) {
            logger.debug("Challenge " + challengeSummary);
        }

        if (challengeSummary != null) {
            challenge = new DisplayChallengeSummary(challengeSummary);

            ChallengeItem challengeItem = photographyService.getChallengeImages(challengeSummary.getTag());

            images = new ArrayList<DisplayImage>(challengeItem.getImages().size());

            for (ImageItem image : challengeItem.getImages()) {
                images.add(new DisplayImage(image));
            }

            Collections.sort(images, new Comparator<DisplayImage>() {
                public int compare(DisplayImage o1, DisplayImage o2) {
                    return o2.getVoteCount().compareTo(o1.getVoteCount());
                }
            });
        }

        return ActionSupport.SUCCESS;
    }

    public void setChallengeTag(String challengeTag) {
        this.challengeTag = challengeTag;
    }

    public List<DisplayImage> getDisplayImages() {
        return images;
    }

    public Challenge getChallenge() {
        return challenge;
    }
}