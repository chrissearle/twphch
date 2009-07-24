package net.chrissearle.flickrvote.web.common;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class VotingChallengeBlockAction extends ActionSupport implements SessionAware {
    Logger logger = Logger.getLogger(VotingChallengeBlockAction.class);

    @Autowired
    private ChallengeService challengeService;

    private ChallengeInfo challenge;

    private Map<String, Object> session;

    private Boolean voted;

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    @Override
    public String execute() throws Exception {
        challenge = challengeService.getVotingChallenge();

        if (logger.isDebugEnabled()) {
            logger.debug("Voting challenge: " + challenge);
        }
        
        if (challenge == null) {
            return "empty";
        }

        if (session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            PhotographerInfo photographer = (PhotographerInfo) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

            voted = challengeService.hasVoted(photographer.getId());

            if (logger.isDebugEnabled()) {
                logger.debug("Setting voted to " + voted);
            }
        }

        return SUCCESS;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public Boolean isVoted() {
        return voted;
    }
}
