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

import net.chrissearle.flickrvote.flickr.impl.FlickrJLoginService;

public class LoginServiceTest extends FlickrJLoginService {
//    String token;
//
//    @BeforeTest(groups = "configured")
//    public void setupConfigured() throws IOException, ParserConfigurationException {
//        InputStream in = getClass().getResourceAsStream("/flickrvote-flickr.properties");
//        Properties properties = new Properties();
//        properties.load(in);
//
//        flickr = new Flickr(properties.getProperty("flickr.key"),
//                properties.getProperty("flickr.secret"), new REST());
//
//        adminAuthToken = properties.getProperty("flickr.admin.auth.token");
//
//        this.token = properties.getProperty("flickr.test.token");
//    }
//
//    @BeforeTest(groups = "broken")
//    public void setupBroken() throws IOException, ParserConfigurationException {
//        flickr = new Flickr("", "", new REST());
//    }
//
//    @Test(groups = "broken", expectedExceptions = FlickrServiceException.class)
//    public void testGetUrlError() {
//        getLoginUrl();
//    }
//
//    @Test(groups = "configured")
//    public void testGetUrl() {
//        URL url = getLoginUrl();
//
//        assert url != null : "LoginUrl was null";
//        assert url.toExternalForm().contains("flickr") : "LoginUrl did not point to flickr";
//    }
}