package net.chrissearle.flickrvote.web.rss;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CurrentAction extends ActionSupport {
    private ChallengeInfo challenge;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;
    private List<FlickrImage> images;

    @Override
    public String execute() throws Exception {
        challenge = challengeService.getCurrentChallenge();

        if (challenge != null) {
            images = photographyService.searchImagesByTag(challenge.getTag());
        }

        Collections.sort(images, new Comparator<FlickrImage>() {
            public int compare(FlickrImage o1, FlickrImage o2) {
                return o2.getPostedDate().compareTo(o1.getPostedDate());
            }
        });

        return SUCCESS;
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public List<FlickrImage> getImages() {
        return images;
    }
}
