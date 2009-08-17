package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.twitter.TwitterService;
import net.chrissearle.flickrvote.twitter.TwitterServiceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class SendTweetAction extends ActionSupport {
    private static final long serialVersionUID = -731316433063598691L;

    private transient Logger logger = Logger.getLogger(SendTweetAction.class);

    private String tweet;

    @Autowired
    private transient TwitterService twitterService;

    @Override
    public String execute() throws Exception {
        try {
            twitterService.twitter(tweet);

            addActionMessage("Tweeted");
        } catch (TwitterServiceException tse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to tweet", tse);
            }
            addActionError("Unable to tweet: " + tse.getMessage());
        }

        return SUCCESS;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getTweet() {
        return tweet;
    }
}
