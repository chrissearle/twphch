package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.chrissearle.flickrvote.flickr.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;

public class FlickrLinkInterceptor implements Interceptor {
    @Autowired
    private FlickrService flickrService;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        actionInvocation.getInvocationContext().put(FlickrVoteWebConstants.FLICKR_LOGIN_URL, flickrService.getLoginUrl().toExternalForm());

        return actionInvocation.invoke();
    }
}
