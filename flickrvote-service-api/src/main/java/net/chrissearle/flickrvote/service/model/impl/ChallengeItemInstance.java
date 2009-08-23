package net.chrissearle.flickrvote.service.model.impl;

import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ImageItem;

import java.util.Set;

public class ChallengeItemInstance implements ChallengeItem {
    private static final long serialVersionUID = -4088537902778903669L;

    private String title;
    private String description;

    private Set<ImageItem> images;

    public ChallengeItemInstance(String title, String description, Set<ImageItem> images) {
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