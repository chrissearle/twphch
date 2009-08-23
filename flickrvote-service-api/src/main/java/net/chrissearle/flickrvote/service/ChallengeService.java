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

public interface ChallengeService {
    ChallengeSummary getChallengeSummary(String tag);

    Set<ChallengeSummary> getChallengesByType(ChallengeType type);

    boolean hasVoted(String photographerId);

    void vote(String photographerId, String imageId);

    void rankChallenge(String tag);

    // Scheduler jobs
    ChallengeSummary openVoting();

    ChallengeSummary announceNewChallenge();

    ChallengeSummary announceResults();

    void remove(String tag);

    List<ChallengeSummary> isDateAvailable(Date startDate);

    ChallengeSummary getMostRecent();

    void saveChallenge(String tag, String title, Date startDate, Date voteDate, Date endDate);
}
