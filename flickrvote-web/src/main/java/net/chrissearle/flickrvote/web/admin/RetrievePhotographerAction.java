package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;

public class RetrievePhotographerAction {

    @Autowired
    private PhotographerService service;

    private String id;

    public String execute() {
        service.retrieveAndStore(id);

        return ActionSupport.SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
