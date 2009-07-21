package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScoreAction extends ActionSupport {
    Logger logger = Logger.getLogger(ScoreAction.class);

    private List<String> id;

    private List<Long> score;

    private ChallengeInfo challenge;

    private String tag;

    private List<ImageInfo> images;

    @Autowired
    private PhotographyService photographyService;

    @Autowired
    private ChallengeService challengeService;

    @Override
    public String input() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Challenge ID " + tag);
        }

        challenge = challengeService.getChallenge(tag);

        if (logger.isDebugEnabled()) {
            logger.debug("Challenge " + challenge);
        }

        if (challenge != null) {
            images = challengeService.getImagesForChallenge(tag);
        }

        return INPUT;
    }

    @Override
    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            for (int i = 0; i < id.size(); i++) {
                logger.debug("ID: " + id.get(i) + " SCORE: " + score.get(i));
            }
        }

        for (int i = 0; i < id.size(); i++) {
            photographyService.setScore(id.get(i), score.get(i));
        }

        return SUCCESS;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public void setScore(List<Long> score) {
        this.score = score;
    }


    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<ImageInfo> getImages() {
        return images;
    }
}
