package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;

public class RankItAction extends ActionSupport {
    private String tag;

    @Autowired
    private ChallengeService challengeService;

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String execute() throws Exception {
        challengeService.rankChallenge(tag);

        addActionMessage("Challenge " + tag + " ranked.");
        return SUCCESS;
    }
}
