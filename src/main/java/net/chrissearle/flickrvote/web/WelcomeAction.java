package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class WelcomeAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private FlickrService flickrService;

    private List<Challenge> challenges;

    public String execute() throws Exception {
        challenges = new ArrayList<Challenge>();

        for (Challenge c : challengeService.getChallenges()) {
            challenges.add(new Challenge(c));
        }

        return SUCCESS;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }
}
