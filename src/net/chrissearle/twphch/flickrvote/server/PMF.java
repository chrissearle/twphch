package net.chrissearle.twphch.flickrvote.server;

import javax.jdo.PersistenceManagerFactory;
import javax.jdo.JDOHelper;

/**
 * Taken straight from the appengine tutorial - http://code.google.com/appengine/docs/java/gettingstarted/usingdatastore.html
 */
public final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
            JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {
    }

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}
