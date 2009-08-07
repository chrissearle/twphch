package net.chrissearle.flickrvote.web.vote;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ShowVoteResultAction extends ActionSupport {

    @Autowired
    private ChallengeService challengeService;

    private Challenge challenge;

    @Override
    public String execute() throws Exception {
        Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.VOTING);

        challenge = new DisplayChallengeSummary(challenges.iterator().next());

        return SUCCESS;
    }

    public Challenge getChallenge() {
        return challenge;
    }
}
