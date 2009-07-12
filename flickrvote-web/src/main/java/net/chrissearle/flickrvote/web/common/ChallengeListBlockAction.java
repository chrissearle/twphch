package net.chrissearle.flickrvote.web.common;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class ChallengeListBlockAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    private List<ChallengeInfo> challenges;

    @Override
    public String execute() throws Exception {
        challenges = challengeService.getClosedChallenges();

        Collections.sort(challenges);

        return SUCCESS;
    }

    public List<ChallengeInfo> getChallenges() {
        return challenges;
    }
}
