package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CurrentChallengeAction {
    private Logger log = Logger.getLogger(CurrentChallengeAction.class);

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;

    private ChallengeInfo challenge = null;

    private List<FlickrImage> images = null;

    public String execute() throws Exception {
        challenge = challengeService.getCurrentChallenge();

        if (log.isDebugEnabled()) {
            log.debug("Current challenge " + challenge);
        }

        if (challenge != null) {
            images = photographyService.searchImagesByTag(challenge.getTag());
        }

        return ActionSupport.SUCCESS;
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public List<FlickrImage> getImages() {
        return images;
    }
}
