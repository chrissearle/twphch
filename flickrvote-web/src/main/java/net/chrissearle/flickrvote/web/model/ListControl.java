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

public class ListControl {
    private final boolean showChallengeTitle;
    private final boolean showRank;
    private final boolean showVotes;
    private final boolean showBadges;
    private final boolean showRefresh;
    private final boolean showPhotographer;

    public ListControl(boolean showChallengeTitle, boolean showRank, boolean showVotes, boolean showBadges, boolean showRefresh, boolean showPhotographer) {
        this.showChallengeTitle = showChallengeTitle;
        this.showRank = showRank;
        this.showVotes = showVotes;
        this.showBadges = showBadges;
        this.showRefresh = showRefresh;
        this.showPhotographer = showPhotographer;
    }

    public boolean isShowChallengeTitle() {
        return showChallengeTitle;
    }

    public boolean isShowRank() {
        return showRank;
    }

    public boolean isShowVotes() {
        return showVotes;
    }

    public boolean isShowBadges() {
        return showBadges;
    }

    public boolean isShowRefresh() {
        return showRefresh;
    }

    public boolean isShowPhotographer() {
        return showPhotographer;
    }
}
