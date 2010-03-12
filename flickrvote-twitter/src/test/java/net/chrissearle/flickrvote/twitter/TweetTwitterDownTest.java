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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@RunWith(JUnit4ClassRunner.class)
public class TweetTwitterDownTest extends AbstractTwitterTestSupport {

    @Test(expected = TwitterServiceException.class)
    public void testTweetDown() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        when(twitter.updateStatus(TEST_TWEET_TEXT)).thenThrow(new TwitterException(TWITTER_DOWN));

        TweetService service = getTweetService(twitter, true);

        try {
            service.tweet(TEST_TWEET_TEXT);
        } catch (TwitterServiceException e) {
            assertEquals("Exception message mismatch", "twitter4j.TwitterException: " + TWITTER_DOWN, e.getMessage());
            assertEquals("Twitter message mismatch", "Unable to tweet " + TEST_TWEET_TEXT, e.getTwitterMessage());

            verify(twitter).updateStatus(TEST_TWEET_TEXT);

            throw e;
        }
    }

    @Test(expected = TwitterServiceException.class)
    public void testFollowDown() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        when(twitter.getUserId()).thenReturn(TEST_TWITTER_LOGIN);
        when(twitter.existsFriendship(TEST_TWITTER_LOGIN, TEST_TWITTER_USER)).thenThrow(new TwitterException(TWITTER_DOWN));

        FollowService service = getFollowService(twitter, true);

        try {
            service.follow(TEST_TWITTER_USER);
        } catch (TwitterServiceException e) {
            assertEquals("Exception message mismatch", "twitter4j.TwitterException: " + TWITTER_DOWN, e.getMessage());
            assertEquals("Twitter message mismatch", "Unable to follow " + TEST_TWITTER_USER, e.getTwitterMessage());

            verify(twitter).existsFriendship(TEST_TWITTER_LOGIN, TEST_TWITTER_USER);
            verify(twitter, never()).createFriendship(TEST_TWITTER_USER);
            verify(twitter, never()).enableNotification(TEST_TWITTER_USER);

            throw e;
        }
    }

    @Test
    public void testExistsDown() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        when(twitter.showUser(TEST_TWITTER_USER)).thenThrow(new TwitterException(TWITTER_DOWN));

        UserExistanceService service = getUserExistanceService(twitter, true);

        try {
            service.checkIfUserExists(TEST_TWITTER_USER);

            verify(twitter).showUser(TEST_TWITTER_USER);
        } catch (TwitterServiceException e) {
            fail("Exception was not caught");
        }
    }

    @Test
    public void testDmDown() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        when(twitter.getUserId()).thenReturn(TEST_TWITTER_USER);
        when(twitter.existsFriendship(TEST_TWITTER_USER, TEST_TWITTER_FRIEND)).thenThrow(new TwitterException(TWITTER_DOWN));

        DirectMessageService service = getDirectMessageService(twitter, true);

        try {
            service.dm(TEST_TWITTER_FRIEND, TEST_TWEET_TEXT);
        } catch (TwitterServiceException e) {
            verify(twitter).existsFriendship(TEST_TWITTER_USER, TEST_TWITTER_FRIEND);
            verify(twitter, never()).sendDirectMessage(TEST_TWITTER_FRIEND, TEST_TWEET_TEXT);
        }
    }
}