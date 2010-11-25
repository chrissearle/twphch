/*
 * Copyright 2010 Chris Searle
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
import net.chrissearle.spring.twitter.service.DirectMessageService;
import net.chrissearle.spring.twitter.service.FollowService;
import net.chrissearle.spring.twitter.service.TweetService;
import net.chrissearle.spring.twitter.service.TwitterServiceException;
import net.chrissearle.mail.SimpleMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class DaoChallengeService uses the dao's to implement challenge service
 *
 * @author chris
 */
@Service("challengeService")
@Transactional
public class DaoChallengeService implements ChallengeService {
    private Logger logger = Logger.getLogger(DaoChallengeService.class.getName());

    private final ChallengeDao challengeDao;
    private final PhotographyDao photographyDao;
    private final ImageDao imageDao;

    private final TweetService tweetService;
    private final FollowService followService;
    private final DirectMessageService dmService;

    private ChallengeMessageService challengeMessageService;
    private SimpleMailService mailService;

    private CommentDAO commentDAO;
    private WinnerService winnerService;

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
                               ChallengeMessageService challengeMessageService, TweetService tweetService, WinnerService winnerService,
                               CommentDAO commentDAO, FollowService followService, DirectMessageService dmService) {
        this.challengeDao = challengeDao;
        this.photographyDao = photographyDao;
        this.imageDao = imageDao;
        this.tweetService = tweetService;
        this.followService = followService;
        this.dmService = dmService;
        this.challengeMessageService = challengeMessageService;
        this.mailService = mailService;
        this.winnerService = winnerService;

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

            if (logger.isLoggable(Level.INFO)) {
                logger.info("Setting final rank for " + image.getId() + " to " + rank);
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
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("hasVoted for: " + photographerId);
        }
        Photographer photographer = photographyDao.findById(photographerId);

        if (logger.isLoggable(Level.FINE)) {
            logger.fine("hasVoted for: " + photographer);
        }

        if (photographer.getVotes().size() > 0) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("hasVoted found: " + photographer.getVotes().size() + " for: " + photographer);
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
            if (logger.isLoggable(Level.INFO)) {
                logger.fine("No voting challenge found to open");
            }

            return null;
        }

        ChallengeSummary challenge = new ChallengeSummaryInstance(votingChallenge);

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Opening voting for " + challenge);
        }

        try {
            tweetService.tweet(challengeMessageService.getVotingTwitter(challenge));
        } catch (TwitterServiceException tse) {
            mailService.sendPost(tse.getMessage(), tse.getTwitterMessage());
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to post to twitter" + tse.getMessage());
            }
        }

        try {
            mailService.sendPost(challengeMessageService.getVotingForumTitle(challenge),
                    challengeMessageService.getVotingForumText(challenge));
        } catch (FlickrServiceException fse) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to post to flickr" + fse.getMessage());
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
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("No current challenge found to announce");
            }

            return null;
        }

        ChallengeSummary challenge = new ChallengeSummaryInstance(currentChallenge);

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Announcing for " + challenge);
        }

        final String tweetText = challengeMessageService.getCurrentTwitter(challenge);

        try {
            tweetService.tweet(tweetText);
        } catch (TwitterServiceException tse) {
            mailService.sendPost(tse.getMessage(), tse.getTwitterMessage());
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to post to twitter" + tse.getMessage());
            }
        }
        try {
            mailService.sendPost(challengeMessageService.getCurrentForumTitle(challenge),
                    challengeMessageService.getCurrentForumText(challenge));
        } catch (FlickrServiceException fse) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to post to flickr" + fse.getMessage());
            }
        }

        // Now - let's DM our friends
        try {
            /*
                To get the list of fllowing - we need to do a call per user - and we're getting rate limited.
                Send to those that have added their account to the database - this saves the lookup call and it's a lower
                number too.
                for (String following : followService.amFollowing()) {
            */
            for (Photographer photographer : photographyDao.all()) {
                String following = photographer.getTwitter();

                if (following != null && !"".equals(following)) {
                    if (logger.isLoggable(Level.INFO)) {
                        logger.info("DM'ing " + following + " with " + tweetText);
                    }

                    dmService.dm(following, tweetText);
                }
            }
        } catch (TwitterServiceException tse) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to send dm" + tse.getMessage());
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
            if (logger.isLoggable(Level.INFO)) {
                logger.info("No challenge found to handle results");
            }

            return null;
        }

        ChallengeSummary challenge = new ChallengeSummaryInstance(votedChallenge);

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Results for " + challenge);
        }

        // Sum up votes
        List<Image> images = votedChallenge.getImages();

        for (Image image : images) {
            if (logger.isLoggable(Level.INFO)) {
                logger.info("Setting final vote count for " + image.getId() + " to " + image.getVotes().size());
            }
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
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to post to twitter" + tse.getMessage());
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
                    if (logger.isLoggable(Level.INFO)) {
                        logger.info("Posting comment on " + imageItem.getId() + " with text " + badgeText);
                    }
                    commentDAO.postComment(imageItem.getId(), badgeText);
                } catch (FlickrServiceException fse) {
                    if (logger.isLoggable(Level.WARNING)) {
                        logger.warning("Unable to post to flickr" + fse.getMessage());
                    }
                }
            }
        }

        String messageText = challengeMessageService.getResultsForumText(resultsUrl, messageGold.toString(), messageSilver.toString(), messageBronze.toString());

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Posting admin mail with text " + messageText);
        }

        try {
            mailService.sendPost(challengeMessageService.getResultsForumTitle(challenge), messageText);
        } catch (FlickrServiceException fse) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to post to flickr" + fse.getMessage());
            }
        }

        mailService.sendPost(challengeMessageService.getFrontPageTitle(), winnerService.getFrontPageHtml());

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
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to post to twitter" + tse.getMessage());
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
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to post to twitter" + tse.getMessage());
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
    public void saveChallenge(String tag, String title, String notes, Date startDate, Date voteDate, Date endDate) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge != null) {
            challenge.setName(title);
            challenge.setStartDate(startDate);
            challenge.setVotingOpenDate(voteDate);
            challenge.setDescription(notes);
            challenge.setEndDate(endDate);
        } else {
            challenge = new Challenge(tag, title, startDate, voteDate, endDate);
            challenge.setDescription(notes);
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
