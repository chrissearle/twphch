package net.chrissearle.flickrvote.web;

import com.aetrion.flickr.photos.Photo;
import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.FlickrService;
import net.chrissearle.flickrvote.web.model.ImageChallenge;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class WelcomeAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private FlickrService flickrService;

    private List<ImageChallenge> challenges;

    public String execute() throws Exception {
        challenges = new ArrayList<ImageChallenge>();
        
        for (Challenge c : challengeService.getChallenges()) {
            challenges.add(new ImageChallenge(c, flickrService.searchForPhotosWithTag(c.getTag())));
        }

        return SUCCESS;
    }

    public List<ImageChallenge> getChallenges() {
        return challenges;
    }
}
