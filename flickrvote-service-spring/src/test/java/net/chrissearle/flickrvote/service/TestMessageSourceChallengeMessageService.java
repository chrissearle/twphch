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

package net.chrissearle.flickrvote.service;

import com.rosaloves.net.shorturl.bitly.Bitly;
import com.rosaloves.net.shorturl.bitly.BitlyFactory;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.TestChallengeSummary;
import net.chrissearle.flickrvote.service.model.TestImageItem;
import net.chrissearle.flickrvote.service.model.TestPhotographerItem;
import org.constretto.ConstrettoBuilder;
import org.constretto.ConstrettoConfiguration;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;

@RunWith(JUnit4ClassRunner.class)
public class TestMessageSourceChallengeMessageService {
    private ChallengeMessageService challengeMessageService;

    private ChallengeSummary challenge;
    private String resultsUrl;

    @Before
    public void setup() throws Exception {
        ConstrettoConfiguration conf = new ConstrettoBuilder()
                .addCurrentTag("development")
                .createPropertiesStore()
                .addResource(new DefaultResourceLoader().getResource("classpath:flickrvote.properties"))
                .done()
                .createPropertiesStore()
                .addResource(new DefaultResourceLoader().getResource("file:/etc/flickrvote/flickrvote.properties"))
                .done()
                .getConfiguration();

        Bitly bitly = BitlyFactory.newInstance(conf.evaluateToString("bitly.login"),
                conf.evaluateToString("bitly.key"));

        ShortUrlService shortUrlService = new BitlyShortUrlService(bitly);

        MessageSourceChallengeMessageService service = new MessageSourceChallengeMessageService(shortUrlService);

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setFallbackToSystemLocale(true);

        service.setMessageSource(messageSource);
        service.afterPropertiesSet();

        challengeMessageService = service;

        DateTime startDate = new DateTime(2009, DateTimeConstants.JANUARY, 1, 18, 0, 0, 0);

        DateTime voteDate = startDate.plusDays(5);

        DateTime endDate = startDate.plusDays(7).plusHours(3);

        TestChallengeSummary testChallenge = new TestChallengeSummary();
        testChallenge.setTag("TestTag");
        testChallenge.setTitle("Test Name");
        testChallenge.setStartDate(startDate.toDate());
        testChallenge.setEndDate(endDate.toDate());
        testChallenge.setVoteDate(voteDate.toDate());

        challenge = testChallenge;

        resultsUrl = challengeMessageService.getResultsUrl(challenge);
    }

    @Test
    public void testGetVotingTwitter() {
        String message = challengeMessageService.getVotingTwitter(challenge);

        System.out.println(message);

        assert message.contains("Avstemning") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("bit.ly") : "Message missing link";
    }

    @Test
    public void testGetCurrentTwitter() {
        String message = challengeMessageService.getCurrentTwitter(challenge);

        System.out.println(message);

        assert message.contains("Oppgaven") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("bit.ly") : "Message missing link";
    }

    @Test
    public void testGetResultsUrl() {
        String resultsUrl = challengeMessageService.getResultsUrl(challenge);

        assert resultsUrl.contains("http") : "Message missing link";
        assert !resultsUrl.contains("bit.ly") : "Message link was shortened";
    }

    @Test
    public void testGetResultsTwitter() {
        String message = challengeMessageService.getResultsTwitter(challenge, resultsUrl);

        System.out.println(message);

        assert message.contains("Resultatene fra oppgaven") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("bit.ly") : "Message missing link";
    }

    @Test
    public void testGetVotingForumTitle() {
        String message = challengeMessageService.getVotingForumTitle(challenge);

        System.out.println(message);

        assert message.contains("Avstemning") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
    }

    @Test
    public void testGetCurrentForumTitle() {
        String message = challengeMessageService.getCurrentForumTitle(challenge);

        System.out.println(message);

        assert message.contains("Oppgaven") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
    }

