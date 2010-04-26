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

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Twitter4jTweetService extends AbstractTwitter4JSupport implements TweetService {
    private final Logger logger = Logger.getLogger(Twitter4jTweetService.class.getName());

    @Autowired
    public Twitter4jTweetService(Twitter twitter) {
        super(twitter);
    }

    public void tweet(String message) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(new StringBuilder().append("Tweet: ").append(message).toString());
        }

        if (getTwitterActiveFlag()) {
            updateTwitterStatus(message);
        } else {
            if (logger.isLoggable(Level.INFO)) {
                logger.info("Twitter disabled");
            }
        }
    }

    private void updateTwitterStatus(String text) {
        try {
            twitter.updateStatus(text);
        } catch (TwitterException e) {
            final String message = new StringBuilder().append("Unable to tweet: ").append(text).append(" due to ")
                    .append(e.getMessage()).toString();

            if (logger.isLoggable(Level.WARNING)) {
                logger.warning(message);
            }

            throw new TwitterServiceException(message, e);
        }
    }
}