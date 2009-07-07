package net.chrissearle.flickrvote;

import net.chrissearle.flickrvote.impl.FlickrJFlickrService;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.REST;

import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import java.net.URL;

public class FlickrServiceTest extends FlickrJFlickrService {
    @BeforeTest(groups = "configured")
    public void setupConfigured() throws IOException, ParserConfigurationException {
        InputStream in = getClass().getResourceAsStream("/flickrvote-flickr.properties");
        Properties properties = new Properties();
        properties.load(in);

        flickr = new Flickr(properties.getProperty("flickr.key"),
                properties.getProperty("flickr.secret"), new REST());

    }

    @BeforeTest(groups = "broken")
    public void setupBroken() throws IOException, ParserConfigurationException {
        flickr = new Flickr("", "", new REST());
    }

    @Test(groups = "broken", expectedExceptions = FlickrServiceException.class)
    public void testGetUrlError() {
        URL url = getLoginUrl();
    }

    @Test(groups = "configured")
    public void testGetUrl() {
        URL url = getLoginUrl();

        assert url != null : "LoginUrl was null";
        assert url.toExternalForm().contains("flickr") : "LoginUrl did not point to flickr";
    }
}
