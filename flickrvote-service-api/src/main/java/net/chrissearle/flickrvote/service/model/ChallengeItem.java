package net.chrissearle.flickrvote.service.model;

import java.util.Set;

public class ChallengeItem {
    private String title;
    private String description;

    private Set<ImageItem> images;

    public ChallengeItem(String title, String description, Set<ImageItem> images) {
        this.description = description;
        this.title = title;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<ImageItem> getImages() {
        return images;
    }
}