package net.chrissearle.flickrvote.service.model;

import java.util.Set;
import java.io.Serializable;

public interface ChallengeItem extends Serializable {
    String getTitle();

    String getDescription();

    Set<ImageItem> getImages();
}
