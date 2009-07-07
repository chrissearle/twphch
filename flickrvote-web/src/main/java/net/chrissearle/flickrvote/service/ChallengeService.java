package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.model.Challenge;

import java.util.List;

public interface ChallengeService {
    Challenge getChallenge(Long id);

    List<Challenge> getChallenges();

    void addChallenge(Challenge challenge);

    List<Challenge> getClosedChallenges();

    Challenge getCurrentChallenge();

    Challenge getVotingChallenge();

    void populateChallengeFromFlickr(Challenge challenge);
}
