package net.chrissearle.flickrvote.web.model;

public class ListControl {
    private final boolean showChallengeTitle;
    private final boolean showRank;
    private final boolean showVotes;
    private final boolean showBadges;

    public ListControl(boolean showChallengeTitle, boolean showRank, boolean showVotes, boolean showBadges) {
        this.showChallengeTitle = showChallengeTitle;
        this.showRank = showRank;
        this.showVotes = showVotes;
        this.showBadges = showBadges;
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
}
