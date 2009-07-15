package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PrepareImageFormAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    private List<ChallengeInfo> challenges;

    public String execute() {
        challenges = challengeService.getClosedChallenges();

        return SUCCESS;
    }

    public List<ChallengeInfo> getChallenges() {
        return challenges;
    }
}
