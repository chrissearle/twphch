package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.flickr.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;

public class FlickrCallbackAction extends ActionSupport {
    @Autowired
    private FlickrService flickrService;

    private String frob;

    public String getFrob() {
        return frob;
    }

    public void setFrob(String frob) {
        this.frob = frob;
    }

    public String execute() throws Exception {
        flickrService.authenticate(frob);

        return SUCCESS;
    }
}
