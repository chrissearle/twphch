package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import org.springframework.beans.factory.annotation.Autowired;

public class FlickrCallbackAction {
    @Autowired
    private PhotographyService photographyService;

    private String frob;

    public String getFrob() {
        return frob;
    }

    public void setFrob(String frob) {
        this.frob = frob;
    }

    public String execute() throws Exception {
        photographyService.checkLoginAndStore(frob);

        return ActionSupport.SUCCESS;
    }
}
