package net.chrissearle.flickrvote.service.model;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: chris
 * Date: Aug 7, 2009
 * Time: 12:55:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ChallengeItem {
    String getTitle();

    String getDescription();

    Set<ImageItem> getImages();
}
