/*
 * Copyright 2009 Chris Searle
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

package net.chrissearle.flickrvote;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.REST;
import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.flickr.impl.FlickrJFlickrService;
import org.constretto.ConstrettoBuilder;
import org.constretto.ConstrettoConfiguration;
import org.springframework.core.io.DefaultResourceLoader;
import org.testng.annotations.BeforeTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class FlickrServiceTest {
    String token;

    FlickrService service;

    @BeforeTest(groups = "configured")
    public void setupConfigured() throws IOException, ParserConfigurationException {
        ConstrettoConfiguration conf = new ConstrettoBuilder()
                .createPropertiesStore()
                .addResource(new DefaultResourceLoader().getResource("classpath:flickrvote.properties"))
                .done()
                .getConfiguration();

        Flickr flickr = new Flickr(conf.evaluateToString("flickr.key"),
                conf.evaluateToString("flickr.secret"),
                new REST());

        FlickrJFlickrService service = new FlickrJFlickrService(flickr, null);

        service.configure(conf.evaluateToString("flickr.test.token"), false);

        this.service = service;
    }

    @BeforeTest(groups = "broken")
    public void setupBroken() throws IOException, ParserConfigurationException {
        Flickr flickr = new Flickr("", "", new REST());

        service = new FlickrJFlickrService(flickr, null);
    }

    // Moving out of flickr service
//    @Test(groups = "configured")
//    public void testSearchTags() {
//        List<FlickrImage> images = service.searchImagesByTag("#TwPhCh001", null);
//
//        assert images != null : "Image list was null";
//        // Hard to know what to set here as the image count varies over time. But we had more than 25 entries
//        // for this tag.
//        assert images.size() > 10 : "Not enough images";
//    }

    public void postOpenVote(String tag, String name, Date endDate) {
    }

    public void postNewChallenge(String tag, String name, Date votingOpenDate, Date endDate) {
    }

    public void postResultsAndAddBadges(String tag, String name, List<FlickrImage> firstPlace, List<FlickrImage> secondPlace, List<FlickrImage> thirdPlace) {
    }
}
