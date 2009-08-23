/*
 * Copyright 2009 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.service.model.impl;

import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.flickr.FlickrImageStatus;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.ImageItemStatus;
import net.chrissearle.flickrvote.service.model.Status;

import java.util.HashSet;
import java.util.Set;

/**
 * Class ImageItemStatusInstance implements ImageItemStatus
 *
 * @author chris
 *         Created on Aug 23, 2009
 */
public class ImageItemStatusInstance implements ImageItemStatus {
    private Status status;
    private Set<ImageItem> images;

    /**
     * Constructor ImageItemStatusInstance creates a new ImageItemStatusInstance instance from a flickr image status.
     *
     * @param flickrStatus of type FlickrImageStatus
     */
    public ImageItemStatusInstance(FlickrImageStatus flickrStatus) {
        switch (flickrStatus.getStatus()) {
            case TAKEN_DATE:
                status = Status.TAKEN_DATE_TOO_EARLY;
                break;
            case MULTIPLE_IMAGES:
                status = Status.MULTIPLE_IMAGE_BY_SAME_PHOTOGRAPHER;
                break;
        }

        images = new HashSet<ImageItem>(flickrStatus.getImageCount());

        for (FlickrImage image : flickrStatus.getImages()) {
            images.add(new ImageItemInstance(image));
        }
    }

    /**
     * Method getStatus returns the status.
     *
     * @return the status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Method getImages returns the images affected.
     *
     * @return the images.
     */
    public Set<ImageItem> getImages() {
        return images;
    }
}
