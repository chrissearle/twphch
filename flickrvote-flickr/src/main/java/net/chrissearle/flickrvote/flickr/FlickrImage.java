package net.chrissearle.flickrvote.flickr;

public class FlickrImage {
    private final String flickrId;
    private final String ownerId;
    private final String title;
    private final String url;
    private final String imageUrl;

    public FlickrImage(String flickrId, String ownerId, String title, String url, String imageUrl) {
        this.flickrId = flickrId;
        this.ownerId = ownerId;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public FlickrImage(FlickrImage flickrImage) {
        this.flickrId = flickrImage.getFlickrId();
        this.ownerId = flickrImage.getOwnerId();
        this.title = flickrImage.getTitle();
        this.url = flickrImage.getUrl();
        this.imageUrl = flickrImage.getImageUrl();
    }

    public String getFlickrId() {
        return flickrId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
