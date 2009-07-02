package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.model.User;

public interface UserService {
    User persistUser(User user);

    User getUser(String username);
}