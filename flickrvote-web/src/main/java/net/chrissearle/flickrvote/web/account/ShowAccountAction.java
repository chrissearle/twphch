package net.chrissearle.flickrvote.web.account;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class ShowAccountAction extends ActionSupport implements SessionAware {
    private Photographer photographer;
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        return SUCCESS;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public Photographer getPhotographer() {
        return photographer;
    }
}
