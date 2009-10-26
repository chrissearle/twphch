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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
public class Twitter4jFollowService extends AbstractTwitter4JSupport implements FollowService {

    @Autowired
    public Twitter4jFollowService(Twitter twitter) {
        super(twitter);
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
}