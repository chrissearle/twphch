package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.dao.ImageDao;
import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.model.Vote;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import net.chrissearle.flickrvote.twitter.TwitterService;
import net.chrissearle.flickrvote.twitter.TwitterServiceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("challengeService")
@Transactional
public class DaoChallengeService implements ChallengeService {
    private Logger logger = Logger.getLogger(DaoChallengeService.class);

    private final ChallengeDao challengeDao;
    private final PhotographyDao photographyDao;
    private final ImageDao imageDao;

    private final TwitterService twitterService;
    private final FlickrService flickrService;
    private ChallengeMessageService challengeMessageService;

    @Autowired
    public DaoChallengeService(ChallengeDao challengeDao, PhotographyDao photographyDao, ImageDao imageDao,
                               ChallengeMessageService challengeMessageService, TwitterService twitterService, FlickrService flickrService) {
        this.challengeDao = challengeDao;
        this.photographyDao = photographyDao;
        this.imageDao = imageDao;
        this.twitterService = twitterService;
        this.flickrService = flickrService;
        this.challengeMessageService = challengeMessageService;
    }

    public ChallengeSummary getChallengeSummary(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge != null) {
            return new ChallengeSummary(challenge);
        }

        return null;
    }

    public Set<ChallengeSummary> getChallengesByType(ChallengeType type) {
        Set<ChallengeSummary> challenges = new HashSet<ChallengeSummary>();

        switch (type) {
            case NORMAL:
                // Get all challenges

                for (Challenge challenge : challengeDao.getAll()) {
                    challenges.add(new ChallengeSummary(challenge));
                }

                break;
            case OPEN:
                // Get open challenges

                challenges.add(new ChallengeSummary(challengeDao.getCurrentChallenge()));

                break;
            case VOTING:
                // Get voting challenges

                challenges.add(new ChallengeSummary(challengeDao.getVotingChallenge()));

                break;
            case CLOSED:
                // Get closed challenges

                for (Challenge challenge : challengeDao.getClosedChallenges()) {
                    challenges.add(new ChallengeSummary(challenge));
                }

                break;
        }

        return challenges;
    }

    @Deprecated
    public List<ChallengeInfo> getChallenges() {
        List<Challenge> allChallenges = challengeDao.getAll();

        List<ChallengeInfo> results = new ArrayList<ChallengeInfo>(allChallenges.size());

        for (Challenge challenge : allChallenges) {
            results.add(new ChallengeInfo(challenge));
        }

        return results;
    }

    @Deprecated
    public List<ImageInfo> getImagesForChallenge(String challengeName) {
        Challenge challenge = challengeDao.findByTag(challengeName);

        List<ImageInfo> results = new ArrayList<ImageInfo>(challenge.getImages().size());

        for (Image image : challenge.getImages()) {
            results.add(new ImageInfo(image));
        }

        return results;
    }

    private void doRanking(List<Image> images) {
        long rank = 0;
        long lastSeenValue = Long.MAX_VALUE;

        Collections.sort(images, new Comparator<Image>() {
            public int compare(Image o1, Image o2) {
                return o2.getFinalVoteCount().compareTo(o1.getFinalVoteCount());
            }
        });

        for (Image image : images) {
            if (image.getFinalVoteCount() < lastSeenValue) {
                lastSeenValue = image.getFinalVoteCount();
                rank++;
            }
            image.setFinalRank(rank);
            imageDao.persist(image);
        }
    }


    public void saveChallenge(ChallengeInfo challengeInfo) {
        Challenge challenge = challengeDao.findByTag(challengeInfo.getTag());

        if (challenge != null) {
            challenge.setName(challengeInfo.getTitle());
            challenge.setStartDate(challengeInfo.getStartDate());
            challenge.setVotingOpenDate(challengeInfo.getVoteDate());
            challenge.setEndDate(challengeInfo.getEndDate());
        } else {
            challenge = new Challenge(challengeInfo.getTag(), challengeInfo.getTitle(),
                    challengeInfo.getStartDate(), challengeInfo.getVoteDate(), challengeInfo.getEndDate());
        }

        challengeDao.persist(challenge);
    }

    @Deprecated
    public List<ChallengeInfo> getClosedChallenges() {
        List<Challenge> challenges = challengeDao.getClosedChallenges();

        List<ChallengeInfo> results = new ArrayList<ChallengeInfo>(challenges.size());

        for (Challenge challenge : challenges) {
            results.add(new ChallengeInfo(challenge));
        }

        return results;
    }

    @Deprecated
    public ChallengeInfo getCurrentChallenge() {
        Challenge challenge = challengeDao.getCurrentChallenge();

        if (challenge != null) {
            return new ChallengeInfo(challenge);
        }

        return null;
    }

    @Deprecated
    public ChallengeInfo getVotingChallenge() {
        Challenge challenge = challengeDao.getVotingChallenge();

        if (challenge != null) {
            return new ChallengeInfo(challenge);
        }

        return null;
    }

    @Deprecated
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
        Photographer photographer = photographyDao.findById(photographerId);

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
        Photographer photographer = photographyDao.findById(photographerId);

        Image image = imageDao.findById(imageId);


        if (photographer != null && image != null) {
            Vote vote = new Vote();
            photographer.addVote(vote);
            image.addVote(vote);

            photographyDao.persist(photographer);
            imageDao.persist(image);
        }
    }

    public ChallengeInfo openVoting() {
        Challenge challenge = challengeDao.getVotingChallenge();

        if (challenge == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No voting challenge found to open");
            }

            return null;
        }

        if (logger.isInfoEnabled()) {
            logger.info("Opening voting for " + challenge);
        }

        try {
            twitterService.twitter(challengeMessageService.getVotingTwitter(challenge));
        } catch (TwitterServiceException tse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to twitter" + tse.getMessage(), tse);
            }
        }

        try {
            flickrService.postForum(challengeMessageService.getVotingForumTitle(challenge),
                    challengeMessageService.getVotingForumText(challenge));
        } catch (FlickrServiceException fse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to flickr" + fse.getMessage(), fse);
            }
        }

        return new ChallengeInfo(challenge);
    }

    public ChallengeInfo announceNewChallenge() {
        Challenge challenge = challengeDao.getCurrentChallenge();

        if (challenge == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No current challenge found to announce");
            }

            return null;
        }

        if (logger.isInfoEnabled()) {
            logger.info("Announcing for " + challenge);
        }

        try {
            twitterService.twitter(challengeMessageService.getCurrentTwitter(challenge));
        } catch (TwitterServiceException tse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to twitter" + tse.getMessage(), tse);
            }
        }
        try {
            flickrService.postForum(challengeMessageService.getCurrentForumTitle(challenge),
                    challengeMessageService.getCurrentForumText(challenge));
        } catch (FlickrServiceException fse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to flickr" + fse.getMessage(), fse);
            }
        }

        return new ChallengeInfo(challenge);
    }

    public ChallengeInfo announceResults() {
        Challenge challenge = challengeDao.getVotedChallenge();

        if (challenge == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No challenge found to handle results");
            }

            return null;
        }

        if (logger.isInfoEnabled()) {
            logger.info("Results for " + challenge);
        }

        // Sum up votes
        List<Image> images = challenge.getImages();

        for (Image image : images) {
            image.setFinalVoteCount((long) image.getVotes().size());
            imageDao.persist(image);
        }

        photographyDao.clearVotes();

        doRanking(images);

        String resultsUrl = challengeMessageService.getResultsUrl(challenge);

        try {
            twitterService.twitter(challengeMessageService.getResultsTwitter(challenge, resultsUrl));
        } catch (TwitterServiceException tse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to twitter" + tse.getMessage(), tse);
            }
        }

        List<ImageInfo> imageResults = new ArrayList<ImageInfo>();

        for (Image image : images) {
            imageResults.add(new ImageInfo(image));
        }

        StringBuilder messageGold = new StringBuilder();
        StringBuilder messageSilver = new StringBuilder();
        StringBuilder messageBronze = new StringBuilder();

        for (ImageInfo imageInfo : imageResults) {
            String badgeText = "";

            String forumPost = challengeMessageService.getResultsForumSingle(imageInfo);

            if (imageInfo.getRank() == 1) {
                // Gold
                badgeText = challengeMessageService.getBadgeText(1, challengeMessageService.getGoldBadgeUrl(), challenge);
                messageGold.append(forumPost);
            }
            if (imageInfo.getRank() == 2) {
                // Silver
                badgeText = challengeMessageService.getBadgeText(2, challengeMessageService.getSilverBadgeUrl(), challenge);
                messageSilver.append(forumPost);
            }
            if (imageInfo.getRank() == 3) {
                // Bronze
                badgeText = challengeMessageService.getBadgeText(3, challengeMessageService.getBronzeBadgeUrl(), challenge);
                messageBronze.append(forumPost);
            }
            if (!"".equals(badgeText)) {
                try {
                    flickrService.postComment(imageInfo.getId(), badgeText);
                } catch (FlickrServiceException fse) {
                    if (logger.isEnabledFor(Level.WARN)) {
                        logger.warn("Unable to post to flickr" + fse.getMessage(), fse);
                    }
                }
            }
        }

        String messageText = challengeMessageService.getResultsForumText(resultsUrl, messageGold.toString(), messageSilver.toString(), messageBronze.toString());

        try {
            flickrService.postForum(challengeMessageService.getResultsForumTitle(challenge), messageText);
        } catch (FlickrServiceException fse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to flickr" + fse.getMessage(), fse);
            }
        }

        return new ChallengeInfo(challenge);
    }

    public void remove(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        challengeDao.remove(challenge);
    }

    public List<ChallengeInfo> isDateAvailable(Date startDate) {
        List<ChallengeInfo> challenges = new ArrayList<ChallengeInfo>();

        for (Challenge challenge : challengeDao.findWithin(startDate)) {
            challenges.add(new ChallengeInfo(challenge));
        }

        return challenges;
    }

    public ChallengeInfo getMostRecent() {
        Challenge challenge = challengeDao.getMostRecent();

        if (challenge != null) {
            return new ChallengeInfo(challenge);
        }

        return null;
    }


    public void rankChallenge(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null) {
            return;
        }

        doRanking(challenge.getImages());
    }
}
