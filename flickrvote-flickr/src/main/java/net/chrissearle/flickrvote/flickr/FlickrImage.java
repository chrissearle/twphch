package net.chrissearle.flickrvote.flickr;

import java.util.Date;

public class FlickrImage {
    private final String flickrId;
    private final String title;
    private final String url;
    private final String imageUrl;
    private final Date postedDate;
    private FlickrPhotographer photographer;

    private Date takenDate;

    public FlickrImage(String flickrId, FlickrPhotographer photographer, String title, String url, String imageUrl, Date takenDate, Date postedDate) {
        this.flickrId = flickrId;
        this.photographer = photographer;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.takenDate = takenDate;
        this.postedDate = postedDate;
    }

    public String getFlickrId() {
        return flickrId;
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

    public FlickrPhotographer getPhotographer() {
        return photographer;
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
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", postedDate=" + postedDate +
                ", photographer=" + photographer +
                ", takenDate=" + takenDate +
                '}';
    }
}
