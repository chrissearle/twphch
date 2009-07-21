package net.chrissearle.flickrvote.flickr;

import java.util.Date;

public class FlickrImage {
    private final String flickrId;
    private final String photographerName;
    private final String title;
    private final String url;
    private final String imageUrl;
    private String photographerFlickrId;

    private Date dateTaken;

    public FlickrImage(String flickrId, String photographerName, String photoId, String title, String url, String imageUrl, Date dateTaken) {
        this.flickrId = flickrId;
        this.photographerName = photographerName;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.setPhotographerFlickrId(photoId);
        this.dateTaken = dateTaken;
    }

    public FlickrImage(FlickrImage flickrImage) {
        this.flickrId = flickrImage.getFlickrId();
        this.photographerName = flickrImage.getPhotographerName();
        this.title = flickrImage.getTitle();
        this.url = flickrImage.getUrl();
        this.imageUrl = flickrImage.getImageUrl();
        this.photographerFlickrId = flickrImage.getPhotographerFlickrId();
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

    public String getPhotographerFlickrId() {
        return photographerFlickrId;
    }

    public void setPhotographerFlickrId(String photographerFlickrId) {
        this.photographerFlickrId = photographerFlickrId;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    @Override
    public String toString() {
        return "FlickrImage{" +
                "flickrId='" + flickrId + '\'' +
                ", photographerName='" + photographerName + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", photographerFlickrId='" + photographerFlickrId + '\'' +
                ", dateTaken=" + dateTaken +
                '}';
    }
}
