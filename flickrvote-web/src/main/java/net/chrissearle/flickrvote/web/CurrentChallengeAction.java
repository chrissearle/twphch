package net.chrissearle.flickrvote.web;

import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class CurrentChallengeAction extends ActionSupport{
    private Logger log = Logger.getLogger(CurrentChallengeAction.class);

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private FlickrService flickrService;

    private ChallengeInfo challenge = null;

    private List<FlickrImage> images = null;

    public String execute() throws Exception {
        challenge = challengeService.getCurrentChallenge();

        if (log.isDebugEnabled()) {
            log.debug("Current challenge " + challenge);
        }

        if (challenge != null) {
            images = flickrService.searchImagesByTag(challenge.getTag());
        }

        return SUCCESS;
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public List<FlickrImage> getImages() {
        return images;
    }
}
