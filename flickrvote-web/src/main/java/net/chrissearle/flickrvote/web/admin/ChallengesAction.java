package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class ChallengesAction {
    @Autowired
    ChallengeService challengeService;

    private List<ChallengeInfo> challenges;

    public String execute() throws Exception {
        challenges = challengeService.getChallenges();

        Collections.sort(challenges);

        return ActionSupport.SUCCESS;
    }

    public List<ChallengeInfo> getChallenges() {
        return challenges;
    }
}
