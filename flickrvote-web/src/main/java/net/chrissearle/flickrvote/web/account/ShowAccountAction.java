package net.chrissearle.flickrvote.web.account;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class ShowAccountAction extends ActionSupport implements SessionAware {
    private PhotographerInfo photographer;
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        this.photographer = (PhotographerInfo) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        return SUCCESS;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public PhotographerInfo getPhotographer() {
        return photographer;
    }
}
