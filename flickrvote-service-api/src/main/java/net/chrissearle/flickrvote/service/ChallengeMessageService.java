package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.model.ImageInfo;

/**
 * Tightly coupled with ChallengeService - simply collects all message source handling in one place
 */
public interface ChallengeMessageService {
    String getResultsForumText(String resultsUrl, String firstPlace, String secondPlace, String thirdPlace);

    String getVotingTwitter(Challenge challenge);

    String getVotingForumTitle(Challenge challenge);

    String getVotingForumText(Challenge challenge);

    String getCurrentTwitter(Challenge challenge);

    String getCurrentForumTitle(Challenge challenge);

    String getCurrentForumText(Challenge challenge);

    String getResultsTwitter(Challenge challenge, String resultsUrl);

    String getResultsForumTitle(Challenge challenge);

    String getResultsUrl(Challenge challenge);

    String getBadgeText(int place, String badgeUrl, Challenge challenge);

    String getGoldBadgeUrl();

    String getSilverBadgeUrl();

    String getBronzeBadgeUrl();

    String getResultsForumSingle(ImageInfo image);
}
