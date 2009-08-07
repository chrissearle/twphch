package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class CurrentChallengeAction extends ActionSupport implements Preparable {
    private Logger log = Logger.getLogger(CurrentChallengeAction.class);

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;

    private List<DisplayImage> images;

    private Challenge challenge = null;

    @Override
    public String execute() throws Exception {
        if (challenge == null) {
            return "noCurrentChallenge";
        }

        return SUCCESS;
    }

    public String executeRss() {
        return "rss";
    }

    public void prepare() throws Exception {
        ChallengeSummary challengeSummary = null;

        // Get a list of all open challenges
        Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.OPEN);

        // Currently the web layer is designed for one current challengeSummary only
        if (challenges.size() > 0) {
            challengeSummary = challenges.iterator().next();
        }

        if (log.isDebugEnabled()) {
            log.debug("Current challengeSummary " + challengeSummary);
        }

        if (challengeSummary != null) {
            challenge = new DisplayChallengeSummary(challengeSummary);

            ChallengeItem challengeItem = photographyService.getChallengeImages(challengeSummary.getTag());

            images = new ArrayList<DisplayImage>(challengeItem.getImages().size());

            for (ImageItem image : challengeItem.getImages()) {
                images.add(new DisplayImage(image));
            }

            Collections.sort(images, new Comparator<DisplayImage>() {
                public int compare(DisplayImage o1, DisplayImage o2) {
                    return o2.getPostedDate().compareTo(o1.getPostedDate());
                }
            });
        }
    }

    public List<DisplayImage> getDisplayImages() {
        return images;
    }

    public Challenge getChallenge() {
        return challenge;
    }
}
