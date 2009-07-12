package net.chrissearle.flickrvote.web.common;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import org.springframework.beans.factory.annotation.Autowired;

public class FlickrLoginLinkAction extends ActionSupport {
    @Autowired
    private PhotographyService photographyService;

    private String link;

    @Override
    public String execute() {
        link = photographyService.getLoginUrl().toExternalForm();

        return SUCCESS;
    }

    public String getLink() {
        return link;
    }
}
