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
