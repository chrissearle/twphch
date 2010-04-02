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

package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.twitter.TweetService;
import net.chrissearle.flickrvote.twitter.TwitterServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SendTweetAction extends ActionSupport {
    private static final long serialVersionUID = -731316433063598691L;

    private transient Logger logger = Logger.getLogger(SendTweetAction.class.getName());

    private String tweet;

    @Autowired
    private transient TweetService tweetService;

    @Override
    public String execute() throws Exception {
        try {
            tweetService.tweet(tweet);

            addActionMessage("Tweeted");
        } catch (TwitterServiceException tse) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to tweet: " + tse.getMessage());
            }
            addActionError("Unable to tweet: " + tse.getMessage());
        }

        return SUCCESS;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getTweet() {
        return tweet;
    }
}
