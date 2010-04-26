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
public class Twitter4jUserExistanceService extends AbstractTwitter4JSupport implements UserExistanceService {
    private final Logger logger = Logger.getLogger(Twitter4jTweetService.class.getName());

    @Autowired
    public Twitter4jUserExistanceService(Twitter twitter) {
        super(twitter);
    }

    public boolean checkIfUserExists(String twitterId) {
        boolean userExists = false;

        if (logger.isLoggable(Level.INFO)) {
            logger.info(new StringBuilder().append("Asking for: ").append(twitterId).toString());
        }

        if (getTwitterActiveFlag()) {
            userExists = askTwitterForUser(twitterId);
        }

        return userExists;
    }

    private boolean askTwitterForUser(String twitterId) {
        try {
            twitter.showUser(twitterId);

            return true;
        } catch (TwitterException e) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning(new StringBuilder().append("Twitter failed: ").append(e.getMessage()).toString());
            }

            return false;
        }
    }
}
