package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.User;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class JpaUserDao extends JpaDaoSupport implements UserDao {
    public User findById(long id) {
        return getJpaTemplate().find(User.class, id);
    }

    public User findByUsername(String username) {
        return (User)getJpaTemplate().find("select u from User u where u.username = ?1", username).iterator().next();
    }

    public User findByToken(String token) {
        return (User)getJpaTemplate().find("select u from User u where u.token = ?1", token).iterator().next();
    }

    public void save(User user) {
        getJpaTemplate().persist(user);
    }

    public User update(User user) {
        return getJpaTemplate().merge(user);
    }

    public void delete(User user) {
        getJpaTemplate().remove(user);
    }
}
