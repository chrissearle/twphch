package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.PhotographerItem;

import java.util.Set;

public interface AdministratorService {
    Set<PhotographerItem> getAdmins();
}
