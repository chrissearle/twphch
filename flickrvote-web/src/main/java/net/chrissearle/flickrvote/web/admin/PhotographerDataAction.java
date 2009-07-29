package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PhotographerDataAction extends ActionSupport implements Preparable {
    @Autowired
    private PhotographyService photographyService;

    private List<PhotographerInfo> photographers;

    private String id;

    public String browse() throws Exception {
        return "browse";
    }

    public void prepare() throws Exception {
        photographers = photographyService.getPhotographers();

        Collections.sort(photographers, new Comparator<PhotographerInfo>() {
            public int compare(PhotographerInfo o1, PhotographerInfo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public List<PhotographerInfo> getPhotographers() {
        return photographers;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String execute() throws Exception {
        PhotographerInfo photographer = photographyService.findById(id);

        if (photographer != null) {
            photographyService.setAdministrator(photographer.getId(), !photographer.isAdministrator());

            addActionMessage("Admin flag toggled for photographer " + photographer.getName());
        } else {
            addActionError("Photographer not found for id: " + id);
        }

        return SUCCESS;
    }
}
