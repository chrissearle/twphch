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
