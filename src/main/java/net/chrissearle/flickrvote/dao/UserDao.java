package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.User;

public interface UserDao {
    public User findById(long id);

    public User findByUsername(String username);

    public User findByToken(String token);

    public void save(User user);

    public User update(User user);

    public void delete(User user);
}