package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PrepareScoreForm extends ActionSupport {
    Logger logger = Logger.getLogger(PrepareScoreForm.class);

    @Autowired
    private ChallengeService challengeService;

    private ChallengeInfo challenge;

    private String tag;

    private List<ImageInfo> images;

    @Override
    public String execute() {
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


        return SUCCESS;
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
