package net.chrissearle.flickrvote.web.common;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class FlickrLoginLinkAction extends ActionSupport implements SessionAware {
    @Autowired
    private PhotographyService photographyService;

    private String link;
    private Map<String, Object> session;

    @Override
    public String execute() {
        if (session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            return SUCCESS;
        }
        link = photographyService.getLoginUrl().toExternalForm();

        return LOGIN;
    }

    public String getLink() {
        return link;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }
}
