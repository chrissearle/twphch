package net.chrissearle.flickrvote.flickr;

public class FlickrPhotographer {
    private final String flickrId;
    private final String token;
    private final String username;
    private final String realname;
    private final String iconUrl;

    public FlickrPhotographer(String flickrId, String token, String username, String realname, String iconUrl) {
        this.flickrId = flickrId;
        this.token = token;
        this.username = username;
        this.realname = realname;
        this.iconUrl = iconUrl;
    }

    public String getFlickrId() {
        return flickrId;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRealname() {
        return realname;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
