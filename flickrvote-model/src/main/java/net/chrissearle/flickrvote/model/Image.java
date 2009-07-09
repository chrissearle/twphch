package net.chrissearle.flickrvote.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Column(length = 100, name = "title")
    private String title;

    @ManyToOne
    private Challenge challenge;

    @ManyToOne
    private Photographer photographer;

    @Id
    @Column(name = "flickr_id", length = 50)
    private String id;

    @Column(name = "page", length = 255)
    private String page;

    @Column(name = "medium_image", length = 255)
    private String mediumImage;

    @Override
    public String toString() {
        return new StringBuilder().append("ID: ").append(getId()).append(", TITLE: ").append(getTitle())
                .toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Photographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Image)) {
            return false;
        }

        Image img = (Image) obj;

            return getId().equals(img.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}