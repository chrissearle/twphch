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
        File configuration = new File("/etc/flickrvote.properties");

        InputStream in = new FileInputStream(configuration);
        Properties properties = new Properties();
        properties.load(in);

        Bitly bitly = BitlyFactory.newInstance(properties.getProperty("bitly.login"),
                properties.getProperty("bitly.key"));

        service = new BitlyShortUrlService(bitly);
    }

    @Test
    public void testShortenUrl() {
        String shortUrl = service.shortenUrl("http://www.chrissearle.org/twitterphotochallenge");

        assert shortUrl.contains("bit.ly") : "Short URL did not point to bit.ly";

        System.out.println(shortUrl);
    }
}
