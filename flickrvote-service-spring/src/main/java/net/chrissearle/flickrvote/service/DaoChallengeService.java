package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.model.Vote;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class DaoChallengeService implements ChallengeService {
    private Logger logger = Logger.getLogger(DaoChallengeService.class);

    private final ChallengeDao challengeDao;
    private final PhotographyDao photographyDao;


    @Autowired
    public DaoChallengeService(ChallengeDao challengeDao, PhotographyDao photographyDao) {
        this.challengeDao = challengeDao;
        this.photographyDao = photographyDao;
    }

    public List<ChallengeInfo> getChallenges() {
        List<Challenge> allChallenges = challengeDao.getAll();

        List<ChallengeInfo> results = new ArrayList<ChallengeInfo>(allChallenges.size());

        for (Challenge challenge : allChallenges) {
            results.add(new ChallengeInfo(challenge));
        }

        return results;
    }

    public List<ImageInfo> getImagesForChallenge(String challengeName) {
        Challenge challenge = challengeDao.findByTag(challengeName);

        List<ImageInfo> results = new ArrayList<ImageInfo>(challenge.getImages().size());

        for (Image image : challenge.getImages()) {
            results.add(new ImageInfo(image));
        }

        doRanking(results);

        return results;
    }

    private void doRanking(List<ImageInfo> images) {
        long rank = 0;
        long lastSeenValue = Long.MAX_VALUE;

        Collections.sort(images, new Comparator<ImageInfo>() {

            public int compare(ImageInfo o1, ImageInfo o2) {
                return o2.getFinalVoteCount().compareTo(o1.getFinalVoteCount());
            }
        });

        for (ImageInfo image : images) {
            if (image.getFinalVoteCount() < lastSeenValue) {
                lastSeenValue = image.getFinalVoteCount();
                rank++;
            }
            image.setRank(rank);
        }
    }


    public void addChallenge(String title, String tag, Date startDate, Date endDate, Date voteDate) {
        Challenge challenge = new Challenge(tag, title, startDate, voteDate, endDate);

        challengeDao.save(challenge);
    }

    public List<ChallengeInfo> getClosedChallenges() {
        List<Challenge> challenges = challengeDao.getClosedChallenges();

        List<ChallengeInfo> results = new ArrayList<ChallengeInfo>(challenges.size());

        for (Challenge challenge : challenges) {
            results.add(new ChallengeInfo(challenge));
        }

        return results;
    }

    public ChallengeInfo getCurrentChallenge() {
        Challenge challenge = challengeDao.getCurrentChallenge();

        if (challenge != null) {
            return new ChallengeInfo(challenge);
        }

        return null;
    }

    public ChallengeInfo getVotingChallenge() {
        Challenge challenge = challengeDao.getVotingChallenge();

        if (challenge != null) {
            return new ChallengeInfo(challenge);
        }

        return null;
    }

    public ChallengeInfo getChallenge(String challengeTag) {
        Challenge challenge = challengeDao.findByTag(challengeTag);

        if (challenge != null) {
            return new ChallengeInfo(challenge);
        }

        return null;
    }

    public boolean hasVoted(String photographerId) {
        if (logger.isDebugEnabled()) {
            logger.debug("hasVoted for: " + photographerId);
        }
        Photographer photographer = photographyDao.findPhotographerByFlickrId(photographerId);

        if (logger.isDebugEnabled()) {
            logger.debug("hasVoted for: " + photographer);
        }

        if (photographer.getVotes().size() > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("hasVoted found: " + photographer.getVotes().size() + " for: " + photographer);
            }
            return true;
        }

        return false;
    }

    public void vote(String photographerId, String imageId) {
        Photographer photographer = photographyDao.findPhotographerByFlickrId(photographerId);

        Image image = photographyDao.findImageByFlickrId(imageId);


        if (photographer != null && image != null) {
            Vote vote = new Vote();
            photographer.addVote(vote);
            image.addVote(vote);

            photographyDao.save(photographer);
            photographyDao.save(image);
        }
    }
}
