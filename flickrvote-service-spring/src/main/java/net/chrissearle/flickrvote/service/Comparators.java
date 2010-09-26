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

package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;

import java.io.Serializable;
import java.util.Comparator;

public class Comparators {
    static class ImageItemSortByVoteCount implements Comparator<ImageItem>, Serializable {
        public int compare(ImageItem o1, ImageItem o2) {
            return o2.getVoteCount().compareTo(o1.getVoteCount());
        }
    }

    public static class ChallengeSummarySortByTag implements Comparator<ChallengeSummary>, Serializable {
        public int compare(ChallengeSummary o1, ChallengeSummary o2) {
            return o2.getTag().compareTo(o1.getTag());
        }
    }

    static class ImageSortByFinalVoteCount implements Comparator<Image>, Serializable {
        public int compare(Image o1, Image o2) {
            return o2.getFinalVoteCount().compareTo(o1.getFinalVoteCount());
        }
    }

    static class ChallengeSummarySortByStartDateComparator implements Comparator<ChallengeSummary>, Serializable {
        public int compare(ChallengeSummary o1, ChallengeSummary o2) {
            return o1.getStartDate().compareTo(o2.getStartDate());
        }
    }
}
