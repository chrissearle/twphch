package net.chrissearle.flickrvote.flickr;

import java.util.Date;

public class FlickrImage {
    private final String flickrId;
    private final String photographerName;
    private final String title;
    private final String url;
    private final String imageUrl;
    private final Date postedDate;
    private String photographerFlickrId;

    private Date takenDate;

    public FlickrImage(String flickrId, String photographerName, String photoId, String title, String url, String imageUrl, Date takenDate, Date postedDate) {
        this.flickrId = flickrId;
        this.photographerName = photographerName;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.setPhotographerFlickrId(photoId);
        this.takenDate = takenDate;
        this.postedDate = postedDate;
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

    public Date getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }

    public Date getPostedDate() {
        return postedDate;
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
                ", takenDate=" + takenDate +
                ", postedDate=" + postedDate +
                '}';
    }
}
