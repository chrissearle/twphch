package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class ChallengesAction extends ActionSupport {
    @Autowired
    ChallengeService challengeService;

    private List<Challenge> challenges;

    public String execute() throws Exception {
        challenges = challengeService.getChallenges();

        Collections.sort(challenges);

        return SUCCESS;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }
}
