package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;

import java.util.Date;

/**
 * Tightly coupled with ChallengeService - simply collects all message source handling in one place
 */
public interface ChallengeMessageService {
    String getResultsForumText(String resultsUrl, String firstPlace, String secondPlace, String thirdPlace);

    String getVotingTwitter(ChallengeSummary challenge);

    String getVotingForumTitle(ChallengeSummary challenge);

    String getVotingForumText(ChallengeSummary challenge);

    String getCurrentTwitter(ChallengeSummary challenge);

    String getCurrentForumTitle(ChallengeSummary challenge);

    String getCurrentForumText(ChallengeSummary challenge);

    String getResultsTwitter(ChallengeSummary challenge, String resultsUrl);

    String getResultsForumTitle(ChallengeSummary challenge);

    String getResultsUrl(ChallengeSummary challenge);

    String getBadgeText(int place, String badgeUrl, ChallengeSummary challenge);

    String getGoldBadgeUrl();

    String getSilverBadgeUrl();

    String getBronzeBadgeUrl();

    String getResultsForumSingle(ImageItem image);

    String getHistoryReportTitle();

    String getInfoSectionTitle();

    String getInfoStartDate(Date date);

    String getInfoVoteDate(Date date);

    String getInfoEndDate(Date date);

    String getImageSectionTitle();

    String getHistoryImageTitleTitle();

    String getHistoryImageRankTitle();

    String getHistoryImageVoteTitle();

    String getHistoryImagePostedTitle();

    String getHistoryImageUrlTitle();

    String getHistoryImagePhotographerTitle();
}
