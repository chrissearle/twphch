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

@Service("directMessageService")
public class Twitter4jDirectMessageService extends AbstractTwitter4JSupport implements DirectMessageService {

    @Autowired
    public Twitter4jDirectMessageService(Twitter twitter) {
        super(twitter);
    }

    public void dm(String username, String message) {
        if (twitterActiveFlag) {
            sendMessage(username, message);
        }
    }

    private void sendMessage(String username, String text) {
        try {
            if (canSendTo(username)) {
                twitter.sendDirectMessage(username, text);
            } else {
                throw new TwitterServiceException("Unable to DM " + username + " - not friends");
            }
        } catch (TwitterException e) {
            throw new TwitterServiceException("Unable to dm " + username + " with message " + text, e);
        }
    }

    private boolean canSendTo(String username) throws TwitterException {
        return twitter.existsFriendship(twitter.getUserId(), username);
    }
}