package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ImageItem;

import java.util.Set;

public interface WinnerService {
    /**
     * Method getGoldWinners returns all images with first place.
     *
     * @return list of images with rank 1.
     */
    Set<ImageItem> getGoldWinners();

    String getFrontPageHtml();
}
