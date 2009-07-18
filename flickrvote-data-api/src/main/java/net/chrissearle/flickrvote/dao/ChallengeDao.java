package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.Challenge;

import java.util.List;

public interface ChallengeDao {
    public Challenge findById(long id);

    public Challenge findByTag(String tag);

    public void save(Challenge challenge);

    public Challenge update(Challenge challenge);

    public void delete(Challenge challenge);

    List<Challenge> getAll();

    List<Challenge> getClosedChallenges();

    Challenge getCurrentChallenge();

    Challenge getVotingChallenge();

    Challenge getVotedChallenge();
}
