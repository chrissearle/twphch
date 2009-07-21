package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class PhotographerAction extends ActionSupport {

    @Autowired
    private PhotographyService service;

    private String id;

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    public String execute() {
        try {
            PhotographerInfo photographer = service.retrieveAndStorePhotographer(id);

            if (photographer == null) {
                addActionMessage("Photographer already present.");
            } else {
                addActionMessage(photographer.getName() + " was retrieved.");
            }
        } catch (FlickrServiceException e) {
            addActionError("Error retrieving photographer: " + e.getMessage());
        }
        
        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
