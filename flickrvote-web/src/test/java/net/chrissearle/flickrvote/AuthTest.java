package net.chrissearle.flickrvote;

import com.aetrion.flickr.people.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.chrissearle.flickrvote.service.FlickrService;
import net.chrissearle.flickrvote.service.FlickrServiceException;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;

@ContextConfiguration(locations = {"/applicationContext.xml /testDbContext.xml"})
public class AuthTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private FlickrService flickrService;

    private String token;

    @BeforeTest
    public void loadProperties() throws IOException {
        InputStream in = getClass().getResourceAsStream("/test.properties");
        Properties properties = new Properties();
        properties.load(in);
        token = properties.getProperty("flickr.test.token");
    }

    @Test
    public void testGetToken() throws FlickrServiceException {

        User user = flickrService.getUser("chris");

        assert user != null : "Photographer was null";
        assert "Chris Searle".equals(user.getRealName()) : "Photographer real name incorrect: " + user.getRealName();
    }

}
