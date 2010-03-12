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

package net.chrissearle.flickrvote.service.cli;

import net.chrissearle.flickrvote.service.ShortUrlService;

public class BitlyShortUrlCli extends AbstractCliService {
    public void shortenUrl(String longUrl) {
        ShortUrlService service = (ShortUrlService) context.getBean("shortUrlService");

        String url = service.shortenUrl(longUrl);

        System.out.println(url);
    }

    public static void main(String[] args) {
        BitlyShortUrlCli app = new BitlyShortUrlCli();
        app.initialize();

        app.shortenUrl(args[0]);
    }
}
