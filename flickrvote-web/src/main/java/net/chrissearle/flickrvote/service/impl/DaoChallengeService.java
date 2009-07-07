package net.chrissearle.flickrvote.service.impl;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographerService;
import net.chrissearle.flickrvote.flickr.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.people.User;

@Service("challengeService")
public class DaoChallengeService implements ChallengeService {
    @Autowired
    private ChallengeDao dao;

    @Autowired
    private FlickrService flickrService;

    @Autowired
    private PhotographerService photographerService;

    public Challenge getChallenge(Long id) {
        return dao.findById(id);
    }

    public List<Challenge> getChallenges() {
        return dao.getAll();
    }

    public void addChallenge(Challenge challenge) {
        dao.save(challenge);
    }

    public List<Challenge> getClosedChallenges() {
        return dao.getClosedChallenges();
    }

    public Challenge getCurrentChallenge() {
        return dao.getCurrentChallenge();
    }

    public Challenge getVotingChallenge() {
        return dao.getVotingChallenge();
    }

    public void populateChallengeFromFlickr(Challenge challenge) {
/*
        List<Photo> photos = flickrService.searchImagesByTag(challenge.getTag());

        for (Photo p : photos) {
            Image image = new Image();

            image.setChallenge(challenge);
            challenge.addImage(image);
            image.setMediumImage(p.getMediumUrl());
            image.setPage(p.getUrl());
            Photographer photographer = photographerService.findByFlickrId(p.getId());
            if (photographer == null) {
                User user = flickrService.getUser(p.getOwner().getUsername());

                photographer = new Photographer(null, null, user.getUsername(), user.getRealName(), user.getId());
            }
            image.setPhotographer(photographer);
            photographer.addImage(image);
            image.setTitle(p.getTitle());

            photographerService.persistUser(photographer);
        }
        dao.save(challenge);
*/
    }
}
