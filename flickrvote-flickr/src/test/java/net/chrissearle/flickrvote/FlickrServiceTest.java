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
import net.chrissearle.flickrvote.flickr.impl.FlickrJFlickrService;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
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

        adminAuthToken = properties.getProperty("flickr.admin.auth.token");

        this.token = properties.getProperty("flickr.test.token");
    }

    @BeforeTest(groups = "broken")
    public void setupBroken() throws IOException, ParserConfigurationException {
        flickr = new Flickr("", "", new REST());
    }

    @Test(groups = "configured")
    public void testSearchTags() {
        List<FlickrImage> images = searchImagesByTag("#TwPhCh001", null);

        assert images != null : "Image list was null";
        // Hard to know what to set here as the image count varies over time. But we had more than 25 entries
        // for this tag.
        assert images.size() > 10 : "Not enough images";
    }

    public void postOpenVote(String tag, String name, Date endDate) {
    }

    public void postNewChallenge(String tag, String name, Date votingOpenDate, Date endDate) {
    }

    public void postResultsAndAddBadges(String tag, String name, List<FlickrImage> firstPlace, List<FlickrImage> secondPlace, List<FlickrImage> thirdPlace) {
    }
}
