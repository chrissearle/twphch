package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class DaoChallengeService implements ChallengeService {
    
    private final ChallengeDao dao;

    @Autowired
    public DaoChallengeService(ChallengeDao dao) {
        this.dao = dao;
    }

    public List<ChallengeInfo> getChallenges() {
        List<Challenge> allChallenges = dao.getAll();

        List<ChallengeInfo> results = new ArrayList<ChallengeInfo>(allChallenges.size());

        for (Challenge challenge : allChallenges) {
            results.add(new ChallengeInfo(challenge));
        }

        return results;
    }

    public List<ImageInfo> getImagesForChallenge(String challengeName) {
        Challenge challenge = dao.findByTag(challengeName);

        List<ImageInfo> results = new ArrayList<ImageInfo>(challenge.getImages().size());

        for (Image image : challenge.getImages()) {
            results.add(new ImageInfo(image));
        }

        return results;
    }

    public void addChallenge(String title, String tag, Date startDate, Date endDate, Date voteDate) {
        Challenge challenge = new Challenge(tag, title, startDate, voteDate, endDate);

        dao.save(challenge);
    }

    public List<ChallengeInfo> getClosedChallenges() {
        List<Challenge> challenges = dao.getClosedChallenges();

        List<ChallengeInfo> results = new ArrayList<ChallengeInfo>(challenges.size());

        for (Challenge challenge : challenges) {
            results.add(new ChallengeInfo(challenge));
        }

        return results;
    }

    public ChallengeInfo getCurrentChallenge() {
        Challenge challenge = dao.getCurrentChallenge();

        if (challenge != null) {
            return new ChallengeInfo(challenge);
        }

        return null;
    }

    public ChallengeInfo getVotingChallenge() {
        Challenge challenge = dao.getVotingChallenge();

        if (challenge != null) {
            return new ChallengeInfo(challenge);
        }

        return null;
    }

    public ChallengeInfo getChallenge(String challengeTag) {
        Challenge challenge = dao.findByTag(challengeTag);

        if (challenge != null) {
            return new ChallengeInfo(challenge);
        }

        return null;
    }
}