    @Test
    public void testGetVotingForumText() {
        String message = challengeMessageService.getVotingForumText(challenge);

        System.out.println(message);

        assert message.contains("tillatt") : "Message missing words";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("2009-01-06") : "Message missing dates";
        assert message.contains("http") : "Message missing link";
        assert !resultsUrl.contains("bit.ly") : "Message link was shortened";
    }

    @Test
    public void testGetCurrentForumText() {
        String message = challengeMessageService.getCurrentForumText(challenge);

        System.out.println(message);

        assert message.contains("tilbakemelding") : "Message missing words";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("2009-01-06") : "Message missing dates";
        assert message.contains("http") : "Message missing link";
        assert !resultsUrl.contains("bit.ly") : "Message link was shortened";
    }

    @Test
    public void testGoldBadge() {
        String message = challengeMessageService.getBadgeText(1, challengeMessageService.getGoldBadgeUrl(), challenge);

        System.out.println(message);

        assert message.contains("notsowide") : "Message missing words";
        assert message.contains("1.") : "Message missing place";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("gold") : "Message missing badge";
    }

    @Test
    public void testSilverBadge() {
        String message = challengeMessageService.getBadgeText(2, challengeMessageService.getSilverBadgeUrl(), challenge);

        System.out.println(message);

        assert message.contains("notsowide") : "Message missing words";
        assert message.contains("2.") : "Message missing place";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("silver") : "Message missing badge";
    }

    @Test
    public void testBronzeBadge() {
        String message = challengeMessageService.getBadgeText(3, challengeMessageService.getBronzeBadgeUrl(), challenge);

        System.out.println(message);

        assert message.contains("notsowide") : "Message missing words";
        assert message.contains("3.") : "Message missing place";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("bronze") : "Message missing badge";
    }

    @Test
    public void testGetResultsForumTitle() {
        String message = challengeMessageService.getResultsForumTitle(challenge);

        System.out.println(message);

        assert message.contains("Resultatene fra oppgaven") : "Message missing words";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
    }

    @Test
    public void testGetResultsForumSingle() {
        TestPhotographerItem photographer = new TestPhotographerItem();
        photographer.setId("Test Photographer ID");
        photographer.setAdministratorFlag(false);
        photographer.setFullname("Test Photographer");
        photographer.setUsername("testPhotographer");
        photographer.setIconUrl("http://iconUrl");
        photographer.setTwitter("twitter");

        TestImageItem image = new TestImageItem();
        image.setVoteCount(43L);
        image.setPhotographer(photographer);
        image.setId("TestImageId");
        image.setUrl("http://test.image.home.page");
        image.setRank(1L);
        image.setTitle("Test Image");
        image.setImageUrl("http://test.image.picture.link");

        String message = challengeMessageService.getResultsForumSingle(image);

        System.out.println(message);

        assert message.contains("stemmer") : "Message missing words";
        assert message.contains("43") : "Message missing votes";
        assert message.contains(image.getPhotographer().getName()) : "Message missing photographer";
        assert message.contains(image.getTitle()) : "Message missing title";
        assert message.contains(image.getUrl()) : "Message missing home page";
        assert message.contains(image.getImageUrl()) : "Message missing picture link";
    }

