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
import net.chrissearle.flickrvote.service.model.ChallengeType;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Interface ChallengeService provides challenge related business logic
 *
 * @author chris
 */
public interface ChallengeService {
    /**
     * Method getChallengeSummary returns challenge summary for a given challenge
     *
     * @param tag of type String
     * @return ChallengeSummary
     */
    ChallengeSummary getChallengeSummary(String tag);

    /**
     * Method getChallengesByType returns all challenges of the given type
     *
     * @param type of type ChallengeType
     * @return Set<ChallengeSummary>
     */
    Set<ChallengeSummary> getChallengesByType(ChallengeType type);

    /**
     * Method hasVoted checks to see if the photographer has voted
     *
     * @param photographerId of type String
     * @return boolean
     */
    boolean hasVoted(String photographerId);

    /**
     * Method vote casts a vote for a given image for a given photographer
     *
     * @param photographerId of type String
     * @param imageId        of type String
     */
    void vote(String photographerId, String imageId);

    /**
     * Method rankChallenge causes challenge rank for a challenge to be re-calculated from score
     *
     * @param tag of type String
     */
    void rankChallenge(String tag);

    // Scheduler jobs

    /**
     * Method openVoting opens the voting for the current challenge
     *
     * @return ChallengeSummary
     */
    ChallengeSummary openVoting();

    /**
     * Method announceNewChallenge announces a new challenge
     *
     * @return ChallengeSummary
     */
    ChallengeSummary announceNewChallenge();

    /**
     * Method announceResults announces the results of a given challenge
     *
     * @return ChallengeSummary
     */
    ChallengeSummary announceResults();

    /**
     * Method warnVotingOpen sends a note that voting will soon open
     */
    void warnVotingOpen();

    /**
     * Method warnVotingClose sends a note that voting will soon close
     */
    void warnVotingClose();

    /**
     * Method remove removes a challenge and all related data
     *
     * @param tag of type String
     */
    void remove(String tag);

    /**
     * Method isDateAvailable checks to see if the date is already covered by an existing challenge
     *
     * @param startDate of type Date
     * @return List<ChallengeSummary>
     */
    List<ChallengeSummary> isDateAvailable(Date startDate);

    /**
     * Method getMostRecent returns the mostRecent of this ChallengeService object.
     *
     * @return the mostRecent (type ChallengeSummary) of this ChallengeService object.
     */
    ChallengeSummary getMostRecent();

    /**
     * Method saveChallenge saves or updates a challenge
     *
     * @param tag       of type String
     * @param title     of type String
     * @param notes     of type String
     * @param startDate of type Date
     * @param voteDate  of type Date
     * @param endDate   of type Date
     */
    void saveChallenge(String tag, String title, String notes, Date startDate, Date voteDate, Date endDate);
}
