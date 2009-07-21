package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.flickr.FlickrAuth;
import net.chrissearle.flickrvote.flickr.FlickrImage;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.List;

@Service
@Transactional
public class DaoPhotographyService implements PhotographyService {

    private final PhotographyDao dao;
    private FlickrService flickrService;
    private ChallengeDao challengeDao;

    @Autowired
    public DaoPhotographyService(PhotographyDao dao, FlickrService flickrService, ChallengeDao challengeDao) {
        this.dao = dao;
        this.flickrService = flickrService;
        this.challengeDao = challengeDao;
    }

    public void addPhotographer(String token, String username, String fullname, String flickrId) {
        Photographer photographer = new Photographer(token, username, fullname, flickrId);

        dao.save(photographer);
    }

    public void setAdministrator(String username, Boolean adminFlag) {
        Photographer photographer = dao.findByUsername(username);

        if (photographer != null) {
            photographer.setAdministrator(adminFlag);
            dao.save(photographer);
        }
    }

    public Boolean isAdministrator(String username) {
        Photographer photographer = dao.findByUsername(username);

        return photographer != null && photographer.isAdministrator();
    }

    public PhotographerInfo retrieveAndStorePhotographer(String id) {
        // Check to see if present
        Photographer photographer = dao.findPhotographerByFlickrId(id);

        if (photographer == null) {
            FlickrAuth auth = flickrService.getUserByFlickrId(id);

            photographer = new Photographer(auth.getToken(), auth.getUsername(), auth.getRealname(), auth.getFlickrId());

            dao.save(photographer);

            return new PhotographerInfo(photographer);
        }

        return null;
    }

    public PhotographerInfo checkLoginAndStore(String frob) {
        FlickrAuth auth = flickrService.authenticate(frob);

        // Check to see if present
        Photographer photographer = dao.findPhotographerByFlickrId(auth.getFlickrId());

        if (photographer == null) {
            photographer = new Photographer();

            photographer.setId(auth.getFlickrId());
            photographer.setAdministrator(false);
        }

        photographer.setFullname(auth.getRealname());
        photographer.setUsername(auth.getUsername());
        photographer.setToken(auth.getToken());

        dao.save(photographer);

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

        Image image = dao.findImageByFlickrId(id);

        if (image == null) {
            FlickrImage flickrImage = flickrService.getImageByFlickrId(id);

            image = new Image();
            image.setId(flickrImage.getFlickrId());
            image.setMediumImage(flickrImage.getImageUrl());
            image.setPage(flickrImage.getUrl());
            image.setTitle(flickrImage.getTitle());

            Photographer photographer = dao.findPhotographerByFlickrId(flickrImage.getPhotographerFlickrId());

            if (photographer == null) {
                retrieveAndStorePhotographer(flickrImage.getPhotographerFlickrId());

                photographer = dao.findPhotographerByFlickrId(flickrImage.getPhotographerFlickrId());
            }

            photographer.addImage(image);

            dao.save(photographer);

            challenge.addImage(image);

            challengeDao.save(challenge);

            return new ImageInfo(image);
        }

        return null;
    }

    public void setScore(String imageId, Long score) {
        Image image = dao.findImageByFlickrId(imageId);

        if (image != null) {
            image.setFinalVoteCount(score);
        }

        dao.save(image);
    }
}
