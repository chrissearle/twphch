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

import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.ImageItemStatus;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Tightly coupled with ChallengeService - simply collects all message source handling in one place
 *
 * @author
 */
public interface ChallengeMessageService {
    /**
     * Method getResultsForumText ...
     *
     * @param resultsUrl  of type String
     * @param firstPlace  of type String
     * @param secondPlace of type String
     * @param thirdPlace  of type String
     * @return String
     */
    String getResultsForumText(String resultsUrl, String firstPlace, String secondPlace, String thirdPlace);

    /**
     * Method getVotingTwitter ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getVotingTwitter(ChallengeSummary challenge);

    /**
     * Method getVotingForumTitle ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getVotingForumTitle(ChallengeSummary challenge);

    /**
     * Method getVotingForumText ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getVotingForumText(ChallengeSummary challenge);

    /**
     * Method getCurrentTwitter ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getCurrentTwitter(ChallengeSummary challenge);

    /**
     * Method getCurrentForumTitle ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getCurrentForumTitle(ChallengeSummary challenge);

    /**
     * Method getCurrentForumText ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getCurrentForumText(ChallengeSummary challenge);

    /**
     * Method getResultsTwitter ...
     *
     * @param challenge  of type ChallengeSummary
     * @param resultsUrl of type String
     * @return String
     */
    String getResultsTwitter(ChallengeSummary challenge, String resultsUrl);

    /**
     * Method getResultsForumTitle ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getResultsForumTitle(ChallengeSummary challenge);

    /**
     * Method getResultsUrl ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getResultsUrl(ChallengeSummary challenge);

    /**
     * Method getBadgeText ...
     *
     * @param place     of type int
     * @param badgeUrl  of type String
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getBadgeText(int place, String badgeUrl, ChallengeSummary challenge);

    /**
     * Method getGoldBadgeUrl returns the goldBadgeUrl of this ChallengeMessageService object.
     *
     * @return the goldBadgeUrl (type String) of this ChallengeMessageService object.
     */
    String getGoldBadgeUrl();

    /**
     * Method getSilverBadgeUrl returns the silverBadgeUrl of this ChallengeMessageService object.
     *
     * @return the silverBadgeUrl (type String) of this ChallengeMessageService object.
     */
    String getSilverBadgeUrl();

    /**
     * Method getBronzeBadgeUrl returns the bronzeBadgeUrl of this ChallengeMessageService object.
     *
     * @return the bronzeBadgeUrl (type String) of this ChallengeMessageService object.
     */
    String getBronzeBadgeUrl();

    /**
     * Method getResultsForumSingle ...
     *
     * @param image of type ImageItem
     * @return String
     */
    String getResultsForumSingle(ImageItem image);


    /**
     * Method getVotingOpenWarning ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getVotingOpenWarning(ChallengeSummary challenge);

    /**
     * Method getVotingCloseWarning ...
     *
     * @param challenge of type ChallengeSummary
     * @return String
     */
    String getVotingCloseWarning(ChallengeSummary challenge);

    String getWarnForCurrentTitle(String tag);

    String getWarnForCurrentBody(Set<ImageItemStatus> status);

    String generateFrontPageHtml(List<ImageItem> images);

    String getFrontPageTitle();

    /**
     * Method getNoChallengeWarning ...
     *
     * @return String
     */
    String getNoChallengeWarning();
}
