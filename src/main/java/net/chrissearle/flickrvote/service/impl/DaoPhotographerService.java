package net.chrissearle.flickrvote.service.impl;

import net.chrissearle.flickrvote.dao.PhotographerDao;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("photographerService")
public class DaoPhotographerService implements PhotographerService {
    @Autowired
    private PhotographerDao dao;

    public Photographer persistUser(Photographer photographer) {
        if (dao.findByUsername(photographer.getUsername()) != null) {
            return dao.update(photographer);
        } else {
            dao.save(photographer);

            return dao.findByUsername(photographer.getUsername());
        }
    }

    public Photographer getUser(String username) {
        return dao.findByUsername(username);
    }
}
