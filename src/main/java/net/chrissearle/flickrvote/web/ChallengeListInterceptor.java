package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
import org.springframework.beans.factory.annotation.Autowired;
import net.chrissearle.flickrvote.service.FlickrService;
import net.chrissearle.flickrvote.service.ChallengeService;

import java.util.Map;

public class ChallengeListInterceptor implements Interceptor {
    @Autowired
    private ChallengeService challengeService;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        actionInvocation.getInvocationContext().put(FlickrVoteWebConstants.CHALLENGE_LIST, challengeService.getChallenges());

        return actionInvocation.invoke();
    }
}