package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageInfo;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ChallengeService {
    ChallengeSummary getChallengeSummary(String tag);

    Set<ChallengeSummary> getChallengesByType(ChallengeType type);


    @Deprecated
    List<ChallengeInfo> getChallenges();

    @Deprecated
    List<ImageInfo> getImagesForChallenge(String challengeName);

    void saveChallenge(ChallengeInfo challenge);

    @Deprecated
    List<ChallengeInfo> getClosedChallenges();

    @Deprecated
    ChallengeInfo getCurrentChallenge();

    @Deprecated
    ChallengeInfo getVotingChallenge();

    @Deprecated
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
