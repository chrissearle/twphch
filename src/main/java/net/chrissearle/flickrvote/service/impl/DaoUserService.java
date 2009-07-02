package net.chrissearle.flickrvote.service.impl;

import net.chrissearle.flickrvote.service.UserService;
import net.chrissearle.flickrvote.model.User;
import net.chrissearle.flickrvote.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class DaoUserService implements UserService {
    @Autowired
    private UserDao dao;

    public User persistUser(User user) {
        if (dao.findByUsername(user.getUsername()) != null) {
            return dao.update(user);
        } else {
            dao.save(user);

            return dao.findByUsername(user.getUsername());
        }
    }

    public User getUser(String username) {
        return dao.findByUsername(username);
    }
}
