package net.chrissearle.flickrvote.service.model;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: chris
 * Date: Aug 7, 2009
 * Time: 12:53:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ImageItem {
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
