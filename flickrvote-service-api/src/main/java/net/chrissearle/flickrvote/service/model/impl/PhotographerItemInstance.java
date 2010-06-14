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

package net.chrissearle.flickrvote.service.model.impl;

import net.chrissearle.flickrvote.flickr.model.FlickrPhotographer;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.model.PhotographerItem;

public class PhotographerItemInstance implements PhotographerItem {
    private static final long serialVersionUID = -90698707988568539L;

    private final String id;
    private final String twitter;
    private final String fullname;
    private final String username;

    private final String iconUrl;

    private final Integer imageCount;

    private final Boolean administratorFlag;

    public PhotographerItemInstance(Photographer photographer) {
        this.id = photographer.getId();
        this.twitter = photographer.getTwitter();
        this.fullname = photographer.getFullname();
        this.username = photographer.getUsername();
        this.administratorFlag = photographer.isAdministrator();
        this.iconUrl = photographer.getIconUrl();
        this.imageCount = photographer.getImages().size();
    }

    public PhotographerItemInstance(FlickrPhotographer photographer) {
        this.id = photographer.getFlickrId();
        this.twitter = null;
        this.fullname = photographer.getRealname();
        this.username = photographer.getUsername();
        this.administratorFlag = false;
        this.iconUrl = photographer.getIconUrl();
        this.imageCount = 0;
    }

    public Boolean isAdministratorFlag() {
        return administratorFlag;
    }

    public String getFullname() {
        return fullname;
    }

    public String getId() {
        return id;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getUsername() {
        return username;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getName() {
        if (fullname != null && !"".equals(fullname)) {
            return fullname;
        }

        return username;
    }

    public Integer getImageCount() {
        return imageCount;
    }
}
