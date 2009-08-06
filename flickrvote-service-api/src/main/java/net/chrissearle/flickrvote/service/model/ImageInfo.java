package net.chrissearle.flickrvote.service.model;

import net.chrissearle.flickrvote.model.Image;

import java.util.Date;

@Deprecated
public class ImageInfo {
    private String id;
    private String title;
    private String imageHomePage;
    private String imagePictureLink;
    private String photographerName;
    private Long finalVoteCount;
    private Long rank = 0L;
    private Long voteCount = 0L;
    private String challengeName;
    private String challengeTag;
    private Date postedDate;

    public ImageInfo() {
    }

    public ImageInfo(Image image) {
        this.setId(image.getId());
        this.setTitle(image.getTitle());
        this.setImageHomePage(image.getPage());
        this.setImagePictureLink(image.getMediumImage());
        this.setChallengeName(image.getChallenge().getName());
        this.setChallengeTag(image.getChallenge().getTag());
        this.setRank(image.getFinalRank());
        this.setPostedDate(image.getPostedDate());

        String name = image.getPhotographer().getFullname();

        if (name == null || "".equals(name)) {
            name = image.getPhotographer().getUsername();
        }

        this.setPhotographerName(name);

        this.setFinalVoteCount(image.getFinalVoteCount());

        if (image.getVotes() != null) {
            this.voteCount = (long) image.getVotes().size();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageHomePage() {
        return imageHomePage;
    }

    public void setImageHomePage(String imageHomePage) {
        this.imageHomePage = imageHomePage;
    }

    public String getImagePictureLink() {
        return imagePictureLink;
    }

    public void setImagePictureLink(String imagePictureLink) {
        this.imagePictureLink = imagePictureLink;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName;
    }

    public Long getFinalVoteCount() {
        return finalVoteCount;
    }

    public void setFinalVoteCount(Long finalVoteCount) {
        this.finalVoteCount = finalVoteCount;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeTag() {
        return challengeTag;
    }

    public void setChallengeTag(String challengeTag) {
        this.challengeTag = challengeTag;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }
}
