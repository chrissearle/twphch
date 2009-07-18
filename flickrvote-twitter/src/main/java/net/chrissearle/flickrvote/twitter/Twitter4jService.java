package net.chrissearle.flickrvote.twitter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

@Service
public class Twitter4jService implements TwitterService {
    @Autowired
    private Twitter twitter;

    private Logger logger = Logger.getLogger(Twitter4jService.class);

    public void twitter(String text) {
        if (logger.isInfoEnabled()) {
            logger.info("Tweeting: " + text);
        }
    }

    public List<Status> getTimeline() {
        try {
            return twitter.getFriendsTimeline();
        } catch (TwitterException e) {
            if (logger.isInfoEnabled()) {
                logger.info("Failed to get twitter timeline", e);
            }
        }

        return null;
    }
}
