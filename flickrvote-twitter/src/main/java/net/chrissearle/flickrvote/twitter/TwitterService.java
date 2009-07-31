package net.chrissearle.flickrvote.twitter;

public interface TwitterService {
    void twitter(String text);

    void follow(String twitter);

    boolean twitterExists(String twitter);
}
