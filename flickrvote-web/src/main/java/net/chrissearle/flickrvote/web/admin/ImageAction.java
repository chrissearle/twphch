package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ImageAction extends ActionSupport implements Preparable {
    Logger logger = Logger.getLogger(ImageAction.class);

    @Autowired
    private PhotographyService photographyService;

    @Autowired
    private ChallengeService challengeService;

    private List<String> id;

    private String tag;

    private List<ChallengeInfo> challenges;

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    @Override
    public String execute() throws Exception {
        for (String imageId : id) {
            if (!"".equals(imageId)) {
                try {
                    ImageInfo image = photographyService.retrieveAndStoreImage(imageId, tag);

                    if (image != null) {
                        addActionMessage("&laquo;" + image.getTitle() + "&raquo; av " + image.getPhotographerName() + " retrieved.");
                    } else {
                        addActionError("No image found for " + imageId);
                    }
                } catch (FlickrServiceException e) {
                    addActionError("Error retrieving image " + imageId + ": " + e.getMessage());
                }
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
            addActionError("Flickr ID must be provided");
        }

    }

    public List<String> getId
            () {
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

    public void prepare() throws Exception {
        challenges = challengeService.getClosedChallenges();
    }

    public List<ChallengeInfo> getChallenges() {
        return challenges;
    }
}