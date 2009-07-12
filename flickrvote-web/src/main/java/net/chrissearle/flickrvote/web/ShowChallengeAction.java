package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowChallengeAction {
    @Autowired
    private ChallengeService challengeService;

    private Logger logger = Logger.getLogger(ShowChallengeAction.class);

    private String challengeTag;

    private ChallengeInfo challenge;

    private List<ImageInfo> images;

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Challenge ID " + challengeTag);
        }

        challenge = challengeService.getChallenge(getChallengeTag());

        if (logger.isDebugEnabled()) {
            logger.debug("Challenge " + challenge);
        }

        if (challenge != null) {
            images = challengeService.getImagesForChallenge(getChallengeTag());

            doRanking();
        }

        return ActionSupport.SUCCESS;
    }

    private void doRanking() {
        long rank = 0;
        long lastSeenValue = Long.MAX_VALUE;

        Collections.sort(images, new Comparator<ImageInfo>() {

            public int compare(ImageInfo o1, ImageInfo o2) {
                return o1.getFinalVoteCount().compareTo(o2.getFinalVoteCount());
            }
        });

        for (ImageInfo image : images) {
            if (image.getFinalVoteCount() < lastSeenValue) {
                lastSeenValue = image.getFinalVoteCount();
                rank++;
            }
            image.setRank(rank);
        }
    }

    public void setChallengeTag(String challengeTag) {
        this.challengeTag = challengeTag;
    }

    public String getChallengeTag() {
        return challengeTag;
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public List<ImageInfo> getImages() {
        return images;
    }
}