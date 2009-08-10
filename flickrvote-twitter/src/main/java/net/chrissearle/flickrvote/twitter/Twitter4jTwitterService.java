package net.chrissearle.flickrvote.twitter;

import net.chrissearle.flickrvote.mail.PostService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

@Service
public class Twitter4jTwitterService implements TwitterService {
    private Twitter twitter;

    private Logger logger = Logger.getLogger(Twitter4jTwitterService.class);
    private Boolean twitterActiveFlag;
    private PostService postService;

    @Autowired
    public Twitter4jTwitterService(Twitter twitter, TwitterHolder twitterHolder, PostService postService) {
        this.twitter = twitter;
        this.twitterActiveFlag = twitterHolder.isActiveFlag();
        this.postService = postService;
    }

    public void twitter(String text) {
        if (twitterActiveFlag) {
            try {
                twitter.updateStatus(text);
            } catch (TwitterException e) {
                postService.sendPost("Unable to tweet " + e.getMessage(), text);
                throw new TwitterServiceException("Unable to tweet " + text, e);
            }
        }
    }

    public void follow(String twitterId) {
        try {
            if (!twitter.existsFriendship(twitter.getUserId(), twitterId)) {
                twitter.createFriendship(twitterId);
                twitter.enableNotification(twitterId);
            }
        } catch (TwitterException e) {
            throw new TwitterServiceException("Unable to follow " + twitterId, e);
        }
    }

    public boolean twitterExists(String twitterId) {
        try {
            User user = twitter.showUser(twitterId);
            return true;
        } catch (TwitterException e) {
            // Short cut - returns false even if twitter is down.
            return false;
        }
    }
}
