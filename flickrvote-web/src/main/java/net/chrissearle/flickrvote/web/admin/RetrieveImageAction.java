package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import org.springframework.beans.factory.annotation.Autowired;

public class RetrieveImageAction {

    @Autowired
    private PhotographyService service;

    private String id;

    private String tag;

    public String execute() {
        service.retrieveAndStoreImage(id, tag);

        return ActionSupport.SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
