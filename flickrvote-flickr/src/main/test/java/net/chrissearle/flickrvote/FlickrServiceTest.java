package net.chrissearle.flickrvote;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.REST;
import net.chrissearle.flickrvote.flickr.FlickrAuth;
import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.flickr.FlickrJFlickrService;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class FlickrServiceTest extends FlickrJFlickrService {
    String token;

    @BeforeTest(groups = "configured")
    public void setupConfigured() throws IOException, ParserConfigurationException {
        InputStream in = getClass().getResourceAsStream("/flickrvote-flickr.properties");
        Properties properties = new Properties();
        properties.load(in);

        flickr = new Flickr(properties.getProperty("flickr.key"),
                properties.getProperty("flickr.secret"), new REST());

        this.token = properties.getProperty("flickr.test.token");
    }

    @BeforeTest(groups = "broken")
    public void setupBroken() throws IOException, ParserConfigurationException {
        flickr = new Flickr("", "", new REST());
    }

    @Test(groups = "broken", expectedExceptions = FlickrServiceException.class)
    public void testGetUrlError() {
        getLoginUrl();
    }

    @Test(groups = "configured")
    public void testGetUrl() {
        URL url = getLoginUrl();

        assert url != null : "LoginUrl was null";
        assert url.toExternalForm().contains("flickr") : "LoginUrl did not point to flickr";
    }

    @Test(groups = "configured")
    public void testAuthenticate() {

        FlickrAuth flickrAuth = checkAuthenticate(token);

        assert flickrAuth != null : "Flickr authentication failed";
        assert flickrAuth.getToken().equals(token) : "Token mismatch";
        assert "Chris Searle".equals(flickrAuth.getRealname()) : "User name incorrect";
    }

    @Test(groups = "configured")
    public void testSearchTags() {
        List<FlickrImage> images = searchImagesByTag("#TwPhCh001", null);

        assert images != null : "Image list was null";
        assert images.size() > 30 : "Not enough images";
    }

    public void postOpenVote(String tag, String name, Date endDate) {
    }

    public void postNewChallenge(String tag, String name, Date votingOpenDate, Date endDate) {
    }

    public void postResultsAndAddBadges(String tag, String name, List<FlickrImage> firstPlace, List<FlickrImage> secondPlace, List<FlickrImage> thirdPlace) {
    }
}
