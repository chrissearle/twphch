package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CurrentChallengeAction extends ActionSupport {
    private Logger log = Logger.getLogger(CurrentChallengeAction.class);

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private FlickrService flickrService;

    private Challenge challenge;

    private List<FlickrImage> images;

    public String execute() throws Exception {
        challenge = challengeService.getCurrentChallenge();

        if (log.isDebugEnabled()) {
            log.debug("Current challenge " + challenge);
        }

        images = flickrService.searchImagesByTag(challenge.getTag());

        return SUCCESS;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public List<FlickrImage> getImages() {
        return images;
    }
}
