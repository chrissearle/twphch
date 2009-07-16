package net.chrissearle.flickrvote.web.vote;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowVoteResultAction extends ActionSupport {

    @Autowired
    private ChallengeService challengeService;

    private ChallengeInfo challenge;

    @Override
    public String execute() throws Exception {
        challenge = challengeService.getVotingChallenge();

        return SUCCESS;
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }
}
