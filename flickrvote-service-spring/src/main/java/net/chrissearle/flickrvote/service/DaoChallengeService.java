package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.model.Vote;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import net.chrissearle.flickrvote.twitter.TwitterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

@Service
@Transactional
public class DaoChallengeService implements ChallengeService {
    private Logger logger = Logger.getLogger(DaoChallengeService.class);

    private final ChallengeDao challengeDao;
    private final PhotographyDao photographyDao;
    private final ShortUrlService shortUrlService;
    private final TwitterService twitterService;
    private final FlickrService flickrService;


    @Autowired
    public DaoChallengeService(ChallengeDao challengeDao, PhotographyDao photographyDao, ShortUrlService shortUrlService,
                               TwitterService twitterService, FlickrService flickrService) {
        this.challengeDao = challengeDao;
        this.photographyDao = photographyDao;
        this.shortUrlService = shortUrlService;
        this.twitterService = twitterService;
        this.flickrService = flickrService;
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

    public void openVoting() {
        Challenge challenge = challengeDao.getVotingChallenge();

        if (challenge == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No voting challenge found to open");
            }

            return;
        }

        if (logger.isInfoEnabled()) {
            logger.info("Opening voting for " + challenge);
        }

        // TODO - cannot hard code this
        String votingUrl = shortUrlService.shortenUrl("http://www.chrissearle.org/twitterphotochallenge/vote/showVotePhotos.action");

        // TODO - cannot hard code this
        twitterService.twitter(MessageFormat.format("Voting opens for {0} - {1} : {2}",
                challenge.getTag(), challenge.getName(), votingUrl));

        flickrService.postOpenVote(challenge.getTag(), challenge.getName(), challenge.getEndDate());
    }

    public void announceNewChallenge() {
        Challenge challenge = challengeDao.getCurrentChallenge();

        if (challenge == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No current challenge found to announce");
            }

            return;
        }

        if (logger.isInfoEnabled()) {
            logger.info("Announcing for " + challenge);
        }

        // TODO - cannot hard code this
        String currentUrl = shortUrlService.shortenUrl("http://www.chrissearle.org/twitterphotochallenge/");

        // TODO - cannot hard code this
        twitterService.twitter(MessageFormat.format("New Challenge {0} - {1} : {2}",
                challenge.getTag(), challenge.getName(), currentUrl));

        flickrService.postNewChallenge(challenge.getTag(), challenge.getName(), challenge.getVotingOpenDate(), challenge.getEndDate());
    }

    public void annouceResults() {
        Challenge challenge = challengeDao.getVotedChallenge();

        if (challenge == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No challenge found to handle results");
            }

            return;
        }

        if (logger.isInfoEnabled()) {
            logger.info("Results for " + challenge);
        }

        // Sum up votes
        List<Image> images = challenge.getImages();

        for (Image image : images) {
            image.setFinalVoteCount((long) image.getVotes().size());
            photographyDao.save(image);
        }

        photographyDao.clearVotes();

        // TODO - cannot hard code this
        String resultsUrl = shortUrlService.shortenUrl(MessageFormat.format("http://www.chrissearle.org/twitterphotochallenge/show.action?challengeTag={0}",
                challenge.getTag()));

        // TODO - cannot hard code this
        twitterService.twitter(MessageFormat.format("Challenge Results {0} - {1} : {2}",
                challenge.getTag(), challenge.getName(), resultsUrl));

        // TODO - populate
        List<FlickrImage> goldImages = new ArrayList<FlickrImage>();
        List<FlickrImage> silverImages = new ArrayList<FlickrImage>();
        List<FlickrImage> bronzeImages = new ArrayList<FlickrImage>();

        flickrService.postResultsAndAddBadges(challenge.getTag(), challenge.getName(), goldImages, silverImages, bronzeImages);
    }
}
