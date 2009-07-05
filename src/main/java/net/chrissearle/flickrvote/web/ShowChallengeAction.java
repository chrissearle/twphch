package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.FlickrService;
import net.chrissearle.flickrvote.web.model.ImageChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;

public class ShowChallengeAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private FlickrService flickrService;

    private Logger logger = Logger.getLogger(ShowChallengeAction.class);

    private Long challengeId;

    private Challenge challenge;

    private List<Image> images;

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Challenge ID " + challengeId);
        }

        challenge = challengeService.getChallenge(getChallengeId());

        if (logger.isDebugEnabled()) {
            logger.debug("Challenge " + challenge);
        }

        if (challenge != null) {
            images = challenge.getImages();
        }

        return SUCCESS;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public List<Image> getImages() {
        return images;
    }
}