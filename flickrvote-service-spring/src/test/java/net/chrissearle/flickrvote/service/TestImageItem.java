package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.PhotographerItem;

import java.util.Date;

public class TestImageItem implements ImageItem {
    String id;
    String imageUrl;
    String title;
    String url;
    PhotographerItem photographer;
    ChallengeSummary challenge;
    Date postedDate;
    Long rank;
    Long voteCount;

    public ChallengeSummary getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeSummary challenge) {
        this.challenge = challenge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PhotographerItem getPhotographer() {
        return photographer;
    }

    public void setPhotographer(PhotographerItem photographer) {
        this.photographer = photographer;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}
