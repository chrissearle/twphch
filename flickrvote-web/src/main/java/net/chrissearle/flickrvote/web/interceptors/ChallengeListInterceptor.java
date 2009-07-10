package net.chrissearle.flickrvote.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class ChallengeListInterceptor implements Interceptor {
    @Autowired
    private ChallengeService challengeService;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        List<ChallengeInfo> challenges = challengeService.getClosedChallenges();

        Collections.sort(challenges);

        actionInvocation.getInvocationContext().put(FlickrVoteWebConstants.CHALLENGE_LIST, challenges);

        return actionInvocation.invoke();
    }
}