/*
 * Copyright 2010 Chris Searle
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

package net.chrissearle.flickrvote.service.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImageItems {
    private List<ImageItem> items;

    public ImageItems(List<ImageItem> items) {
        this.items = items;
    }

    public List<ImageItem> getImages() {
        return this.items;
    }

    public List<ImageItem> getImagesUniquePhotographer(Set<ImageItem> priorityItems) {
        ImagesUniquePhotographer imagesUniquePhotographer = new ImagesUniquePhotographer();

        if (priorityItems != null && !priorityItems.isEmpty()) {
            imagesUniquePhotographer.addImages(priorityItems);
        }
        
        imagesUniquePhotographer.addImages(items);

        return imagesUniquePhotographer.getImages();
    }

    private class ImagesUniquePhotographer {
        private List<ImageItem> itemList = new ArrayList<ImageItem>();
        private List<String> seenPhotographers = new ArrayList<String>();

        public List<ImageItem> getImages() {
            return itemList;
        }

        public void addImages(Collection<ImageItem> items) {
            for (ImageItem item : items) {
                if (!seenPhotographers.contains(item.getPhotographer().getId())) {
                    seenPhotographers.add(item.getPhotographer().getId());

                    itemList.add(item);
                }
            }
        }
    }
}
