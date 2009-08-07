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
