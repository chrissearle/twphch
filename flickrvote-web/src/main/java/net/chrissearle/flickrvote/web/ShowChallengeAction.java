package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
        }

        return ActionSupport.SUCCESS;
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