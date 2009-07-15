package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TestChartChallengeService implements ChallengeService {
    public List<ChallengeInfo> getChallenges() {
        return null;
    }

    public List<ImageInfo> getImagesForChallenge(String challengeName) {
        Random rand = new Random();

        List<ImageInfo> images = new ArrayList<ImageInfo>();

        for (int i = 0; i < 20; i++) {
            ImageInfo image = new ImageInfo();
            image.setFinalVoteCount(Math.abs(rand.nextLong() % 50));
            image.setPhotographerName("Test Photographer " + i);

            images.add(image);
        }

        return images;
    }

    public void addChallenge(String title, String tag, Date startDate, Date endDate, Date voteDate) {
    }

    public List<ChallengeInfo> getClosedChallenges() {
        return null;
    }

    public ChallengeInfo getCurrentChallenge() {
        return null;
    }

    public ChallengeInfo getVotingChallenge() {
        return null;
    }

    public ChallengeInfo getChallenge(String challengeTag) {
        ChallengeInfo challenge = new ChallengeInfo();
        challenge.setTag(challengeTag);
        challenge.setTitle(challengeTag);
        challenge.setStartDate(new Date());
        challenge.setEndDate(challenge.getStartDate());
        challenge.setVoteDate(challenge.getStartDate());

        return challenge;
    }
}
