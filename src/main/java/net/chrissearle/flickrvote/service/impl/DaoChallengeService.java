package net.chrissearle.flickrvote.service.impl;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("challengeService")
public class DaoChallengeService implements ChallengeService {
    @Autowired
    private ChallengeDao dao;

    public Challenge getChallenge(Long id) {
        return dao.findById(id);
    }

    public List<Challenge> getChallenges() {
        return dao.getAll();
    }

    public void addChallenge(Challenge challenge) {
        dao.save(challenge);
    }

    public List<Challenge> getClosedChallenges() {
        return dao.getClosedChallenges();
    }

    public Challenge getCurrentChallenge() {
        return dao.getCurrentChallenge();
    }

    public Challenge getVotingChallenge() {
        return dao.getVotingChallenge();
    }
}
