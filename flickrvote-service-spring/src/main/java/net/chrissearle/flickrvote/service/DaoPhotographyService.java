package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.dao.ImageDao;
import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.flickr.FlickrPhotographer;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.ChallengeState;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.PhotographerItem;
import net.chrissearle.flickrvote.service.model.impl.ChallengeItemInstance;
import net.chrissearle.flickrvote.service.model.impl.ImageItemInstance;
import net.chrissearle.flickrvote.service.model.impl.PhotographerItemInstance;
import net.chrissearle.flickrvote.twitter.TwitterService;
import net.chrissearle.flickrvote.twitter.TwitterServiceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.*;

@Service("photographyService")
@Transactional
public class DaoPhotographyService implements PhotographyService {
    private Logger logger = Logger.getLogger(DaoPhotographyService.class);

    private final PhotographyDao photographyDao;
    private ChallengeDao challengeDao;
    private ImageDao imageDao;

    private FlickrService flickrService;
    private TwitterService twitterService;

    @Autowired
    public DaoPhotographyService(PhotographyDao photographyDao, ChallengeDao challengeDao, ImageDao imageDao,
                                 FlickrService flickrService, TwitterService twitterService) {
        this.photographyDao = photographyDao;
        this.challengeDao = challengeDao;
        this.imageDao = imageDao;

        this.flickrService = flickrService;
        this.twitterService = twitterService;
    }

    public void setAdministrator(String id, Boolean adminFlag) {
        Photographer photographer = photographyDao.findById(id);

        if (photographer != null) {
            photographer.setAdministrator(adminFlag);
            photographyDao.persist(photographer);
        }
    }

    public Boolean isAdministrator(String username) {
        Photographer photographer = photographyDao.findByUsername(username);

        return photographer != null && photographer.isAdministrator();
    }

    public PhotographerItem retrieveAndStorePhotographer(String id) {
        // Check to see if present
        Photographer photographer = photographyDao.findById(id);

        FlickrPhotographer flickrPhotographer = flickrService.getUserByFlickrId(id);

        if (photographer == null) {
            photographer = new Photographer(flickrPhotographer.getToken(), flickrPhotographer.getUsername(),
                    flickrPhotographer.getRealname(), flickrPhotographer.getFlickrId(), flickrPhotographer.getIconUrl());
        } else {
            photographer.setUsername(flickrPhotographer.getUsername());
            photographer.setFullname(flickrPhotographer.getRealname());
            photographer.setIconUrl(flickrPhotographer.getIconUrl());
        }

        photographyDao.persist(photographer);

        return new PhotographerItemInstance(photographer);
    }

    public PhotographerItem checkLoginAndStore(String frob) {
        FlickrPhotographer flickrPhotographer = flickrService.authenticate(frob);

        // Check to see if present
        String flickrId = flickrPhotographer.getFlickrId();

        Photographer photographer = photographyDao.findById(flickrId);

        if (photographer == null) {
            photographer = new Photographer();

            photographer.setId(flickrId);
            photographer.setAdministrator(false);
        }

        photographer.setFullname(flickrPhotographer.getRealname());
        photographer.setUsername(flickrPhotographer.getUsername());
        photographer.setToken(flickrPhotographer.getToken());
        photographer.setIconUrl(flickrPhotographer.getIconUrl());

        photographyDao.persist(photographer);

        return new PhotographerItemInstance(photographer);
    }

    public ChallengeItem getChallengeImages(String tag) {
        Set<ImageItem> images = new HashSet<ImageItem>();

        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null) {
            return null;
        }

        Map<String, ImageItem> seenImages = new HashMap<String, ImageItem>();

        if (challenge.getVotingState() == ChallengeState.OPEN) {
            // Grab images from flickr
            for (FlickrImage image : flickrService.searchImagesByTag(tag, challenge.getStartDate())) {
                ImageItemInstance imageItem = new ImageItemInstance(image);
                images.add(imageItem);
                seenImages.put(image.getPhotographer().getFlickrId(), imageItem);
            }
        }

        // Grab images from db. Merge in if flickr images have already been fetched
        for (Image image : challenge.getImages()) {
            final String photographerId = image.getPhotographer().getId();

            if (seenImages.containsKey(photographerId)) {
                // The db has an image for a photographer that has been found by search.
                // This only happens when administrators add an image manually.
                // In this case - use the db image.
                images.remove(seenImages.get(photographerId));
            }

            images.add(new ImageItemInstance(image));
        }

