package net.chrissearle.flickrvote.model;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(length = 100, name = "title")
    private String title;

    @ManyToOne
    private Challenge challenge;

    @ManyToOne
    private Photographer photographer;

    @Column(name = "page", length = 255)
    private String page;

    @Column(name = "medium_image", length = 255)
    private String mediumImage;

    @Override
    public String toString() {
        return new StringBuilder().append("ID: ").append(getId()).append(", TITLE: ").append(getTitle())
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        // Simplest equality is id equality - same db object. If id is not present (we haven't persisted the object or
        // we don't have the persistent version then check the page of the image - this is unique to the image.
        if (getId() == null || img.getId() == null) {
            return getPage().equals(img.getPage());
        } else {
            return getId().equals(img.getId());
        }

    }
}