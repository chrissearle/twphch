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

package net.chrissearle.flickrvote.flickr.model;

import java.util.HashSet;
import java.util.Set;

/**
 * FlickrImageStatus represents the result of an image check.
 *
 * @author chris
 *         Created on Aug 23, 2009
 */
public class FlickrImageStatus {

    private ImageStatus status;
    private FlickrPhotographer photographer;
    private Set<FlickrImage> images;

    /**
     * Constructor FlickrImageStatus creates a new FlickrImageStatus instance.
     *
     * @param status       of type ImageStatus
     * @param photographer of type FlickrPhotographer
     * @param image        of type FlickrImage
     */
    public FlickrImageStatus(ImageStatus status, FlickrPhotographer photographer, FlickrImage image) {
        this.status = status;
        this.photographer = photographer;
        this.images = new HashSet<FlickrImage>();
        this.images.add(image);
    }

    /**
     * Method addImage add an image to the list
     *
     * @param image of type FlickrImage
     */
    public void addImage(FlickrImage image) {
        this.images.add(image);
    }

    /**
     * Method getImageCount returns the number of images.
     *
     * @return the imageCount (type int) of this FlickrImageStatus object.
     */
    public int getImageCount() {
        return images.size();
    }

    /**
     * Method getImages returns the images of this FlickrImageStatus object.
     *
     * @return the images (type Set<FlickrImage>) of this FlickrImageStatus object.
     */
    public Set<FlickrImage> getImages() {
        return images;
    }

    /**
     * Method getPhotographer returns the photographer of this FlickrImageStatus object.
     *
     * @return the photographer (type FlickrPhotographer) of this FlickrImageStatus object.
     */
    public FlickrPhotographer getPhotographer() {
        return photographer;
    }

    /**
     * Method getStatus returns the status of this FlickrImageStatus object.
     *
     * @return the status (type ImageStatus) of this FlickrImageStatus object.
     */
    public ImageStatus getStatus() {
        return status;
    }

    /**
     * Enum ImageStatus - what kind of status
     *
     * @author chris
     *         Created on Aug 23, 2009
     */
    public enum ImageStatus {
        /**
         * Photographer has multiple images
         */
        MULTIPLE_IMAGES,

        /**
         * Image was taken before earliest date
         */
        TAKEN_DATE
    }
}
