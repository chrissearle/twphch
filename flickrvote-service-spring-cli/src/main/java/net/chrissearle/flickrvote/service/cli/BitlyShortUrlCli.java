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
