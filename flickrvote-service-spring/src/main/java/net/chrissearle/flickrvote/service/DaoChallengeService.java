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
import net.chrissearle.flickrvote.service.model.*;
import net.chrissearle.flickrvote.service.model.impl.ChallengeSummaryInstance;
import net.chrissearle.flickrvote.service.model.impl.ImageItemInstance;
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
            return new ChallengeSummaryInstance(challenge);
        }

        return null;
    }

    public Set<ChallengeSummary> getChallengesByType(ChallengeType type) {
        Set<ChallengeSummary> challenges = new HashSet<ChallengeSummary>();

        switch (type) {
            case ALL:
                // Get all challenges

                for (Challenge challenge : challengeDao.getAll()) {
                    challenges.add(new ChallengeSummaryInstance(challenge));
                }

                break;
            case OPEN:
                // Get open challenges

                challenges.add(new ChallengeSummaryInstance(challengeDao.getCurrentChallenge()));

                break;
            case VOTING:
                // Get voting challenges

                challenges.add(new ChallengeSummaryInstance(challengeDao.getVotingChallenge()));

                break;
            case CLOSED:
                // Get closed challenges

                for (Challenge challenge : challengeDao.getClosedChallenges()) {
                    challenges.add(new ChallengeSummaryInstance(challenge));
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

    @Deprecated
    public ChallengeInfo getVotingChallenge() {
        Challenge challenge = challengeDao.getVotingChallenge();

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

    public ChallengeSummary openVoting() {
        ChallengeSummary challenge = new ChallengeSummaryInstance(challengeDao.getVotingChallenge());

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

        return challenge;
    }

    public ChallengeSummary announceNewChallenge() {
        ChallengeSummary challenge = new ChallengeSummaryInstance(challengeDao.getCurrentChallenge());

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

        return challenge;
    }

    public ChallengeSummary announceResults() {
        Challenge votedChallenge = challengeDao.getVotedChallenge();

        ChallengeSummary challenge = new ChallengeSummaryInstance(votedChallenge);

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
        List<Image> images = votedChallenge.getImages();

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

        List<ImageItem> imageResults = new ArrayList<ImageItem>();

        for (Image image : images) {
            imageResults.add(new ImageItemInstance(image));
        }

        StringBuilder messageGold = new StringBuilder();
        StringBuilder messageSilver = new StringBuilder();
        StringBuilder messageBronze = new StringBuilder();

        for (ImageItem imageItem : imageResults) {
            String badgeText = "";

            String forumPost = challengeMessageService.getResultsForumSingle(imageItem);

            if (imageItem.getRank() == 1) {
                // Gold
                badgeText = challengeMessageService.getBadgeText(1, challengeMessageService.getGoldBadgeUrl(), challenge);
                messageGold.append(forumPost);
            }
            if (imageItem.getRank() == 2) {
                // Silver
                badgeText = challengeMessageService.getBadgeText(2, challengeMessageService.getSilverBadgeUrl(), challenge);
                messageSilver.append(forumPost);
            }
            if (imageItem.getRank() == 3) {
                // Bronze
                badgeText = challengeMessageService.getBadgeText(3, challengeMessageService.getBronzeBadgeUrl(), challenge);
                messageBronze.append(forumPost);
            }
            if (!"".equals(badgeText)) {
                try {
                    flickrService.postComment(imageItem.getId(), badgeText);
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

        return challenge;
    }

    public void remove(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        challengeDao.remove(challenge);
    }

    public List<ChallengeSummary> isDateAvailable(Date startDate) {
        List<ChallengeSummary> challenges = new ArrayList<ChallengeSummary>();

        for (Challenge challenge : challengeDao.findWithin(startDate)) {
            challenges.add(new ChallengeSummaryInstance(challenge));
        }

        return challenges;
    }

    public ChallengeSummary getMostRecent() {
        Challenge challenge = challengeDao.getMostRecent();

        if (challenge != null) {
            return new ChallengeSummaryInstance(challenge);
        }

        return null;
    }

    public void saveChallenge(String tag, String title, Date startDate, Date voteDate, Date endDate) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge != null) {
            challenge.setName(title);
            challenge.setStartDate(startDate);
            challenge.setVotingOpenDate(voteDate);
            challenge.setEndDate(endDate);
        } else {
            challenge = new Challenge(tag, title, startDate, voteDate, endDate);
        }

        challengeDao.persist(challenge);
    }


    public void rankChallenge(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null) {
            return;
        }

        doRanking(challenge.getImages());
    }
}
