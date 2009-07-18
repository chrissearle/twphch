package net.chrissearle.flickrvote.service;

import com.rosaloves.net.shorturl.bitly.Bitly;
import com.rosaloves.net.shorturl.bitly.url.BitlyUrl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BitlyShortUrlService implements ShortUrlService {
    private Logger logger = Logger.getLogger(BitlyShortUrlService.class);

    private Bitly bitly;

    @Autowired
    public BitlyShortUrlService(Bitly bitly) {
        this.bitly = bitly;
    }

    /**
     * Shortens a URL via bit.ly. If anything fails - returns the original URL
     *
     * @param longUrl
     * @return shortened form
     */
    public String shortenUrl(String longUrl) {
        try {
            BitlyUrl shortUrl = bitly.shorten(longUrl);

            return shortUrl.getShortUrl().toExternalForm();
        } catch (IOException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to shorten longUrl: " + longUrl);
            }
            return longUrl;
        }
    }
}
