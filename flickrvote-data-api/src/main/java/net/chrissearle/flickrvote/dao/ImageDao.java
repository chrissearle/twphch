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

package net.chrissearle.flickrvote.dao;

import net.chrissearle.common.jpa.Dao;
import net.chrissearle.flickrvote.model.Image;

import java.util.List;

/**
 * Interface ImageDao provides access to the data layer for image objects.
 *
 * @author chris
 */
public interface ImageDao extends Dao<String, Image> {
    /**
     * Method getImagesWithRank will return all images with a given rank.
     *
     * @param rank of type long
     * @return Images with the given rank.
     */
    List<Image> getImagesWithRank(long rank);
}