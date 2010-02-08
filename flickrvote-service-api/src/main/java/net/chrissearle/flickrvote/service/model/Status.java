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

package net.chrissearle.flickrvote.service.model;

/**
 * Enum Status - status problem with an image
 *
 * @author chris
 *         Created on Aug 23, 2009
 */
public enum Status {

    /**
     * Photographer has more than one image present for the given check
     */
    MULTIPLE_IMAGE_BY_SAME_PHOTOGRAPHER ("Multiple images by same photographer"),

    /**
     * Date of photo was too early
     */
    TAKEN_DATE_TOO_EARLY ("Image date too early");

    private final String displayTitle;

    Status(String title) {
        this.displayTitle = title;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }
}
