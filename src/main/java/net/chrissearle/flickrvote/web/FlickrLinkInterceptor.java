package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.ActionInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import net.chrissearle.flickrvote.service.FlickrService;

import java.util.Map;

public class FlickrLinkInterceptor implements Interceptor {
    @Autowired
    private FlickrService flickrService;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map session = actionInvocation.getInvocationContext().getSession();

        session.put(FlickrVoteWebConstants.FLICKR_LOGIN_URL, flickrService.getLoginUrl().toExternalForm());

        return actionInvocation.invoke();
    }
}
