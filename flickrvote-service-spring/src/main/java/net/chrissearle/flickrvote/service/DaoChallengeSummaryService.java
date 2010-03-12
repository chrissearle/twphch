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

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.impl.ChallengeSummaryInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("challengeSummaryService")
public class DaoChallengeSummaryService implements ChallengeSummaryService {
    private ChallengeDao challengeDao;

    @Autowired
    public DaoChallengeSummaryService(ChallengeDao challengeDao) {
        this.challengeDao = challengeDao;
    }

    public Set<ChallengeSummary> getChallengesOpenAt(Date date) {

        Set<ChallengeSummary> challenges = new HashSet<ChallengeSummary>();

        for (Challenge challenge : challengeDao.findWithin(date)) {
            challenges.add(new ChallengeSummaryInstance(challenge));
        }

        return challenges;
    }
}
