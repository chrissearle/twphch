package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class VotingChallengeInterceptor implements Interceptor {
    private Logger log = Logger.getLogger(VotingChallengeInterceptor.class);

    @Autowired
    private ChallengeService challengeService;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ChallengeInfo challenge = challengeService.getVotingChallenge();

        if (challenge != null) {
            if (log.isDebugEnabled()) {
                log.debug("Voting challenge found " + challenge);
            }
            actionInvocation.getInvocationContext().put(FlickrVoteWebConstants.VOTING_CHALLENGE, challenge);
        }

        return actionInvocation.invoke();
    }
}