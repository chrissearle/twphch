/*
 * Copyright 2010 Chris Searle
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
public class Twitter4jTweetService extends AbstractTwitter4JSupport implements TweetService {

    @Autowired
    public Twitter4jTweetService(Twitter twitter) {
        super(twitter);
    }

    public void tweet(String message) {
        if (twitterActiveFlag) {
            updateTwitterStatus(message);
        }
    }

    private void updateTwitterStatus(String text) {
        try {
            twitter.updateStatus(text);
        } catch (TwitterException e) {
            throw new TwitterServiceException("Unable to tweet " + text, e);
        }
    }
}