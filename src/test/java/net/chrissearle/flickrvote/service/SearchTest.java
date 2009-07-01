package net.chrissearle.flickrvote.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration(locations = {"/applicationContext.xml"})
public class SearchTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private FlickrService flickrService;

    @Test
    public void testSearchForTag() throws FlickrServiceException {
        List photos = flickrService.searchForPhotosWithTag("#TwPhCh001");

        assert photos != null : "Photo list was null";
        assert 34 >= photos.size() : "Number of photos was incorrect: " + photos.size();
    }
}
