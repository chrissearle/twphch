package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
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
                    ImageItem image = photographyService.retrieveAndStoreImage(imageId, tag);

                    addActionMessage("&laquo;" + image.getTitle() + "&raquo; av " + image.getPhotographer().getName() + " retrieved/updated.");
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

    public void prepare() throws Exception {
        challenges = challengeService.getChallenges();

        Collections.sort(challenges, new Comparator<ChallengeInfo>() {
            public int compare(ChallengeInfo o1, ChallengeInfo o2) {
                return o2.getTag().compareTo(o1.getTag());
            }
        });
    }

    public List<ChallengeInfo> getChallenges() {
        return challenges;
    }
}
