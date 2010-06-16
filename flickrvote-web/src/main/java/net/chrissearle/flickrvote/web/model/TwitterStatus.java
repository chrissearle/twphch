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

package net.chrissearle.flickrvote.web.model;

public class TwitterStatus {
    private final String name;
    private final String twitterId;
    private final boolean followed;
    private final boolean following;

    public TwitterStatus(String name, String twitterId, boolean followed, boolean following) {
        this.name = name;
        this.twitterId = twitterId;
        this.followed = followed;
        this.following = following;
    }

    public String getName() {
        return name;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public boolean isFollowed() {
        return followed;
    }

    public boolean isFollowing() {
        return following;
    }
}
