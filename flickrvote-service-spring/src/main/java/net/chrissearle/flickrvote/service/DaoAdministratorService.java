package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.AdministratorService;
import net.chrissearle.flickrvote.service.model.PhotographerItem;
import net.chrissearle.flickrvote.service.model.impl.PhotographerItemInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service("administratorService")
public class DaoAdministratorService implements AdministratorService {
    private PhotographyDao photographyDao;

    @Autowired
    public DaoAdministratorService(PhotographyDao photographyDao) {
        this.photographyDao = photographyDao;
    }

    public Set<PhotographerItem> getAdmins() {
        Set<PhotographerItem> photographers = new HashSet<PhotographerItem>();

        for (Photographer photographer : photographyDao.getAdmins()) {
            photographers.add(new PhotographerItemInstance(photographer));
        }

        return photographers;
    }
}
