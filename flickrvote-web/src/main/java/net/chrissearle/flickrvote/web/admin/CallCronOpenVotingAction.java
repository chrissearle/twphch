package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;

public class CallCronOpenVotingAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    public String openVoting() throws Exception {
        challengeService.openVoting();

        return SUCCESS;
    }

    public String newChallenge() throws Exception {
        challengeService.announceNewChallenge();

        return SUCCESS;
    }

    public String results() throws Exception {
        challengeService.announceResults();

        return SUCCESS;
    }
}
