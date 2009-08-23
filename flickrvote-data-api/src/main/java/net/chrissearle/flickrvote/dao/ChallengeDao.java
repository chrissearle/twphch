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

/**
 * Interface ChallengeDao provides access to the data layer for challenge objects.
 *
 * @author chris
 */
public interface ChallengeDao extends Dao<String, Challenge> {
    /**
     * Method findByTag returns the challenge with the given tag. Null if no matching challenge found.
     *
     * @param tag of type String
     * @return Challenge
     */
    public Challenge findByTag(String tag);

    /**
     * Method getAll returns all challenges.
     *
     * @return all challenges.
     */
    List<Challenge> getAll();

    /**
     * Method getClosedChallenges returns all challenges in a closed state.
     *
     * @return all closed challenges.
     */
    List<Challenge> getClosedChallenges();

    /**
     * Method getCurrentChallenge returns the the currently open challenge. Null if none found.
     *
     * @return the currently open challenge.
     */
    Challenge getCurrentChallenge();

    /**
     * Method getVotingChallenge returns the current voting challenge. Null if none found.
     *
     * @return the current voting challenge.
     */
    Challenge getVotingChallenge();

    /**
     * Method getVotedChallenge returns a challenge with existing votes. Returns null if none found.
     *
     * @return the challenge with votes.
     */
    Challenge getVotedChallenge();

    /**
     * Method findWithin finds the challenges where date is between start (inclusive) and voting open (exclusive).
     *
     * @param date of type Date
     * @return list of matching challenges
     */
    List<Challenge> findWithin(Date date);

    /**
     * Method getMostRecent returns the challenge with the latest start date.
     *
     * @return the challenge with the latest start date.
     */
    Challenge getMostRecent();
}
