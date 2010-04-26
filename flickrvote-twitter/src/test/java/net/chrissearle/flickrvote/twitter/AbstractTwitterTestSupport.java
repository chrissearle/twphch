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

    protected UserExistanceService getUserExistanceService(Twitter twitter, boolean activeFlag) {
        final UserExistanceService service = new Twitter4jUserExistanceService(twitter);

        initialize(activeFlag, (AbstractTwitter4JSupport) service);

        return service;
    }

    protected TweetService getTweetService(Twitter twitter, boolean activeFlag) {
        final TweetService service = new Twitter4jTweetService(twitter);

        initialize(activeFlag, (AbstractTwitter4JSupport) service);

        return service;
    }

    protected FollowService getFollowService(Twitter twitter, boolean activeFlag) {
        final FollowService service = new Twitter4jFollowService(twitter);

        initialize(activeFlag, (AbstractTwitter4JSupport) service);

        return service;
    }

    protected DirectMessageService getDirectMessageService(Twitter twitter, boolean activeFlag) {
        final DirectMessageService service = new Twitter4jDirectMessageService(twitter);

        initialize(activeFlag, (AbstractTwitter4JSupport) service);

        return service;
    }

    private void initialize(boolean activeFlag, AbstractTwitter4JSupport service) {
        service.configure(activeFlag);
    }
}
