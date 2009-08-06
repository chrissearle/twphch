package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
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

public class CurrentChallengeAction extends ActionSupport implements Preparable {
    private Logger log = Logger.getLogger(CurrentChallengeAction.class);

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;

    private ChallengeInfo challenge = null;

    private ImageList imageList;

    private List<DisplayImage> images;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String executeRss() {
        return "rss";
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public List<DisplayImage> getImages() {
        return images;
    }

    public void prepare() throws Exception {
        challenge = challengeService.getCurrentChallenge();

        if (log.isDebugEnabled()) {
            log.debug("Current challenge " + challenge);
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
                return o2.getPostedDate().compareTo(o1.getPostedDate());
            }
        });
    }

    public ImageList getImageList() {
        return imageList;
    }
}
