package net.chrissearle.flickrvote.service.impl;

import net.chrissearle.flickrvote.dao.PhotographerDao;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.PhotographerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("photographerService")
public class DaoPhotographerService implements PhotographerService {
    @Autowired
    private PhotographerDao dao;

    private Logger log = Logger.getLogger(DaoPhotographerService.class);

    public Photographer persistUser(Photographer photographer) {
        if (log.isDebugEnabled()) {
            log.debug("Persisting photographer: " + photographer);
        }

        dao.save(photographer);

        return dao.findByUsername(photographer.getUsername());
    }

    public Photographer getUser(String username) {
        return dao.findByUsername(username);
    }
}
