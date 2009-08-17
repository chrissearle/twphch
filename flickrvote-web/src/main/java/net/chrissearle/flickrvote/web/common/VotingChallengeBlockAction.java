package net.chrissearle.flickrvote.web.common;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

public class VotingChallengeBlockAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1953138483852395428L;

    private transient Logger logger = Logger.getLogger(VotingChallengeBlockAction.class);

    @Autowired
    private transient ChallengeService challengeService;

    private Challenge challenge;

    private Map<String, Object> session;

    private Boolean voted;

    public Challenge getChallenge() {
        return challenge;
    }

    @Override
    public String execute() throws Exception {
        Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.VOTING);

        if (challenges.size() == 0) {
            return "empty";
        }

        // Front end assumes one voting challenge
        challenge = new DisplayChallengeSummary(challenges.iterator().next());

        if (logger.isDebugEnabled()) {
            logger.debug("Voting challenge: " + challenge);
        }

        if (session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

            voted = challengeService.hasVoted(photographer.getPhotographerId());

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
