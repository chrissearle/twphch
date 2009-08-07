package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowChartAction extends ActionSupport {

    private String tag;

    private Challenge challenge;

    @Autowired
    private ChallengeService challengeService;

    @Override
    public String execute() throws Exception {
        challenge = new DisplayChallengeSummary(challengeService.getChallengeSummary(tag));

        return SUCCESS;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Challenge getChallenge() {
        return challenge;
    }
}
