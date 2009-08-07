package net.chrissearle.flickrvote.web.model;

import net.chrissearle.flickrvote.service.model.ImageItem;

public class ScoreAdmin {
    String photographerName;
    String imageTitle;
    Long score;
    String imageId;

    public ScoreAdmin(ImageItem image) {
        photographerName = image.getPhotographer().getName();
        imageTitle = image.getTitle();
        score = image.getVoteCount();
        imageId = image.getId();
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
