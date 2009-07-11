package net.chrissearle.flickrvote.web.chart;

import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.impl.AvalonLogger;
import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ChallengeVotesAction {
    private JFreeChart chart;

    private String tag;

    @Autowired
    private ChallengeService challengeService;

    private Logger logger = Logger.getLogger(ChallengeVotesAction.class);

    public String execute() {
        if (logger.isDebugEnabled()) {
            logger.debug("Challenge ID " + tag);
        }

        // Need the challenge for title etc for the graph axes
        ChallengeInfo challenge = challengeService.getChallenge(tag);

        if (logger.isDebugEnabled()) {
            logger.debug("Challenge " + challenge);
        }

        // Meed the images to get the graph data
        List<ImageInfo> images = null;

        if (challenge != null) {
            images = challengeService.getImagesForChallenge(tag);
        }

        // TODO - create correct chart type - in the mean time - here's the data
        Map data<String, Long> = new HashMap<String, Long>();

        for (ImageInfo imageInfo : images) {
            data.put(imageInfo.getPhotographerName(), imageInfo.getFinalVoteCount());
        }

        return ActionSupport.SUCCESS;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public JFreeChart getChart() {
        return chart;
    }
}
