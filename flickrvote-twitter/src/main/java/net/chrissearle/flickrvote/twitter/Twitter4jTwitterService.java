package net.chrissearle.flickrvote.twitter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;

@Service
public class Twitter4jTwitterService implements TwitterService {
    @Autowired
    private Twitter twitter;

    private Logger logger = Logger.getLogger(Twitter4jTwitterService.class);

    public void twitter(String text) {
        if (logger.isInfoEnabled()) {
//            logger.info("Tweeting: " + text);
        }

        // TODO - Tweet
    }
}
