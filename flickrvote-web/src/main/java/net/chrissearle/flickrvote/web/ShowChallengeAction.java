package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.ImageList;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowChallengeAction {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;

    private Logger logger = Logger.getLogger(ShowChallengeAction.class);

    private String challengeTag;

    private ChallengeInfo challenge;

    private ImageList imageList;

    private List<DisplayImage> images;

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Challenge ID " + challengeTag);
        }

        challenge = challengeService.getChallenge(challengeTag);

        if (logger.isDebugEnabled()) {
            logger.debug("Challenge " + challenge);
        }

        if (challenge != null) {
            imageList = photographyService.getChallengeImages(challenge);
        }

        images = new ArrayList<DisplayImage>(imageList.getImages().size());

        for (ImageItem image : imageList.getImages()) {
            images.add(new DisplayImage(image));
        }

        Collections.sort(images, new Comparator<DisplayImage>() {
            public int compare(DisplayImage o1, DisplayImage o2) {
                return o2.getVoteCount().compareTo(o1.getVoteCount());
            }
        });

        return ActionSupport.SUCCESS;
    }

    public void setChallengeTag(String challengeTag) {
        this.challengeTag = challengeTag;
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public List<DisplayImage> getImages() {
        return images;
    }

    public ImageList getImageList() {
        return imageList;
    }
}