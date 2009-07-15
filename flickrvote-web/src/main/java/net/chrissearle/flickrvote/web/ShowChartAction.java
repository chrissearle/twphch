package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowChartAction extends ActionSupport {

    private String tag;

    private ChallengeInfo challenge;

    @Autowired
    private ChallengeService challengeService;

    @Override
    public String execute() throws Exception {
        challenge = challengeService.getChallenge(tag);

        return SUCCESS;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }
}
