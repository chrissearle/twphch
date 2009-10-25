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

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@RunWith(JUnit4ClassRunner.class)
public class TweetTwitterDisabledTest {
    private static final String TEST_TWEET_TEXT = "Test Tweet Text";
    private static final String TEST_TWITTER_USER = "Test Twitter User";
    private static final String TEST_TWITTER_LOGIN = "TestTwitter";

    @Test
    public void testTweetDisabled() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        TwitterService service = getService(twitter);

        service.twitter(TEST_TWEET_TEXT);

        verify(twitter, never()).updateStatus(TEST_TWEET_TEXT);
    }

    @Test
    public void testFollowDisabled() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        TwitterService service = getService(twitter);

        service.follow(TEST_TWITTER_USER);

        verify(twitter, never()).existsFriendship(TEST_TWITTER_LOGIN, TEST_TWITTER_USER);
        verify(twitter, never()).createFriendship(TEST_TWITTER_USER);
        verify(twitter, never()).enableNotification(TEST_TWITTER_USER);
    }

    @Test
    public void testExistsDisabled() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        TwitterService service = getService(twitter);

        service.twitterExists(TEST_TWITTER_USER);

        verify(twitter, never()).showUser(TEST_TWITTER_USER);
    }


    private TwitterService getService(Twitter twitter) {
        Twitter4jTwitterService service = new Twitter4jTwitterService(twitter);

        service.configure(false);

        return service;
    }
}
