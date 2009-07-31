package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;

import java.util.Date;
import java.util.List;

public interface ChallengeService {
    List<ChallengeInfo> getChallenges();

    List<ImageInfo> getImagesForChallenge(String challengeName);

    void saveChallenge(ChallengeInfo challenge);

    List<ChallengeInfo> getClosedChallenges();

    ChallengeInfo getCurrentChallenge();

    ChallengeInfo getVotingChallenge();

    ChallengeInfo getChallenge(String challengeTag);

    boolean hasVoted(String photographerId);

    void vote(String photographerId, String imageId);

    void rankChallenge(String tag);

    // Scheduler jobs
    ChallengeInfo openVoting();

    ChallengeInfo announceNewChallenge();

    ChallengeInfo announceResults();

    void remove(String tag);

    List<ChallengeInfo> isDateAvailable(Date startDate);

    ChallengeInfo getMostRecent();
}
