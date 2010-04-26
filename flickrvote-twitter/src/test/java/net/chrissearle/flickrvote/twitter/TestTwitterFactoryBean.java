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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.OAuthAuthorization;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:twitter4j.xml"})
public class TestTwitterFactoryBean {

    public TestTwitterFactoryBean() {
        // Force to development. This test will fail without the correct keys in /etc/flickrvote/flickrvote.properties
        // and these are NOT checked in by design.
        System.setProperty("CONSTRETTO_TAGS", "development");
    }

    @Autowired
    private Twitter twitter;

    @Test
    public void testConfig() throws TwitterException {
        AccessToken token = ((OAuthAuthorization) twitter.getAuthorization()).getOAuthAccessToken();

        assertEquals("Incorrect token", "39244571-uz7vQhkNV7QIU8NuCUuVoGBvAGlvNFMGvKcGmp0", token.getToken());
        assertEquals("Incorrect token secret", "p5ljZX0ekxTsc7M9uFnzVfOSbW5c1WKKC6fkdMRMVc", token.getTokenSecret());
    }

    @Test
    public void testScreenName() throws TwitterException {
        assertEquals("Incorrect user", "twphch", twitter.getScreenName());
    }

    @Test
    public void testNoSpring() throws TwitterException {
        String key = "Ur9SEVYF4x8HRPj5Bg3GzA";
        String secret = "UNysUsPpTUZuc0fqToGEppEDHJjp2FaRBLH7cYnsh4";

        String token = "39244571-uz7vQhkNV7QIU8NuCUuVoGBvAGlvNFMGvKcGmp0";
        String tokenSecret = "p5ljZX0ekxTsc7M9uFnzVfOSbW5c1WKKC6fkdMRMVc";

        AccessToken twitterToken = new AccessToken(token, tokenSecret);

        Twitter twitterInstance = new TwitterFactory().getOAuthAuthorizedInstance(key, secret, twitterToken);

        assertEquals("Incorrect user", "twphch", twitterInstance.getScreenName());
    }
}
