package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.web.model.DisplayPhotographer;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class FlickrCallbackAction implements SessionAware {
    @Autowired
    private PhotographyService photographyService;

    private String frob;

    private Map<String, Object> session;

    public String getFrob() {
        return frob;
    }

    public void setFrob(String frob) {
        this.frob = frob;
    }

    public String execute() throws Exception {
        Photographer photographer = new DisplayPhotographer(photographyService.checkLoginAndStore(frob));

        session.put(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY, photographer);

        return ActionSupport.SUCCESS;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }
}
