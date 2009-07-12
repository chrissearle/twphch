package net.chrissearle.flickrvote.service.model;

import net.chrissearle.flickrvote.model.Image;

public class ImageInfo {
    private String title;
    private String imageHomePage;
    private String imagePictureLink;
    private String photographerName;
    private Long finalVoteCount;
    private Long rank;

    public ImageInfo(String title, String imageHomePage, String imagePictureLink, String photographerName, Long finalVoteCount) {
        this.setTitle(title);
        this.setImageHomePage(imageHomePage);
        this.setImagePictureLink(imagePictureLink);
        this.setPhotographerName(photographerName);
        this.setFinalVoteCount(finalVoteCount);
        this.rank = 0L;
    }

    public ImageInfo(Image image) {
        this.setTitle(image.getTitle());
        this.setImageHomePage(image.getPage());
        this.setImagePictureLink(image.getMediumImage());
        this.setPhotographerName(image.getPhotographer().getFullname());
        this.setFinalVoteCount(image.getFinalVoteCount());
        this.rank = 0L;
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
}
