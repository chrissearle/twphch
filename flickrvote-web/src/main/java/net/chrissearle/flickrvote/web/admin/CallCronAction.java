package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class CallCronAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    public String openVoting() throws Exception {
        ChallengeInfo challenge = challengeService.openVoting();

        if (challenge != null) {
            addActionMessage("Voting opened for: " + challenge.getTag());
        } else {
            addActionMessage("No challenge found for voting");
        }

        return SUCCESS;
    }

    public String newChallenge() throws Exception {
        ChallengeInfo challenge = challengeService.announceNewChallenge();

        if (challenge != null) {
            addActionMessage("Announced new challenge: " + challenge.getTag());
        } else {
            addActionMessage("No challenge found for announcement");
        }

        return SUCCESS;
    }

    public String results() throws Exception {
        ChallengeInfo challenge = challengeService.announceResults();

        if (challenge != null) {
            addActionMessage("Results calculated for: " + challenge.getTag());
            addActionMessage("Badges added for: " + challenge.getTag());
            addActionMessage("Results announced for: " + challenge.getTag());
        } else {
            addActionMessage("No challenge found for results");
        }

        return SUCCESS;
    }
}
