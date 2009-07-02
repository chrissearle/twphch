package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;

public class CurrentChallengeAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    public String execute() throws Exception {

        return SUCCESS;
    }
}
