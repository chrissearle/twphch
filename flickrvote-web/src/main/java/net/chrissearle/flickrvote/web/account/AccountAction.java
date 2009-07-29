package net.chrissearle.flickrvote.web.account;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class AccountAction extends ActionSupport implements SessionAware, Preparable {
    @Autowired
    PhotographyService photographyService;

    private Map<String, Object> session;

    private PhotographerInfo photographer;
    private String twitter;

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public void prepare() throws Exception {
        photographer = (PhotographerInfo) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        if (photographer.getTwitter() != null && (twitter == null || "".equals(twitter))) {
            twitter = photographer.getTwitter();
        }
    }

    public PhotographerInfo getPhotographer() {
        return photographer;
    }

    @Override
    public String execute() throws Exception {
        photographer.setTwitter(twitter);
        session.put(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY, photographer);

        photographyService.setTwitter(photographer.getId(), twitter);

        return SUCCESS;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
