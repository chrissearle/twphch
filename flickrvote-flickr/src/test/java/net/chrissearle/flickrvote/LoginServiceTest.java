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
import net.chrissearle.flickrvote.flickr.FlickrLoginService;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.flickr.impl.FlickrJLoginService;
import net.chrissearle.flickrvote.flickr.impl.FlickrJUserDAO;
import org.constretto.ConstrettoBuilder;
import org.constretto.ConstrettoConfiguration;
import org.springframework.core.io.DefaultResourceLoader;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

public class LoginServiceTest {
    FlickrLoginService service;

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

        service = new FlickrJLoginService(flickr, new FlickrJUserDAO(flickr));
    }

    @BeforeTest(groups = "broken")
    public void setupBroken() throws IOException, ParserConfigurationException {
        Flickr flickr = new Flickr("", "", new REST());

        service = new FlickrJLoginService(flickr, new FlickrJUserDAO(flickr));
    }

    @Test(groups = "broken", expectedExceptions = FlickrServiceException.class)
    public void testGetUrlError() {
        service.getLoginUrl();
    }

    @Test(groups = "configured")
    public void testGetUrl() {
        URL url = service.getLoginUrl();

        assert url != null : "LoginUrl was null";
        assert url.toExternalForm().contains("flickr") : "LoginUrl did not point to flickr";
    }
}