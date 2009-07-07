package net.chrissearle.flickrvote.model;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ModelTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    private Connection connection;

    @BeforeTest
    private void initialize() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbcDriver");
        connection = DriverManager.getConnection("jdbc:hsqldb:mem:unit-testing-jpa", "sa", "");
        emf = Persistence.createEntityManagerFactory("FlickrVote-Model-Test");
        em = emf.createEntityManager();
    }


    @AfterTest
    protected void closeDb() throws Exception {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }

        connection.createStatement().execute("SHUTDOWN");
    }

    @Test
    public void testPersistChallenge() {
        em.getTransaction().begin();
        
        Challenge challenge = new Challenge(null, "#TwPhCh001", "Rødt", null, null, null);

        em.persist(challenge);

        assert em.contains(challenge) : "Failed to save challenge";

/*
            Group g = new Group();
            g1.addUser(u);

            em.persist(g);
            assertTrue(em.contains(g));

            g.removeUser(u);
            em.remove(u);
            em.merge(g);
            assertFalse(em.contains(u));
*/

        em.getTransaction().commit();

    }
}



