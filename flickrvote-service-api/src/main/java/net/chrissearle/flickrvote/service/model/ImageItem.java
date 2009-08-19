package net.chrissearle.flickrvote.service.model;

import java.util.Date;
import java.io.Serializable;

public interface ImageItem extends Serializable {
    String getId();

    String getImageUrl();

    PhotographerItem getPhotographer();

    Date getPostedDate();

    Long getRank();

    String getTitle();

    String getUrl();

    Long getVoteCount();

    ChallengeSummary getChallenge();
}
