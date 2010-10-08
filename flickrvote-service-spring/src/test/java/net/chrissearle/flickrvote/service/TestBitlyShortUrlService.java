/*
 * Copyright 2010 Chris Searle
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

package net.chrissearle.flickrvote.service;

import org.constretto.ConstrettoBuilder;
import org.constretto.ConstrettoConfiguration;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;

@RunWith(JUnit4ClassRunner.class)
public class TestBitlyShortUrlService {
    ShortUrlService service;

    @Before
    public void setUp() throws IOException {
        ConstrettoConfiguration conf = new ConstrettoBuilder()
                .createPropertiesStore()
                .addResource(new DefaultResourceLoader().getResource("classpath:flickrvote.properties"))
                .done()
                .createPropertiesStore()
                .addResource(new DefaultResourceLoader().getResource("file:/etc/flickrvote/flickrvote.properties"))
                .done()
                .getConfiguration();

        service = new BitlyShortUrlService();
        ((BitlyShortUrlService)service).configure(conf.evaluateToString("bitly.login"), conf.evaluateToString("bitly.key"));
    }

    @Test
    public void testShortenUrl() {
        String shortUrl = service.shortenUrl("http://vote.twphch.com/twitterphotochallenge");

        assertTrue("Short URL did not point to j.mp " + shortUrl, shortUrl.contains("j.mp"));

        System.out.println(shortUrl);
    }
}
