/*
 * Copyright 2009 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.twitter;

import net.chrissearle.mail.SimpleMailService;
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
    private SimpleMailService mailService;

    @Autowired
    public Twitter4jTwitterService(Twitter twitter, TwitterHolder twitterHolder, SimpleMailService mailService) {
        this.twitter = twitter;
        this.twitterActiveFlag = twitterHolder.isActiveFlag();
        this.mailService = mailService;
    }

    public void twitter(String text) {
        if (twitterActiveFlag) {
            try {
                twitter.updateStatus(text);
            } catch (TwitterException e) {
                mailService.sendPost("Unable to tweet " + e.getMessage(), text);
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
            twitter.showUser(twitterId);
            return true;
        } catch (TwitterException e) {
            // Short cut - returns false even if twitter is down.
            return false;
        }
    }
}
