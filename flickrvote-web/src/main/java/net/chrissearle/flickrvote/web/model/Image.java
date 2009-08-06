package net.chrissearle.flickrvote.web.model;

import java.util.Date;

public interface Image {
    String getImageId();

    String getImageTitle();

    String getPageUrl();

    String getImageUrl();

    Date getPostedDate();

    Long getVoteCount();

    Long getRank();
}
