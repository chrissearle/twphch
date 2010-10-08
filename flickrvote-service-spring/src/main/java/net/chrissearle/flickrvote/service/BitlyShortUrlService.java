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

import com.rosaloves.bitlyj.Url;
import static com.rosaloves.bitlyj.Jmp.*;

import org.constretto.annotation.Configuration;
import org.constretto.annotation.Configure;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.rosaloves.bitlyj.Bitly.shorten;

@Service("shortUrlService")
public class BitlyShortUrlService implements ShortUrlService {
    private Logger logger = Logger.getLogger(BitlyShortUrlService.class.getName());

    private String login;
    private String key;

    @Configure
    public void configure(@Configuration(expression = "bitly.login") String login,
                          @Configuration(expression = "bitly.key") String key) {
        this.login = login;
        this.key = key;
    }


    /**
     * Shortens a URL via bit.ly.
     *
     * @param longUrl
     * @return shortened form
     */
    public String shortenUrl(String longUrl) {
        Url url = as(this.login, this.key).call(shorten(longUrl));

        return url.getShortUrl();
    }
}
