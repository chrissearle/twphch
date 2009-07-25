package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.dao.ImageDao;
import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.flickr.FlickrAuth;
import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DaoPhotographyService implements PhotographyService {
    private Logger logger = Logger.getLogger(DaoPhotographyService.class);

    private final PhotographyDao photographyDao;
    private ChallengeDao challengeDao;
    private ImageDao imageDao;

    private FlickrService flickrService;

    @Autowired
    public DaoPhotographyService(PhotographyDao photographyDao, ChallengeDao challengeDao, ImageDao imageDao,
                                 FlickrService flickrService) {
        this.photographyDao = photographyDao;
        this.challengeDao = challengeDao;
        this.imageDao = imageDao;

        this.flickrService = flickrService;
    }

    public void addPhotographer(String token, String username, String fullname, String flickrId) {
        Photographer photographer = new Photographer(token, username, fullname, flickrId);

        photographyDao.persist(photographer);
    }

    public void setAdministrator(String username, Boolean adminFlag) {
        Photographer photographer = photographyDao.findByUsername(username);

        if (photographer != null) {
            photographer.setAdministrator(adminFlag);
            photographyDao.persist(photographer);
        }
    }

    public Boolean isAdministrator(String username) {
        Photographer photographer = photographyDao.findByUsername(username);

        return photographer != null && photographer.isAdministrator();
    }

    public PhotographerInfo retrieveAndStorePhotographer(String id) {
        // Check to see if present
        Photographer photographer = photographyDao.findById(id);

        if (photographer == null) {
            FlickrAuth auth = flickrService.getUserByFlickrId(id);

            photographer = new Photographer(auth.getToken(), auth.getUsername(), auth.getRealname(), auth.getFlickrId());

            photographyDao.persist(photographer);

            return new PhotographerInfo(photographer);
        }

        return null;
    }

    public PhotographerInfo checkLoginAndStore(String frob) {
        FlickrAuth auth = flickrService.authenticate(frob);

        // Check to see if present
        Photographer photographer = photographyDao.findById(auth.getFlickrId());

        if (photographer == null) {
            photographer = new Photographer();

            photographer.setId(auth.getFlickrId());
            photographer.setAdministrator(false);
        }

        photographer.setFullname(auth.getRealname());
        photographer.setUsername(auth.getUsername());
        photographer.setToken(auth.getToken());

        photographyDao.persist(photographer);

        return new PhotographerInfo(photographer);
    }

    public List<FlickrImage> searchImagesByTag(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null) {
            return null;
        }

        return flickrService.searchImagesByTag(tag, challenge.getStartDate());
    }

    public URL getLoginUrl() {
        return flickrService.getLoginUrl();
    }

    public ImageInfo retrieveAndStoreImage(String id, String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        if (challenge == null)
            return null;

        Image image = imageDao.findById(id);

        if (image == null) {
            FlickrImage flickrImage = flickrService.getImageByFlickrId(id);

            image = new Image();
            image.setId(flickrImage.getFlickrId());
            image.setMediumImage(flickrImage.getImageUrl());
            image.setPage(flickrImage.getUrl());
            image.setTitle(flickrImage.getTitle());

            Photographer photographer = photographyDao.findById(flickrImage.getPhotographerFlickrId());

            if (photographer == null) {
                retrieveAndStorePhotographer(flickrImage.getPhotographerFlickrId());

                photographer = photographyDao.findById(flickrImage.getPhotographerFlickrId());
            }

            photographer.addImage(image);

            photographyDao.persist(photographer);

            challenge.addImage(image);

            challengeDao.persist(challenge);

            return new ImageInfo(image);
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

    public List<ImageInfo> getImagesForPhotographer(String id) {
        Photographer photographer = photographyDao.findById(id);

        if (photographer == null || photographer.getImages() == null || photographer.getImages().size() == 0) {
            return null;
        }
        
        List<ImageInfo> images = new ArrayList<ImageInfo>(photographer.getImages().size());

        for (Image image : photographer.getImages()) {
            images.add(new ImageInfo(image));
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
            retrieveAndStoreImage(image.getFlickrId(), challenge.getTag());
        }
    }

    public List<ImageInfo> getGoldWinners() {
        List<ImageInfo> images = new ArrayList<ImageInfo>();

        for (Image image : imageDao.getImagesWithRank(1L)) {
            images.add(new ImageInfo(image));
        }

        return images;
    }
}
