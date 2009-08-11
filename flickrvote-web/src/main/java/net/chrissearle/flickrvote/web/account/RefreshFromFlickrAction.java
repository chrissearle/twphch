package net.chrissearle.flickrvote.web.account;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ImageItem;
import org.springframework.beans.factory.annotation.Autowired;

public class RefreshFromFlickrAction extends ActionSupport {
    @Autowired
    private PhotographyService photographyService;

    private String id;

    @Override
    public String execute() throws Exception {

        ImageItem image = photographyService.findImageById(id);

        if (image != null) {
            image = photographyService.retrieveAndStoreImage(image.getId(), image.getChallenge().getTag());

            String[] args = new String[1];
            args[0] = image.getTitle();
            addActionMessage(getText("refresh.message.text", args));
        }

        return SUCCESS;
    }

    public void setId(String id) {
        this.id = id;
    }
}
