package net.chrissearle.flickrvote.model;

import org.joda.time.DateTime;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class ModelTest {
    private EntityManagerFactory emf;
    private EntityManager em;
    private static final String CHALLENGE_TAG = "#TwPhCh001";
    private static final String CHALLENGE_TITLE = "Rødt";
    private static final String PHOTOGRAPHER_USER = "user";
    private static final String PHOTOGRAPHER_FULLNAME = "full";
    private static final String PHOTOGRAPHER_FLICKR_ID = "foo";
    private static final String IMAGE_FLICKR_ID = "Foo";
    private static final String IMAGE_MEDIUM_URL = "http://www.foo.com";
    private static final String IMAGE_PAGE_URL = "http://www.bar.com";
    private static final String IMAGE_TITLE = "Test image";
    private static final Date START_DATE = new DateTime(2009, 5, 8, 18, 0, 0, 0).toDate();
    private static final Date END_DATE = new DateTime(2009, 5, 17, 21, 0, 0, 0).toDate();
    private static final Date VOTE_DATE = new DateTime(2009, 5, 15, 18, 0, 0, 0).toDate();
    private static final String PHOTOGRAPHER_TOKEN = "0250295209475-9235720975";

    @BeforeTest
    private void initialize() {
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
    }

    @BeforeMethod
    protected void startTransaction() {
        em.getTransaction().begin();
    }

    @AfterMethod
    protected void endTransaction() {
        em.getTransaction().commit();
    }

    @Test
    public void testPersistChallenge() {
        Challenge challenge = new Challenge(CHALLENGE_TAG, CHALLENGE_TITLE, START_DATE, VOTE_DATE, END_DATE);

        em.persist(challenge);

        assert em.contains(challenge) : "Failed to save challenge";
    }

    @Test(dependsOnMethods = {"testPersistChallenge"})
    public void testChallengeFields() {
        Challenge challenge = getChallenge();

        assert challenge.getTag().equals(CHALLENGE_TAG) : "Tag was incorrect";
        assert challenge.getName().equals(CHALLENGE_TITLE) : "Name was incorrect";
        assert challenge.getStartDate().equals(START_DATE) : "Start date was incorrect";
        assert challenge.getVotingOpenDate().equals(VOTE_DATE) : "Voting date was incorrect";
        assert challenge.getEndDate().equals(END_DATE) : "End date was incorrect";

        String challengeString = challenge.toString();

        assert challengeString.contains(CHALLENGE_TAG) &&
                challengeString.contains(CHALLENGE_TITLE) : "toString did not contain correct fields";

    }

    private Challenge getChallenge() {
        Query qC = em.createQuery("select c from Challenge c where c.tag = :tag");
        qC.setParameter("tag", CHALLENGE_TAG);
        return (Challenge) qC.getSingleResult();
    }

    @Test
    public void testPersistPhotographer() {
        Photographer photographer = new Photographer(PHOTOGRAPHER_TOKEN, PHOTOGRAPHER_USER, PHOTOGRAPHER_FULLNAME, PHOTOGRAPHER_FLICKR_ID);

        em.persist(photographer);

        assert em.contains(photographer) : "Failed to save photographer";
    }

    @Test(dependsOnMethods = {"testPersistPhotographer"})
    public void testPhotographerFields() {
        Photographer photographer = getPhotographer();

        assert photographer.getId().equals(PHOTOGRAPHER_FLICKR_ID) : "Flickr ID was incorrect";
        assert photographer.getFullname().equals(PHOTOGRAPHER_FULLNAME) : "Full name was incorrect";
        assert photographer.getToken().equals(PHOTOGRAPHER_TOKEN) : "Token was incorrect";
        assert photographer.getUsername().equals(PHOTOGRAPHER_USER) : "Username was incorrect";
        assert !photographer.isAdministrator() : "Photographer was incorrectly marked as admin";
        assert photographer.getId() != null : "ID was null";

        String photographerString = photographer.toString();

        assert photographerString.contains(PHOTOGRAPHER_FULLNAME) &&
                photographerString.contains(PHOTOGRAPHER_USER) : "toString did not contain correct fields";
    }

    private Photographer getPhotographer() {
        Query query = em.createQuery("select p from Photographer p where p.username = :user");
        query.setParameter("user", PHOTOGRAPHER_USER);
        return (Photographer) query.getSingleResult();
    }


    @Test(dependsOnMethods = {"testPersistChallenge", "testPersistPhotographer"})
    public void testPersistImage() {
        Image image = new Image();

        image.setId(IMAGE_FLICKR_ID);
        image.setMediumImage(IMAGE_MEDIUM_URL);
        image.setPage(IMAGE_PAGE_URL);
        image.setTitle(IMAGE_TITLE);

        em.persist(image);

        assert em.contains(image) : "Failed to save image";

        Challenge challenge = getChallenge();

        Photographer photographer = getPhotographer();

        // Call these twice to make sure we are protected against double calls
        photographer.addImage(image);
        photographer.addImage(image);

        challenge.addImage(image);
        challenge.addImage(image);

        em.persist(image);

        assert image.getChallenge().getTag().equals(challenge.getTag()) : "Image did not have challenge set correctly";

        assert image.getPhotographer().getId().equals(photographer.getId()) : "Image did not have photographer set correctly";
    }

    @Test(dependsOnMethods = {"testPersistImage"})
    public void testRetrieveImageByPhotographer() {
        Image image = getImage();

        Photographer photographer = getPhotographer();

        assert photographer.getImages().contains(image) : "Photographer did not contain image";
    }

    @Test(dependsOnMethods = {"testPersistImage"})
    public void testImageFields() {
        Image image = getImage();

        assert image.getId().equals(IMAGE_FLICKR_ID) : "Flickr ID was incorrect";
        assert image.getMediumImage().equals(IMAGE_MEDIUM_URL) : "Medium URL was incorrect";
        assert image.getPage().equals(IMAGE_PAGE_URL) : "Page URL was incorrect";
        assert image.getTitle().equals(IMAGE_TITLE) : "Title was incorrect";
        assert image.getId() != null : "ID was null";
        assert image.getFinalVoteCount() == 0 : "Image has votes wihtout voting";

        String imageString = image.toString();

        assert imageString.contains(IMAGE_TITLE) : "toString did not contain correct fields";
    }

    private Image getImage() {
        Query qI = em.createQuery("select i from Image i where i.id = :flickr");
        qI.setParameter("flickr", IMAGE_FLICKR_ID);
        return (Image) qI.getSingleResult();
    }

    @Test(dependsOnMethods = {"testPersistImage"})
    public void testRetrieveImageByChallenge() {
        Image image = getImage();

        Challenge challenge = getChallenge();

        assert challenge.getImages().contains(image) : "Challenge did not contain image";
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetVotingChallenge() {
        DateTime today = new DateTime();
        Challenge challenge = new Challenge("#Test1", "Test 1", today.minusDays(3).toDate(),
                today.minusDays(1).toDate(), today.plusDays(1).toDate());

        em.persist(challenge);

        Query query = em.createQuery("select c from Challenge c where c.votingOpenDate <= :now and c.endDate > :now");
        query.setParameter("now", new Date());

        List<Challenge> challenges = query.getResultList();

        assert challenges.size() == 1 : "Incorrect challenge count " + challenges.size();

        assert challenges.iterator().next().getTag().equals("#Test1") : "Incorrect challenge returned";
    }
}



