package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.model.Image;

import java.util.Comparator;
import java.io.Serializable;

public class Comparators {
    static class ImageItemSortByVoteCount implements Comparator<ImageItem>, Serializable {
        public int compare(ImageItem o1, ImageItem o2) {
            return o2.getVoteCount().compareTo(o1.getVoteCount());
        }
    }

    static class ChallengeSummarySortByTag implements Comparator<ChallengeSummary>, Serializable {

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
