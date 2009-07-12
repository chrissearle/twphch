package net.chrissearle.flickrvote.web.common;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class VotingChallengeBlockAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    private ChallengeInfo challenge;

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    @Override
    public String execute() throws Exception {
        challenge = challengeService.getVotingChallenge();

        if (challenge == null) {
            return "empty";
        }

        return SUCCESS;
    }
}
