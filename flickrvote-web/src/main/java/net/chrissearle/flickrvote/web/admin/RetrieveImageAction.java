package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RetrieveImageAction extends ActionSupport {
    Logger logger = Logger.getLogger(RetrieveImageAction.class);

    @Autowired
    private PhotographyService photographyService;

    @Autowired
    private ChallengeService challengeService;

    private List<String> id;

    private String tag;

    public String execute() {
        for (String imageId : id) {
            if (!"".equals(imageId)) {
                photographyService.retrieveAndStoreImage(imageId, tag);
            }
        }

        return SUCCESS;
    }

    @Override
    public void validate() {
        boolean seenId = false;

        for (String imageId : id) {
            if (!"".equals(imageId)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Seen an ID " + imageId);
                }

                seenId = true;
            }
        }

        if (!seenId) {
            addFieldError("id", "Flickr ID must be provided");
        }

    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
