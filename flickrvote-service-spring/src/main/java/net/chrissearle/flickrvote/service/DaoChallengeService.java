/*
 * Copyright 2009 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.dao.ImageDao;
import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.flickr.CommentDAO;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.model.Vote;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.impl.ChallengeSummaryInstance;
import net.chrissearle.flickrvote.service.model.impl.ImageItemInstance;
import net.chrissearle.flickrvote.twitter.TweetService;
import net.chrissearle.flickrvote.twitter.TwitterServiceException;
import net.chrissearle.mail.SimpleMailService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Class DaoChallengeService uses the dao's to implement challenge service
 *
 * @author chris
 */
@Service("challengeService")
@Transactional
public class DaoChallengeService implements ChallengeService {
    private Logger logger = Logger.getLogger(DaoChallengeService.class);

    private final ChallengeDao challengeDao;
    private final PhotographyDao photographyDao;
    private final ImageDao imageDao;

    private final TweetService tweetService;
    private ChallengeMessageService challengeMessageService;
    private SimpleMailService mailService;

    private CommentDAO commentDAO;

    /**
     * Constructor DaoChallengeService creates a new DaoChallengeService instance.
     *
     * @param challengeDao            of type ChallengeDao
     * @param photographyDao          of type PhotographyDao
     * @param imageDao                of type ImageDao
     * @param challengeMessageService of type ChallengeMessageService
     * @param tweetService            of type UserExistanceService
     */
    @Autowired
    public DaoChallengeService(ChallengeDao challengeDao, PhotographyDao photographyDao, ImageDao imageDao, SimpleMailService mailService,
                               ChallengeMessageService challengeMessageService, TweetService tweetService,
                               CommentDAO commentDAO) {
        this.challengeDao = challengeDao;
        this.photographyDao = photographyDao;
        this.imageDao = imageDao;
        this.tweetService = tweetService;
        this.challengeMessageService = challengeMessageService;
        this.mailService = mailService;

        this.commentDAO = commentDAO;
    }

    /**
     * Method getChallengeSummary returns challenge summary for a given challenge
     *
     * @param tag of type String
     * @return ChallengeSummary
     */
    public ChallengeSummary getChallengeSummary(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge != null) {
            return new ChallengeSummaryInstance(challenge);
        }

