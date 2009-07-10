package net.chrissearle.flickrvote.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class FlickrLinkInterceptor implements Interceptor {
    @Autowired
    private PhotographyService photographyService;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        actionInvocation.getInvocationContext().put(FlickrVoteWebConstants.FLICKR_LOGIN_URL, photographyService.getLoginUrl().toExternalForm());

        return actionInvocation.invoke();
    }
}
