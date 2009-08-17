package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.ScoreAdmin;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreAction extends ActionSupport {
    private static final long serialVersionUID = -398904299718446646L;

    private transient Logger logger = Logger.getLogger(ScoreAction.class);

    private List<String> id;

    private List<Long> score;

    private Challenge challenge;

    private String tag;

    private List<ScoreAdmin> scores;

    @Autowired
    private transient PhotographyService photographyService;

    @Autowired
    private transient ChallengeService challengeService;

    @Override
    public String input() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Challenge ID " + tag);
        }

        ChallengeSummary challengeSummary = challengeService.getChallengeSummary(tag);

        if (logger.isDebugEnabled()) {
            logger.debug("Challenge " + challengeSummary);
        }

        if (challengeSummary != null) {
            challenge = new DisplayChallengeSummary(challengeSummary);

            ChallengeItem challengeItem = photographyService.getChallengeImages(challengeSummary.getTag());

            scores = new ArrayList<ScoreAdmin>(challengeItem.getImages().size());

            for (ImageItem image : challengeItem.getImages()) {
                scores.add(new ScoreAdmin(image));
            }

            Collections.sort(scores, new Comparator<ScoreAdmin>() {
                public int compare(ScoreAdmin o1, ScoreAdmin o2) {
                    return o2.getScore().compareTo(o1.getScore());
                }
            });
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


    public Challenge getChallenge() {
        return challenge;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<ScoreAdmin> getScores() {
        return scores;
    }
}
