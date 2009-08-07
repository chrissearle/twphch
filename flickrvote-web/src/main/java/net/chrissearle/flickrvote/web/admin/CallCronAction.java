package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import org.springframework.beans.factory.annotation.Autowired;

public class CallCronAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;

    public String openVoting() throws Exception {
        photographyService.freezeChallenge();

        ChallengeSummary challenge = challengeService.openVoting();

        if (challenge != null) {
            addActionMessage("Voting opened for: " + challenge.getTag());
        } else {
            addActionMessage("No challenge found for voting");
        }

        return SUCCESS;
    }

    public String newChallenge() throws Exception {
        ChallengeSummary challenge = challengeService.announceNewChallenge();

        if (challenge != null) {
            addActionMessage("Announced new challenge: " + challenge.getTag());
        } else {
            addActionMessage("No challenge found for announcement");
        }

        return SUCCESS;
    }

    public String results() throws Exception {
        ChallengeSummary challenge = challengeService.announceResults();

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
