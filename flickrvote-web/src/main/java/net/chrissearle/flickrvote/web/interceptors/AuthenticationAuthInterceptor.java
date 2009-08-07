package net.chrissearle.flickrvote.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;

import java.util.Map;

public class AuthenticationAuthInterceptor implements Interceptor {
    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map<String, Object> session = actionInvocation.getInvocationContext().getSession();

        if (!session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            return "notloggedin";
        }

        return actionInvocation.invoke();
    }
}