        return null;
    }

    /**
     * Method getChallengesByType returns all challenges of the given type
     *
     * @param type of type ChallengeType
     * @return Set<ChallengeSummary>
     */
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

                Challenge currentChallenge = challengeDao.getCurrentChallenge();
                if (currentChallenge != null) {
                    challenges.add(new ChallengeSummaryInstance(currentChallenge));
                }

                break;
            case VOTING:
                // Get voting challenges

                Challenge votingChallenge = challengeDao.getVotingChallenge();
                if (votingChallenge != null) {
                    challenges.add(new ChallengeSummaryInstance(votingChallenge));
                }

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

    /**
     * Method doRanking calculates rank based on score
     *
     * @param images of type List<Image>
     */
    private void doRanking(List<Image> images) {
        long rank = 0;
        long lastSeenValue = Long.MAX_VALUE;

        Collections.sort(images, new Comparators.ImageSortByFinalVoteCount());

        for (Image image : images) {
            if (image.getFinalVoteCount() < lastSeenValue) {
                lastSeenValue = image.getFinalVoteCount();
                rank++;
            }
            image.setFinalRank(rank);
            imageDao.persist(image);
        }
    }

    /**
     * Method hasVoted checks to see if the photographer has voted
     *
     * @param photographerId of type String
     * @return boolean
     */
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

    /**
     * Method vote casts a vote for a given image for a given photographer
     *
     * @param photographerId of type String
     * @param imageId        of type String
     */
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

    /**
     * Method openVoting opens the voting for the current challenge
     *
     * @return ChallengeSummary
     */
    public ChallengeSummary openVoting() {
        Challenge votingChallenge = challengeDao.getVotingChallenge();

        if (votingChallenge == null) {
            if (logger.isInfoEnabled()) {
                logger.info("No voting challenge found to open");
            }

            return null;
        }

        ChallengeSummary challenge = new ChallengeSummaryInstance(votingChallenge);

        if (logger.isInfoEnabled()) {
            logger.info("Opening voting for " + challenge);
        }

        try {
            tweetService.tweet(challengeMessageService.getVotingTwitter(challenge));
        } catch (TwitterServiceException tse) {
            mailService.sendPost(tse.getMessage(), tse.getTwitterMessage());
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to twitter" + tse.getMessage(), tse);
            }
        }

        try {
            mailService.sendPost(challengeMessageService.getVotingForumTitle(challenge),
                    challengeMessageService.getVotingForumText(challenge));
        } catch (FlickrServiceException fse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to flickr" + fse.getMessage(), fse);
            }
        }

        return challenge;
    }

    /**
     * Method announceNewChallenge announces a new challenge
     *
     * @return ChallengeSummary
     */
    public ChallengeSummary announceNewChallenge() {
        Challenge currentChallenge = challengeDao.getCurrentChallenge();

        if (currentChallenge == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No current challenge found to announce");
            }

            return null;
        }

        ChallengeSummary challenge = new ChallengeSummaryInstance(currentChallenge);

        if (logger.isInfoEnabled()) {
            logger.info("Announcing for " + challenge);
        }

        try {
            tweetService.tweet(challengeMessageService.getCurrentTwitter(challenge));
        } catch (TwitterServiceException tse) {
            mailService.sendPost(tse.getMessage(), tse.getTwitterMessage());
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to twitter" + tse.getMessage(), tse);
            }
        }
        try {
            mailService.sendPost(challengeMessageService.getCurrentForumTitle(challenge),
                    challengeMessageService.getCurrentForumText(challenge));
        } catch (FlickrServiceException fse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to flickr" + fse.getMessage(), fse);
            }
        }

        return challenge;
    }

    /**
     * Method announceResults announces the results of a given challenge
     *
     * @return ChallengeSummary
     */
    public ChallengeSummary announceResults() {
        Challenge votedChallenge = challengeDao.getVotedChallenge();

        if (votedChallenge == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No challenge found to handle results");
            }

            return null;
        }

        ChallengeSummary challenge = new ChallengeSummaryInstance(votedChallenge);

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
            tweetService.tweet(challengeMessageService.getResultsTwitter(challenge, resultsUrl));
        } catch (TwitterServiceException tse) {
            mailService.sendPost(tse.getMessage(), tse.getTwitterMessage());
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
                    commentDAO.postComment(imageItem.getId(), badgeText);
                } catch (FlickrServiceException fse) {
                    if (logger.isEnabledFor(Level.WARN)) {
                        logger.warn("Unable to post to flickr" + fse.getMessage(), fse);
                    }
                }
            }
        }

        String messageText = challengeMessageService.getResultsForumText(resultsUrl, messageGold.toString(), messageSilver.toString(), messageBronze.toString());

        try {
            mailService.sendPost(challengeMessageService.getResultsForumTitle(challenge), messageText);
        } catch (FlickrServiceException fse) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to flickr" + fse.getMessage(), fse);
            }
        }

        return challenge;
    }

    /**
     * Method warnVotingOpen sends a note that voting will soon open
     */
    public void warnVotingOpen() {
        Set<ChallengeSummary> challenges = getChallengesByType(ChallengeType.OPEN);

        if (challenges.size() == 0) {
            return;
        }

        ChallengeSummary challenge = challenges.iterator().next();

        try {
            tweetService.tweet(challengeMessageService.getVotingOpenWarning(challenge));
        } catch (TwitterServiceException tse) {
            mailService.sendPost(tse.getMessage(), tse.getTwitterMessage());
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to twitter" + tse.getMessage(), tse);
            }
        }
    }

    /**
     * Method warnVotingClose sends a note that voting will soon close
     */
    public void warnVotingClose() {
        Set<ChallengeSummary> challenges = getChallengesByType(ChallengeType.VOTING);

        if (challenges.size() == 0) {
            return;
        }

        ChallengeSummary challenge = challenges.iterator().next();

        try {
            tweetService.tweet(challengeMessageService.getVotingCloseWarning(challenge));
        } catch (TwitterServiceException tse) {
            mailService.sendPost(tse.getMessage(), tse.getTwitterMessage());
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to post to twitter" + tse.getMessage(), tse);
            }
        }
    }

    /**
     * Method remove removes a challenge and all related data
     *
     * @param tag of type String
     */
    public void remove(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        challengeDao.remove(challenge);
    }

    /**
     * Method isDateAvailable checks to see if the date is already covered by an existing challenge
     *
     * @param startDate of type Date
     * @return List<ChallengeSummary>
     */
    public List<ChallengeSummary> isDateAvailable(Date startDate) {
        List<ChallengeSummary> challenges = new ArrayList<ChallengeSummary>();

        for (Challenge challenge : challengeDao.findWithin(startDate)) {
            challenges.add(new ChallengeSummaryInstance(challenge));
        }

        return challenges;
    }

    /**
     * Method getMostRecent returns the mostRecent of this ChallengeService object.
     *
     * @return the mostRecent (type ChallengeSummary) of this ChallengeService object.
     */
    public ChallengeSummary getMostRecent() {
        Challenge challenge = challengeDao.getMostRecent();

        if (challenge != null) {
            return new ChallengeSummaryInstance(challenge);
        }

        return null;
    }

    /**
     * Method saveChallenge saves or updates a challenge
     *
     * @param tag       of type String
     * @param title     of type String
     * @param startDate of type Date
     * @param voteDate  of type Date
     * @param endDate   of type Date
     */
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


    /**
     * Method rankChallenge causes challenge rank for a challenge to be re-calculated from score
     *
     * @param tag of type String
     */
    public void rankChallenge(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null) {
            return;
        }

        doRanking(challenge.getImages());
    }

}
