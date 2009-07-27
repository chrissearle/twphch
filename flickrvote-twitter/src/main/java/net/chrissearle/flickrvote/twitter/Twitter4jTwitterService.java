package net.chrissearle.flickrvote.twitter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
public class Twitter4jTwitterService implements TwitterService {
    private Twitter twitter;

    private Logger logger = Logger.getLogger(Twitter4jTwitterService.class);
    private Boolean twitterActiveFlag;

    @Autowired
    public Twitter4jTwitterService(Twitter twitter, TwitterHolder twitterHolder) {
        this.twitter = twitter;
        this.twitterActiveFlag = twitterHolder.isActiveFlag();
    }

    public void twitter(String text) {
        if (twitterActiveFlag) {
            try {
                twitter.updateStatus(text);
            } catch (TwitterException e) {
                throw new TwitterServiceException("Unable to tweet " + text, e);
            }
        }
    }
}
