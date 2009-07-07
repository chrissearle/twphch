package net.chrissearle.flickrvote;

import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = {"/applicationContext.xml /testDbContext.xml"})
public class ChallengeTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private ChallengeService challengeService;

    @Test
    public void testAddChallenge() {
        Challenge challenge = new Challenge(100L, "#TwPhCh001", "Red", null, null, null);

        challengeService.addChallenge(challenge);

        Challenge challenge2 = challengeService.getChallenge(100L);

        assert challenge.getName().equals(challenge2.getName()) : "Name mismatch";
        assert challenge.getTag().equals(challenge2.getTag()) : "Tag mismatch";
    }
}
