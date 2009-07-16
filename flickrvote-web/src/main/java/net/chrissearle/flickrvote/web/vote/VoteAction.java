package net.chrissearle.flickrvote.web.vote;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class VoteAction extends ActionSupport implements SessionAware {
    Logger logger = Logger.getLogger(VoteAction.class);

    private Map<String, Object> session;

    List<String> votes;

    @Autowired
    private ChallengeService challengeService;

    public void setVotes(List<String> votes) {
        this.votes = votes;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    @Override
    public String execute() throws Exception {
        PhotographerInfo photographer = (PhotographerInfo) session.get("flickrUser");

        if (logger.isDebugEnabled()) {
            logger.debug("Votes by: " + photographer.getName() + " : " + votes);
        }

        for (String imageId : votes) {
            challengeService.vote(photographer.getId(), imageId);
        }


        return SUCCESS;
    }
}
