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