        return new ChallengeItemInstance(challenge.getTag(), challenge.getName(), images);
    }

    public URL getLoginUrl() {
        return flickrService.getLoginUrl();
    }

    public ImageItem retrieveAndStoreImage(String id, String tag) {
        return retrieveAndStoreImage(id, tag, true);
    }

    public ImageItem retrieveAndStoreImage(String id, String tag, boolean replaceExisting) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null)
            return null;

        Image image = imageDao.findById(id);

        FlickrImage flickrImage = flickrService.getImageByFlickrId(id);

        if (image == null) {
            if (logger.isInfoEnabled()) {
                logger.info("Storing image " + flickrImage.getFlickrId());
            }
            String photographerId = flickrImage.getPhotographer().getFlickrId();
            Photographer photographer = photographyDao.findById(photographerId);

            Image existing = checkForExistingImage(photographer, tag);

            if (existing != null) {
                // Found an existing image for this challenge by this photographer
                if (replaceExisting) {
                    // Normal functionality - remove the existing one - we're doing an update
                    photographer.removeImage(existing);
                    imageDao.remove(existing);
                } else {
                    // Special case - e.g. we don't want to replace admin overridden images at freeze.
                    return new ImageItemInstance(existing);
                }
            }

            image = new Image();
            image.setId(flickrImage.getFlickrId());
            image.setMediumImage(flickrImage.getImageUrl());
            image.setPage(flickrImage.getUrl());
            image.setTitle(flickrImage.getTitle());
            image.setPostedDate(flickrImage.getPostedDate());


            if (photographer == null) {
                retrieveAndStorePhotographer(photographerId);

                photographer = photographyDao.findById(photographerId);
            }

            photographer.addImage(image);

            photographyDao.persist(photographer);

            challenge.addImage(image);

            challengeDao.persist(challenge);
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("Refreshing image " + flickrImage.getFlickrId());
            }

            image.setTitle(flickrImage.getTitle());
            image.setPostedDate(flickrImage.getPostedDate());
            image.setPage(flickrImage.getUrl());
            image.setMediumImage(flickrImage.getImageUrl());

            imageDao.persist(image);
        }

        return new ImageItemInstance(image);
    }

    private Image checkForExistingImage(Photographer photographer, String tag) {
        for (Image image : photographer.getImages()) {
            if (image.getChallenge().getTag().equals(tag)) {
                return image;
            }
        }
        return null;
    }

    public void setScore(String imageId, Long score) {
        Image image = imageDao.findById(imageId);

        if (image != null) {
            image.setFinalVoteCount(score);
        }

        imageDao.persist(image);
    }

    public Set<ImageItem> getImagesForPhotographer(String id) {
        Photographer photographer = photographyDao.findById(id);

        if (photographer == null || photographer.getImages() == null || photographer.getImages().size() == 0) {
            return null;
        }

        Set<ImageItem> images = new HashSet<ImageItem>();

        for (Image image : photographer.getImages()) {
            images.add(new ImageItemInstance(image));
        }

        return images;
    }

    public void freezeChallenge() {
        if (logger.isInfoEnabled()) {
            logger.info("Freezing challenge");
        }

        Challenge challenge = challengeDao.getVotingChallenge();

        if (challenge == null) {
            return;
        }

        if (logger.isInfoEnabled()) {
            logger.info("Freezing challenge : " + challenge);
        }


        List<FlickrImage> images = flickrService.searchImagesByTag(challenge.getTag(), challenge.getStartDate());

        for (FlickrImage image : images) {
            retrieveAndStoreImage(image.getFlickrId(), challenge.getTag(), false);
        }
    }

    public Set<ImageItem> getGoldWinners() {
        Set<ImageItem> images = new HashSet<ImageItem>();

        for (Image image : imageDao.getImagesWithRank(1L)) {
            images.add(new ImageItemInstance(image));
        }

        return images;
    }

    public PhotographerItem setTwitter(String id, String twitter) {
        Photographer photographer = photographyDao.findById(id);

        if (photographer != null) {
            photographer.setTwitter(twitter);

            photographyDao.persist(photographer);

            try {
                twitterService.follow(twitter);
            } catch (TwitterServiceException tse) {
                if (logger.isEnabledFor(Level.WARN)) {
                    logger.warn("Unable to follow" + tse.getMessage(), tse);
                }
            }

            return new PhotographerItemInstance(photographer);
        }

        return null;
    }

    public List<PhotographerItem> getPhotographers() {
        List<PhotographerItem> photographers = new ArrayList<PhotographerItem>();

        for (Photographer photographer : photographyDao.all()) {
            photographers.add(new PhotographerItemInstance(photographer));
        }

        return photographers;
    }

    public PhotographerItem findById(String id) {
        Photographer photographer = photographyDao.findById(id);

        if (photographer != null) {
            return new PhotographerItemInstance(photographer);
        }

        return null;
    }

    public Map<String, String> checkSearch(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        return flickrService.checkSearch(tag, challenge.getStartDate());
    }

    public boolean checkTwitterExists(String twitter) {
        return twitterService.twitterExists(twitter);
    }

    public ImageItem findImageById(String imageId) {
        Image image = imageDao.findById(imageId);

        if (image != null) {
            return new ImageItemInstance(image);
        }

        return null;
    }
}
