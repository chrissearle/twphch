package net.chrissearle.flickrvote.flickr;

public class FlickrAuth {
    private final String flickrId;
    private final String token;
    private final String username;
    private final String realname;

    public FlickrAuth(String flickrId, String token, String username, String realname) {
        this.flickrId = flickrId;
        this.token = token;
        this.username = username;
        this.realname = realname;
    }

    public FlickrAuth(FlickrAuth flickrAuth) {
        this.flickrId = flickrAuth.getFlickrId();
        this.token = flickrAuth.getToken();
        this.username = flickrAuth.getUsername();
        this.realname = flickrAuth.getRealname();
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
}
