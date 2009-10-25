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

import org.constretto.annotation.Configuration;
import org.constretto.annotation.Configure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
public class Twitter4jTwitterService implements TwitterService {
    private Twitter twitter;

    private Boolean twitterActiveFlag;

    @Autowired
    public Twitter4jTwitterService(Twitter twitter) {
        this.twitter = twitter;
    }

    @Configure
    public void configure(@Configuration(expression = "twitter.active") Boolean active) {
        twitterActiveFlag = active;
    }

    public void twitter(String text) {
        if (twitterActiveFlag) {
            updateTwitterStatus(text);
        }
    }

    private void updateTwitterStatus(String text) {
        try {
            twitter.updateStatus(text);
        } catch (TwitterException e) {
            throw new TwitterServiceException("Unable to tweet " + text, e);
        }
    }

    public void follow(String twitterId) {
        if (twitterActiveFlag) {
            addTwitterFriendshipAndNotification(twitterId);
        }
    }

    private void addTwitterFriendshipAndNotification(String twitterId) {
        try {
            establishFriendshipWithNotification(twitterId);
        } catch (TwitterException e) {
            throw new TwitterServiceException("Unable to follow " + twitterId, e);
        }
    }

    private void establishFriendshipWithNotification(String twitterId) throws TwitterException {
        if (!alreadyFriends(twitterId)) {
            twitter.createFriendship(twitterId);
            twitter.enableNotification(twitterId);
        }
    }

    private boolean alreadyFriends(String twitterId) throws TwitterException {
        return twitter.existsFriendship(twitter.getUserId(), twitterId);
    }

    public boolean twitterExists(String twitterId) {
        boolean userExists = false;

        if (twitterActiveFlag) {
            userExists = askTwitterForUser(twitterId);
        }

        return userExists;
    }

    private boolean askTwitterForUser(String twitterId) {
        try {
            twitter.showUser(twitterId);

            return true;
        } catch (TwitterException e) {
            // Short cut - returns false even if twitter is down.
            return false;
        }
    }
}
