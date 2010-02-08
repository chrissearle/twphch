package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ImageItemStatus;

import java.util.Set;

public interface StatusCheckService {
    /**
     * Method checkSearch runs a search for the given challenge at flickr and checks for multiple entries, date errors etc.
     *
     * @param tag - tag of an existing challenge
     * @return Set<ImageItemStatus>
     */
    Set<ImageItemStatus> checkSearch(String tag);
}
