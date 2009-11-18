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
public class TweetTwitterEnabledTest extends AbstractTwitterTestSupport {
    @Test
    public void testTweet() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        TweetService service = getTweetService(twitter, true);

        service.tweet(TEST_TWEET_TEXT);

        verify(twitter).updateStatus(TEST_TWEET_TEXT);
    }

    @Test
    public void testFollow() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        when(twitter.getUserId()).thenReturn(TEST_TWITTER_LOGIN);

        FollowService service = getFollowService(twitter, true);

        service.follow(TEST_TWITTER_USER);

        verify(twitter).existsFriendship(TEST_TWITTER_LOGIN, TEST_TWITTER_USER);
        verify(twitter).createFriendship(TEST_TWITTER_USER);
        verify(twitter).enableNotification(TEST_TWITTER_USER);
    }

    @Test
    public void testExists() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        UserExistanceService service = getUserExistanceService(twitter, true);

        service.checkIfUserExists(TEST_TWITTER_USER);

        verify(twitter).showUser(TEST_TWITTER_USER);
    }

    @Test
    public void testDm() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        when(twitter.getUserId()).thenReturn(TEST_TWITTER_USER);
        when(twitter.existsFriendship(TEST_TWITTER_USER, TEST_TWITTER_FRIEND)).thenReturn(true);

        DirectMessageService service = getDirectMessageService(twitter, true);

        service.dm(TEST_TWITTER_FRIEND, TEST_TWEET_TEXT);

        verify(twitter).existsFriendship(TEST_TWITTER_USER, TEST_TWITTER_FRIEND);
        verify(twitter).sendDirectMessage(TEST_TWITTER_FRIEND, TEST_TWEET_TEXT);
    }

    @Test
    public void testDmNotFriend() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        when(twitter.getUserId()).thenReturn(TEST_TWITTER_USER);
        when(twitter.existsFriendship(TEST_TWITTER_USER, TEST_TWITTER_FRIEND)).thenReturn(false);

        DirectMessageService service = getDirectMessageService(twitter, true);

        try {
            service.dm(TEST_TWITTER_FRIEND, TEST_TWEET_TEXT);
        } catch (TwitterServiceException e) {
            verify(twitter).existsFriendship(TEST_TWITTER_USER, TEST_TWITTER_FRIEND);
            verify(twitter, never()).sendDirectMessage(TEST_TWITTER_FRIEND, TEST_TWEET_TEXT);
        }
    }
}