    @Test
    public void testGetResultsForumText() {
        TestPhotographerItem photographer1 = new TestPhotographerItem();
        photographer1.setId("Test Photographer ID 1");
        photographer1.setAdministratorFlag(false);
        photographer1.setFullname("Test Photographer 1");
        photographer1.setUsername("testPhotographer1");
        photographer1.setIconUrl("http://iconUrl1");
        photographer1.setTwitter("twitter1");

        TestPhotographerItem photographer2 = new TestPhotographerItem();
        photographer2.setId("Test Photographer ID 2");
        photographer2.setAdministratorFlag(false);
        photographer2.setFullname("Test Photographer 2");
        photographer2.setUsername("testPhotographer2");
        photographer2.setIconUrl("http://iconUrl2");
        photographer2.setTwitter("twitter2");

        TestPhotographerItem photographer3 = new TestPhotographerItem();
        photographer3.setId("Test Photographer ID 3");
        photographer3.setAdministratorFlag(false);
        photographer3.setFullname("Test Photographer 3");
        photographer3.setUsername("testPhotographer3");
        photographer3.setIconUrl("http://iconUrl3");
        photographer3.setTwitter("twitter3");

        TestImageItem image1 = new TestImageItem();
        image1.setVoteCount(43L);
        image1.setPhotographer(photographer1);
        image1.setId("TestImageId");
        image1.setUrl("http://test.image.home.page");
        image1.setRank(1L);
        image1.setTitle("Test Image 1");
        image1.setImageUrl("http://test.image.picture.link");

        TestImageItem image2 = new TestImageItem();
        image2.setVoteCount(38L);
        image2.setPhotographer(photographer2);
        image2.setId("TestImageId2");
        image2.setUrl("http://test.image2.home.page");
        image2.setRank(2L);
        image2.setTitle("Test Image 2");
        image2.setImageUrl("http://test.image2.picture.link");


        TestImageItem image3 = new TestImageItem();
        image3.setVoteCount(27L);
        image3.setPhotographer(photographer3);
        image3.setId("TestImageId3");
        image3.setUrl("http://test.image3.home.page");
        image3.setRank(3L);
        image3.setTitle("Test Image 3");
        image3.setImageUrl("http://test.image3.picture.link");

        String message = challengeMessageService.getResultsForumText(challengeMessageService.getResultsUrl(challenge),
                challengeMessageService.getResultsForumSingle(image1),
                challengeMessageService.getResultsForumSingle(image2),
                challengeMessageService.getResultsForumSingle(image3));

        System.out.println(message);

        assert message.contains("Andre plass") : "Message missing words";
        assert message.contains("43") : "Message missing votes";
        assert message.contains("38") : "Message missing votes";
        assert message.contains("27") : "Message missing votes";
        assert message.contains(image1.getPhotographer().getName()) : "Message missing photographer 1";
        assert message.contains(image1.getTitle()) : "Message missing title 1";
        assert message.contains(image1.getUrl()) : "Message missing home page 1";
        assert message.contains(image1.getImageUrl()) : "Message missing picture link 1";
        assert message.contains(image2.getPhotographer().getName()) : "Message missing photographer 2";
        assert message.contains(image2.getTitle()) : "Message missing title 2";
        assert message.contains(image2.getUrl()) : "Message missing home page 2";
        assert message.contains(image2.getImageUrl()) : "Message missing picture link 2";
        assert message.contains(image3.getPhotographer().getName()) : "Message missing photographer 3";
        assert message.contains(image3.getTitle()) : "Message missing title 3";
        assert message.contains(image3.getUrl()) : "Message missing home page 3";
        assert message.contains(image3.getImageUrl()) : "Message missing picture link 3";
        assert message.contains("http") : "Message missing link";
        assert !resultsUrl.contains("bit.ly") : "Message link was shortened";
    }

    @Test
    public void testVotingOpenWarning() {
        String message = challengeMessageService.getVotingOpenWarning(challenge);

        System.out.println(message);

        assert message.contains("Husk") : "Message started incorrectly";
        assert message.contains("legge til bilde i") : "Message has wrong text";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("bit.ly") : "Message missing link";
    }

    @Test
    public void testVotingCloseWarning() {
        String message = challengeMessageService.getVotingCloseWarning(challenge);

        System.out.println(message);

        assert message.contains("Husk") : "Message started incorrectly";
        assert message.contains("stemme i") : "Message has wrong text";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getTitle()) : "Message missing name";
        assert message.contains("bit.ly") : "Message missing link";
    }
}
