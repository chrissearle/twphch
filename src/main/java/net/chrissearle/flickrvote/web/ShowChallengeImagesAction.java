package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.FlickrService;
import net.chrissearle.flickrvote.web.model.ImageChallenge;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowChallengeImagesAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private FlickrService flickrService;

    private ImageChallenge imageChallenge;

    private Long challengeId;

    public String execute() throws Exception {
        Challenge challenge = challengeService.getChallenge(getChallengeId());

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