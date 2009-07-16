package net.chrissearle.flickrvote.service.model;

import net.chrissearle.flickrvote.model.Image;

public class ImageInfo {
    private String id;
    private String title;
    private String imageHomePage;
    private String imagePictureLink;
    private String photographerName;
    private Long finalVoteCount;
    private Long rank;
    private Long voteCount = 0L;

    public ImageInfo() {}
    
    public ImageInfo(Image image) {
        this.setId(image.getId());
        this.setTitle(image.getTitle());
        this.setImageHomePage(image.getPage());
        this.setImagePictureLink(image.getMediumImage());

        String name = image.getPhotographer().getFullname();

        if (name == null || "".equals(name)) {
            name = image.getPhotographer().getUsername();
        }

        this.setPhotographerName(name);

        this.setFinalVoteCount(image.getFinalVoteCount());
        this.rank = 0L;
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
}
