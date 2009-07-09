package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.PhotographerDao;
import net.chrissearle.flickrvote.flickr.FlickrAuth;
import net.chrissearle.flickrvote.flickr.FlickrService;
import net.chrissearle.flickrvote.model.Photographer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DaoPhotographerService implements PhotographerService {

    private final PhotographerDao dao;
    private FlickrService flickrService;

    @Autowired
    public DaoPhotographerService(PhotographerDao dao, FlickrService flickrService) {
        this.dao = dao;
        this.flickrService = flickrService;
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

        if (photographer != null) {
            return photographer.isAdministrator();
        }

        return false;
    }

    public void retrieveAndStore(String id) {
        // Check to see if present
        Photographer photographer = dao.findByFlickrId(id);

        if (photographer == null) {
            FlickrAuth auth = flickrService.getUserByFlickrId(id);

            photographer = new Photographer(auth.getToken(), auth.getUsername(), auth.getRealname(), auth.getFlickrId());

            dao.save(photographer);
        }
    }

    public void checkLoginAndStore(String frob) {
        FlickrAuth auth = flickrService.authenticate(frob);

        // Check to see if present
        Photographer photographer = dao.findByFlickrId(auth.getFlickrId());

        if (photographer == null) {
            photographer = new Photographer();

            photographer.setId(auth.getFlickrId());
            photographer.setAdministrator(false);
        }

        photographer.setFullname(auth.getRealname());
        photographer.setUsername(auth.getUsername());
        photographer.setToken(auth.getToken());

        dao.save(photographer);
    }
}
