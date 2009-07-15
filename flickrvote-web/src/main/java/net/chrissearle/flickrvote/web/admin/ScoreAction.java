package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScoreAction extends ActionSupport {
    Logger logger = Logger.getLogger(ScoreAction.class);

    private List<String> id;

    private List<Long> score;

    @Autowired
    private PhotographyService photographyService;

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
}
