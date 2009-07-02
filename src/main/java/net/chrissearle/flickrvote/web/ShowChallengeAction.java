package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.FlickrService;
import net.chrissearle.flickrvote.web.model.ImageChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;

public class ShowChallengeAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private FlickrService flickrService;

    private Logger logger = Logger.getLogger(ShowChallengeAction.class);

    private ImageChallenge imageChallenge;

    private Long challengeId;

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Challenge ID " + challengeId);
        }

        Challenge challenge = challengeService.getChallenge(getChallengeId());

        if (logger.isDebugEnabled()) {
            logger.debug("Challenge " + challenge);
        }

        imageChallenge = new ImageChallenge(challenge, flickrService.searchForPhotosWithTag(challenge.getTag()));

        return SUCCESS;
    }

    public ImageChallenge getChallenge() {
        return imageChallenge;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public Long getChallengeId() {
        return challengeId;
    }
}