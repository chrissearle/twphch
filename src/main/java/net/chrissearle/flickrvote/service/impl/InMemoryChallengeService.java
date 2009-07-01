package net.chrissearle.flickrvote.service.impl;

import org.springframework.stereotype.Service;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;

import java.util.*;

@Service("challengeService")
public class InMemoryChallengeService implements ChallengeService {
    private Map<Long, Challenge> challenges = new HashMap<Long, Challenge>();

    public InMemoryChallengeService() {
        // Setup test data
        for (int i = 1; i < 8; i++) {
            Challenge c = new Challenge((long) i, "#TwPhCh00"+i, "Test challenge " + i, null, null, null);
            addChallenge(c);
        }
    }

    public Challenge getChallenge(Long id) {
        if (challenges.containsKey(id)) {
            return new Challenge(challenges.get(id));
        }

        return null;
    }

    public List<Challenge> getChallenges() {
        List<Challenge> items = new ArrayList<Challenge>(challenges.values().size());

        Collections.sort(items);

        for (Challenge c : challenges.values()) {
            items.add(new Challenge(c));
        }

        return items;
    }

    public void addChallenge(Challenge challenge) {
        challenges.put(challenge.getId(), new Challenge(challenge));
    }
}
