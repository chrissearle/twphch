package net.chrissearle.flickrvote.service;

import com.aetrion.flickr.people.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class AuthTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private FlickrService flickrService;

    @Test
    public void testGetToken() throws FlickrServiceException {
        flickrService.testAddToken("chris", "72157620807259230-3fe63a2bc7d0a4c3");

        User user = flickrService.getUser("chris");

        assert user != null : "User was null";
        assert "Chris Searle".equals(user.getRealName()) : "User real name incorrect: " + user.getRealName();
    }

}
