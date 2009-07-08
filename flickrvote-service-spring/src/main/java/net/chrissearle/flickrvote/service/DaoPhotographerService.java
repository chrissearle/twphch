package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.PhotographerDao;
import net.chrissearle.flickrvote.model.Photographer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("photographerService")
public class DaoPhotographerService implements PhotographerService {
    @Autowired
    private PhotographerDao dao;

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
}
