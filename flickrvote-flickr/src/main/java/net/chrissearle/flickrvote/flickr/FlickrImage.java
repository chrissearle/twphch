package net.chrissearle.flickrvote.flickr;

public class FlickrImage {
    private final String flickrId;
    private final String photographerName;
    private final String title;
    private final String url;
    private final String imageUrl;

    public FlickrImage(String flickrId, String photographerName, String title, String url, String imageUrl) {
        this.flickrId = flickrId;
        this.photographerName = photographerName;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public FlickrImage(FlickrImage flickrImage) {
        this.flickrId = flickrImage.getFlickrId();
        this.photographerName = flickrImage.getPhotographerName();
        this.title = flickrImage.getTitle();
        this.url = flickrImage.getUrl();
        this.imageUrl = flickrImage.getImageUrl();
    }

    public String getFlickrId() {
        return flickrId;
    }

    public String getPhotographerName() {
        return photographerName;
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
