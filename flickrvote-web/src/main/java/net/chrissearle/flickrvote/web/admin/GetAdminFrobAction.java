package net.chrissearle.flickrvote.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import net.chrissearle.flickrvote.flickr.FlickrService;
import com.opensymphony.xwork2.ActionSupport;

public class GetAdminFrobAction extends ActionSupport {
    @Autowired
    private FlickrService flickrService;

    private String adminUrl;

    @Override
    public String execute() throws Exception {
        adminUrl = flickrService.getLoginUrl(true).toExternalForm();

        return SUCCESS;
    }

    public String getAdminUrl() {
        return adminUrl;
    }

}
