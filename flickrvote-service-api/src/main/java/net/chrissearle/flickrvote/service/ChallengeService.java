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

    @Deprecated
    ChallengeInfo getVotingChallenge();

    @Deprecated
    ChallengeInfo getChallenge(String challengeTag);

    boolean hasVoted(String photographerId);

    void vote(String photographerId, String imageId);

    void rankChallenge(String tag);

    // Scheduler jobs
    ChallengeSummary openVoting();

    ChallengeSummary announceNewChallenge();

    ChallengeSummary announceResults();

    void remove(String tag);

    List<ChallengeInfo> isDateAvailable(Date startDate);

    ChallengeSummary getMostRecent();

    void saveChallenge(String tag, String title, Date startDate, Date voteDate, Date endDate);
}
