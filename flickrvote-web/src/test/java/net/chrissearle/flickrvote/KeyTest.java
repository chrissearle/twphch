package net.chrissearle.flickrvote;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import net.chrissearle.flickrvote.service.FlickrService;
import net.chrissearle.flickrvote.service.FlickrServiceException;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;

@ContextConfiguration(locations = {"/applicationContext.xml /testDbContext.xml"})
public class KeyTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private FlickrService flickrService;

    @Test
    public void testKey() throws FlickrServiceException {
        flickrService.echo();
    }
}
