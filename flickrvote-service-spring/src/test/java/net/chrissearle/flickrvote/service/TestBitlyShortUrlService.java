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

package net.chrissearle.flickrvote.service;

import com.rosaloves.net.shorturl.bitly.Bitly;
import com.rosaloves.net.shorturl.bitly.BitlyFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestBitlyShortUrlService {
    ShortUrlService service;

    @BeforeClass
    public void setUp() throws IOException {
        File configuration = new File("/etc/flickrvote/flickrvote.properties");

        InputStream in = new FileInputStream(configuration);
        Properties properties = new Properties();
        properties.load(in);

        Bitly bitly = BitlyFactory.newInstance(properties.getProperty("bitly.login"),
                properties.getProperty("bitly.key"));

        service = new BitlyShortUrlService(bitly);
    }

    @Test
    public void testShortenUrl() {
        String shortUrl = service.shortenUrl("http://vote.twphch.com/twitterphotochallenge");

        assert shortUrl.contains("bit.ly") : "Short URL did not point to bit.ly";

        System.out.println(shortUrl);
    }
}
