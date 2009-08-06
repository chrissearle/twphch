package net.chrissearle.flickrvote.web.model;

import net.chrissearle.flickrvote.service.model.ImageItem;

import java.util.Date;

public class DisplayImage implements Image, Photographer {
    private final String imageId;
    private final String imageTitle;
    private final String imagePageUrl;
    private final String imageUrl;
    private final Date postedDate;
    private final Long voteCount;
    private final Long rank;
    private final String photographerId;
    private final String username;
    private final String fullname;
    private final String twitterAccount;
    private final String iconUrl;
    private final Boolean adminFlag;
    private final Boolean activeFlag;

    public DisplayImage(ImageItem image) {
        this.imageId = image.getId();
        this.imageTitle = image.getTitle();
        this.imagePageUrl = image.getUrl();
        this.imageUrl = image.getImageUrl();
        this.postedDate = image.getPostedDate();
        this.voteCount = image.getVoteCount();
        this.rank = image.getRank();
        this.photographerId = image.getPhotographer().getId();
        this.username = image.getPhotographer().getUsername();
        this.fullname = image.getPhotographer().getFullname();
        this.twitterAccount = image.getPhotographer().getTwitter();
        this.iconUrl = image.getPhotographer().getIconUrl();
        this.adminFlag = image.getPhotographer().isAdministratorFlag();
        this.activeFlag = image.getPhotographer().isActiveFlag();
    }

    public String getImageId() {
        return imageId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getPageUrl() {
        return imagePageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public Long getRank() {
        return rank;
    }

    public String getPhotographerId() {
        return photographerId;
    }

    public String getPhotographerName() {
        if (fullname != null && !"".equals(fullname)) {
            return fullname;
        }
        return username;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Boolean isAdmin() {
        return adminFlag;
    }

    public Boolean isActive() {
        return activeFlag;
    }
}
