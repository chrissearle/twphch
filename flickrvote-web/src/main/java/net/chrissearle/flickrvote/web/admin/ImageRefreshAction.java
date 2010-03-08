package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.ServiceException;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ImageItem;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageRefreshAction extends ActionSupport {
    @Autowired
    ChallengeService challengeService;

    private String tag;

    @Autowired
    private PhotographyService photographyService;

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String execute() throws Exception {
        ChallengeItem challengeItem = photographyService.getChallengeImages(tag);

        addActionMessage("Refreshing " + tag + ": " + challengeItem.getTitle());
        for (ImageItem item : challengeItem.getImages()) {
            try {
                ImageItem image = photographyService.retrieveAndStoreImage(item.getId(), tag);
                addActionMessage("&laquo;" + image.getTitle() + "&raquo; av " + image.getPhotographer().getName() + " retrieved/updated.");
            } catch (ServiceException se) {
                addActionError("Failed to refresh " + item.getTitle() + " due to " + se.getMessage());                
            }
        }

        return SUCCESS;
    }
}
