package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;

public class FlickrCallbackAction extends ActionSupport {
    @Autowired
    private PhotographerService photographerService;

    private String frob;

    public String getFrob() {
        return frob;
    }

    public void setFrob(String frob) {
        this.frob = frob;
    }

    public String execute() throws Exception {
        photographerService.checkLoginAndStore(frob);

        return SUCCESS;
    }
}
