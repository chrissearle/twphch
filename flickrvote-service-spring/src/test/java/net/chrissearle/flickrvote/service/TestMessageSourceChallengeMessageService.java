package net.chrissearle.flickrvote.service;

import com.rosaloves.net.shorturl.bitly.Bitly;
import com.rosaloves.net.shorturl.bitly.BitlyFactory;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class TestMessageSourceChallengeMessageService {
    private ChallengeMessageService challengeMessageService;
    private Challenge challenge;
    private String resultsUrl;

    @BeforeClass
    public void setup() throws Exception {
        File configuration = new File("/etc/flickrvote.properties");

        InputStream in = new FileInputStream(configuration);
        Properties properties = new Properties();
        properties.load(in);

        Bitly bitly = BitlyFactory.newInstance(properties.getProperty("bitly.login"),
                properties.getProperty("bitly.key"));

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

        challenge = new Challenge("TestTag", "Test Name", startDate.toDate(), voteDate.toDate(), endDate.toDate());
    }

    @Test
    public void testGetVotingTwitter() {
        String message = challengeMessageService.getVotingTwitter(challenge);

        System.out.println(message);

        assert message.contains("Avstemning") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getName()) : "Message missing name";
        assert message.contains("bit.ly") : "Message missing link";
    }

    @Test
    public void testGetCurrentTwitter() {
        String message = challengeMessageService.getCurrentTwitter(challenge);

        System.out.println(message);

        assert message.contains("Oppgaven") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getName()) : "Message missing name";
        assert message.contains("bit.ly") : "Message missing link";
    }

    @Test
    public void testGetResultsUrl() {
        resultsUrl = challengeMessageService.getResultsUrl(challenge);

        assert resultsUrl.contains("http") : "Message missing link";
        assert !resultsUrl.contains("bit.ly") : "Message link was shortened";
    }

    @Test(dependsOnMethods = {"testGetResultsUrl"})
    public void testGetResultsTwitter() {
        String message = challengeMessageService.getResultsTwitter(challenge, resultsUrl);

        System.out.println(message);

        assert message.contains("Resultatene fra oppgaven") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getName()) : "Message missing name";
        assert message.contains("bit.ly") : "Message missing link";
    }

    @Test
    public void testGetVotingForumTitle() {
        String message = challengeMessageService.getVotingForumTitle(challenge);

        System.out.println(message);

        assert message.contains("Avstemning") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getName()) : "Message missing name";
    }

    @Test
    public void testGetCurrentForumTitle() {
        String message = challengeMessageService.getCurrentForumTitle(challenge);

        System.out.println(message);

        assert message.contains("Oppgaven") : "Message started incorrectly";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getName()) : "Message missing name";
    }

    @Test
    public void testGetVotingForumText() {
        String message = challengeMessageService.getVotingForumText(challenge);

        System.out.println(message);

        assert message.contains("tillatt") : "Message missing words";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getName()) : "Message missing name";
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
        assert message.contains(challenge.getName()) : "Message missing name";
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
        assert message.contains(challenge.getName()) : "Message missing name";
        assert message.contains("gold") : "Message missing badge";
    }

    @Test
    public void testSilverBadge() {
        String message = challengeMessageService.getBadgeText(2, challengeMessageService.getSilverBadgeUrl(), challenge);

        System.out.println(message);

        assert message.contains("notsowide") : "Message missing words";
        assert message.contains("2.") : "Message missing place";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getName()) : "Message missing name";
        assert message.contains("silver") : "Message missing badge";
    }

    @Test
    public void testBronzeBadge() {
        String message = challengeMessageService.getBadgeText(3, challengeMessageService.getBronzeBadgeUrl(), challenge);

        System.out.println(message);

        assert message.contains("notsowide") : "Message missing words";
        assert message.contains("3.") : "Message missing place";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getName()) : "Message missing name";
        assert message.contains("bronze") : "Message missing badge";
    }

    @Test
    public void testGetResultsForumTitle() {
        String message = challengeMessageService.getResultsForumTitle(challenge);

        System.out.println(message);

        assert message.contains("Resultatene fra oppgaven") : "Message missing words";
        assert message.contains(challenge.getTag()) : "Message missing tag";
        assert message.contains(challenge.getName()) : "Message missing name";
    }

    @Test
    public void testGetResultsForumSingle() {
        ImageInfo image = new ImageInfo();
        image.setFinalVoteCount(43L);
        image.setPhotographerName("Test Photographer");
        image.setId("TestImageId");
        image.setImageHomePage("http://test.image.home.page");
        image.setRank(1L);
        image.setTitle("Test Image");
        image.setImagePictureLink("http://test.image.picture.link");

        String message = challengeMessageService.getResultsForumSingle(image);

        System.out.println(message);

        assert message.contains("stemmer") : "Message missing words";
        assert message.contains("43") : "Message missing votes";
        assert message.contains(image.getPhotographerName()) : "Message missing photographer";
        assert message.contains(image.getTitle()) : "Message missing title";
        assert message.contains(image.getImageHomePage()) : "Message missing home page";
        assert message.contains(image.getImagePictureLink()) : "Message missing picture link";
    }

    @Test
    public void testGetResultsForumText() {
        ImageInfo image1 = new ImageInfo();
        image1.setFinalVoteCount(43L);
        image1.setPhotographerName("Test Photographer 1");
        image1.setId("TestImageId");
        image1.setImageHomePage("http://test.image1.home.page");
        image1.setRank(1L);
        image1.setTitle("Test Image 1");
        image1.setImagePictureLink("http://test.image1.picture.link");

        ImageInfo image2 = new ImageInfo();
        image2.setFinalVoteCount(38L);
        image2.setPhotographerName("Test Photographer 2");
        image2.setId("TestImageId2");
        image2.setImageHomePage("http://test.image2.home.page");
        image2.setRank(2L);
        image2.setTitle("Test Image 2");
        image2.setImagePictureLink("http://test.image2.picture.link");

        ImageInfo image3 = new ImageInfo();
        image3.setFinalVoteCount(27L);
        image3.setPhotographerName("Test Photographer 3");
        image3.setId("TestImageId3");
        image3.setImageHomePage("http://test.image3.home.page");
        image3.setRank(3L);
        image3.setTitle("Test Image 3");
        image3.setImagePictureLink("http://test.image3.picture.link");

        String message = challengeMessageService.getResultsForumText(challengeMessageService.getResultsUrl(challenge),
                challengeMessageService.getResultsForumSingle(image1),
                challengeMessageService.getResultsForumSingle(image2),
                challengeMessageService.getResultsForumSingle(image3));

        System.out.println(message);

        assert message.contains("Andre plass") : "Message missing words";
        assert message.contains("43") : "Message missing votes";
        assert message.contains("38") : "Message missing votes";
        assert message.contains("27") : "Message missing votes";
        assert message.contains(image1.getPhotographerName()) : "Message missing photographer 1";
        assert message.contains(image1.getTitle()) : "Message missing title 1";
        assert message.contains(image1.getImageHomePage()) : "Message missing home page 1";
        assert message.contains(image1.getImagePictureLink()) : "Message missing picture link 1";
        assert message.contains(image2.getPhotographerName()) : "Message missing photographer 2";
        assert message.contains(image2.getTitle()) : "Message missing title 2";
        assert message.contains(image2.getImageHomePage()) : "Message missing home page 2";
        assert message.contains(image2.getImagePictureLink()) : "Message missing picture link 2";
        assert message.contains(image3.getPhotographerName()) : "Message missing photographer 3";
        assert message.contains(image3.getTitle()) : "Message missing title 3";
        assert message.contains(image3.getImageHomePage()) : "Message missing home page 3";
        assert message.contains(image3.getImagePictureLink()) : "Message missing picture link 3";
        assert message.contains("http") : "Message missing link";
        assert !resultsUrl.contains("bit.ly") : "Message link was shortened";
    }
}
