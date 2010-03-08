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

package net.chrissearle.flickrvote.flickr.impl;

import com.aetrion.flickr.photos.Photo;
import net.chrissearle.flickrvote.flickr.model.FlickrImage;
import net.chrissearle.flickrvote.flickr.model.FlickrPhotographer;

public abstract class AbstractFlickrJImageSupport {
    protected FlickrImage buildImage(Photo photo) {
        FlickrPhotographer photographer = new FlickrPhotographer(photo.getOwner().getId());

        String largeImageUrl = photo.getLargeUrl();

        if (largeImageUrl == null || "".equals(largeImageUrl)) {
            largeImageUrl = photo.getMediumUrl();
        }
        
        return new FlickrImage(photo.getId(), photographer, photo.getTitle(), photo.getUrl(),
                photo.getMediumUrl(), largeImageUrl,
                photo.getDateTaken(), photo.getDatePosted());
    }
}
