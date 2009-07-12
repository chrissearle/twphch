package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateChallengeAction extends ActionSupport {
    private ChallengeInfo challenge;

    @Autowired
    private ChallengeService challengeService;

    @Override
    public String execute() throws Exception {
        challengeService.addChallenge(challenge.getTitle(), challenge.getTag(),
                challenge.getStartDate(), challenge.getVoteDate(), challenge.getEndDate());

        return SUCCESS;
    }

    @Override
    public void validate() {
        if (challenge.getTitle().length() == 0) {
            addFieldError("challenge.title", "Title must be filled out");
        }
        if (challenge.getTag().length() == 0) {
            addFieldError("challenge.tag", "Tag must be filled out");
        }
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeInfo challenge) {
        this.challenge = challenge;
    }
}
