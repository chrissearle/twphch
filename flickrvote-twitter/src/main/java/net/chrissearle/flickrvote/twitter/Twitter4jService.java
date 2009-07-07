package net.chrissearle.flickrvote.twitter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

@Repository("twitterService")
public class Twitter4jService implements TwitterService {
    @Autowired
    private Twitter twitter;

    private Logger log = Logger.getLogger(Twitter4jService.class);

    public void twitter(String text) {

    }

    public List<Status> getTimeline() {
        try {
            return twitter.getFriendsTimeline();
        } catch (TwitterException e) {
            if (log.isInfoEnabled()) {
                log.info("Failed to get twitter timeline", e);
            }
        }

        return null;
    }
}
