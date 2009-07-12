package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import org.springframework.beans.factory.annotation.Autowired;

public class RetrievePhotographerAction extends ActionSupport {

    @Autowired
    private PhotographyService service;

    private String id;

    public String execute() {
        service.retrieveAndStorePhotographer(id);

        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    @Override
    public void validate() {
        if (getId().length() == 0) {
            addFieldError("id", "Flickr ID must be provided");
        }
    }

    public void setId(String id) {
        this.id = id;
    }
}
