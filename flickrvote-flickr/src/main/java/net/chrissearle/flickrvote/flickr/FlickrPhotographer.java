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

package net.chrissearle.flickrvote.flickr;

public class FlickrPhotographer {
    private final String flickrId;
    private final String token;
    private final String username;
    private final String realname;
    private final String iconUrl;
    private static final String TOKEN_UNKNOWN = "TOKEN_UNKNOWN";
    private static final String USERNAME_UNKNOWN = "USERNAME_UNKNOWN";
    private static final String REALNAME_UNKNOWN = "REALNAME_UNKNOWN";
    private static final String ICONURL_UNKNOWN = "ICONURL_UNKNOWN";

    public FlickrPhotographer(String flickrId) {
        this.flickrId = flickrId;
        this.username = USERNAME_UNKNOWN;
        this.realname = REALNAME_UNKNOWN;
        this.token = TOKEN_UNKNOWN;
        this.iconUrl = ICONURL_UNKNOWN;
    }

    public FlickrPhotographer(String flickrId, String username, String realname, String iconUrl) {
        this.flickrId = flickrId;
        this.username = username;
        this.realname = realname;
        this.iconUrl = iconUrl;
        this.token = TOKEN_UNKNOWN;
    }

    public FlickrPhotographer(String flickrId, String token, String username, String realname, String iconUrl) {
        this.flickrId = flickrId;
        this.token = token;
        this.username = username;
        this.realname = realname;
        this.iconUrl = iconUrl;
    }

    public String getFlickrId() {
        return flickrId;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRealname() {
        return realname;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
