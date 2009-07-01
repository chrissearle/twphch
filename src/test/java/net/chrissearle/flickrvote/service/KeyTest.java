package net.chrissearle.flickrvote.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class KeyTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private FlickrService flickrService;

    @Test
    public void testKey() throws FlickrServiceException {
        flickrService.echo();
    }
}
