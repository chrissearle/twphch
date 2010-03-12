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

import twitter4j.Twitter;

public abstract class AbstractTwitterTestSupport {
    protected static final String TEST_TWEET_TEXT = "Test Tweet Text";
    protected static final String TEST_TWITTER_USER = "Test Twitter User";
    protected static final String TEST_TWITTER_LOGIN = "TestTwitter";
    protected static final String TWITTER_DOWN = "Twitter down";
    protected static final String TEST_TWITTER_FRIEND = "Test Twitter Friend";

    protected Twitter4jUserExistanceService getUserExistanceService(Twitter twitter, boolean activeFlag) {
        Twitter4jUserExistanceService service = new Twitter4jUserExistanceService(twitter);

        service.configure(activeFlag);

        return service;
    }

    protected Twitter4jTweetService getTweetService(Twitter twitter, boolean activeFlag) {
        Twitter4jTweetService service = new Twitter4jTweetService(twitter);

        service.configure(activeFlag);

        return service;
    }

    protected Twitter4jFollowService getFollowService(Twitter twitter, boolean activeFlag) {
        Twitter4jFollowService service = new Twitter4jFollowService(twitter);

        service.configure(activeFlag);

        return service;
    }

    protected DirectMessageService getDirectMessageService(Twitter twitter, boolean activeFlag) {
        Twitter4jDirectMessageService service = new Twitter4jDirectMessageService(twitter);

        service.configure(activeFlag);

        return service;
    }

}
