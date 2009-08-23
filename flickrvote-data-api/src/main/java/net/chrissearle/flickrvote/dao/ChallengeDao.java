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

package net.chrissearle.flickrvote.dao;

import net.chrissearle.common.jpa.Dao;
import net.chrissearle.flickrvote.model.Challenge;

import java.util.Date;
import java.util.List;

public interface ChallengeDao extends Dao<String, Challenge> {
    public Challenge findByTag(String tag);

    List<Challenge> getAll();

    List<Challenge> getClosedChallenges();

    Challenge getCurrentChallenge();

    Challenge getVotingChallenge();

    Challenge getVotedChallenge();

    List<Challenge> findWithin(Date date);

    Challenge getMostRecent();
}
