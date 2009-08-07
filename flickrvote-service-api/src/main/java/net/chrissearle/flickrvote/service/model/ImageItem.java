package net.chrissearle.flickrvote.service.model;

import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.model.Image;

import java.util.Date;

public class ImageItem {
    final private String id;
    final private String title;
    final private String url;
    final private String imageUrl;
    final private Date postedDate;
    final private Long voteCount;
    final private Long rank;

    final private PhotographerItem photographer;
    final private ChallengeSummary challenge;

    public ImageItem(FlickrImage image) {
        this.id = image.getFlickrId();
        this.title = image.getTitle();
        this.url = image.getUrl();
        this.imageUrl = image.getImageUrl();
        this.postedDate = image.getPostedDate();
        this.voteCount = 0L;
        this.rank = 0L;

        this.photographer = new PhotographerItem(image.getPhotographer());
        this.challenge = null;
    }

    public ImageItem(Image image) {
        this.id = image.getId();
        this.title = image.getTitle();
        this.url = image.getPage();
        this.imageUrl = image.getMediumImage();
        this.postedDate = image.getPostedDate();
        switch (image.getChallenge().getVotingState()) {
            case CLOSED:
                this.voteCount = image.getFinalVoteCount();
                this.rank = image.getFinalRank();
                break;
            case VOTING:
                this.voteCount = (long) image.getVotes().size();
                this.rank = 0L;
                break;
            case OPEN:
                this.voteCount = 0L;
                this.rank = 0L;
                break;
            default:
                this.voteCount = 0L;
                this.rank = 0L;
        }

        this.photographer = new PhotographerItem(image.getPhotographer());
        this.challenge = new ChallengeSummary(image.getChallenge());
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public PhotographerItem getPhotographer() {
        return photographer;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public Long getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public ChallengeSummary getChallenge() {
        return challenge;
    }
}
