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
public class TweetTwitterDisabledTest extends AbstractTwitterTestSupport {

    @Test
    public void testTweetDisabled() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        TweetService service = getTweetService(twitter, false);

        service.tweet(TEST_TWEET_TEXT);

        verify(twitter, never()).updateStatus(TEST_TWEET_TEXT);
    }

    @Test
    public void testFollowDisabled() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        FollowService service = getFollowService(twitter, false);

        service.follow(TEST_TWITTER_USER);

        verify(twitter, never()).existsFriendship(TEST_TWITTER_LOGIN, TEST_TWITTER_USER);
        verify(twitter, never()).createFriendship(TEST_TWITTER_USER);
        verify(twitter, never()).enableNotification(TEST_TWITTER_USER);
    }


    @Test
    public void testExistsDisabled() throws TwitterException {
        Twitter twitter = mock(Twitter.class);

        Twitter4jUserExistanceService service = getUserExistanceService(twitter, false);

        service.checkIfUserExists(TEST_TWITTER_USER);

        verify(twitter, never()).showUser(TEST_TWITTER_USER);
    }

}
