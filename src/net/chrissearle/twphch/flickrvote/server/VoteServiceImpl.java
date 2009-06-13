package net.chrissearle.twphch.flickrvote.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.chrissearle.twphch.flickrvote.client.VoteService;
import net.chrissearle.twphch.flickrvote.client.VotingRound;

import java.util.*;

public class VoteServiceImpl extends RemoteServiceServlet implements VoteService {
    public List<VotingRound> getVotes() {
        List<VotingRound> votes = new ArrayList<VotingRound>();


        // FIXME - Dummy Data
        Calendar cal = new GregorianCalendar();

        cal.set(Calendar.YEAR, 2009);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        Date start = null;
        Date end = null;

        cal.set(Calendar.DATE, 15);
        cal.set(Calendar.HOUR, 18);
        start = cal.getTime();

        cal.set(Calendar.DATE, 17);
        cal.set(Calendar.HOUR, 21);
        end = cal.getTime();

        votes.add(new VotingRound("#TwPhCh001",
                "R¿dt",
                "http://www.flickr.com/groups/twphch/discuss/72157617907501451/", start, end));

        cal.set(Calendar.DATE, 22);
        cal.set(Calendar.HOUR, 18);
        start = cal.getTime();

        cal.set(Calendar.DATE, 24);
        cal.set(Calendar.HOUR, 21);
        end = cal.getTime();

        votes.add(new VotingRound("#TwPhCh002",
                "Mat",
                "http://www.flickr.com/groups/twphch/discuss/72157618194690402/", start, end));

        cal.set(Calendar.DATE, 29);
        cal.set(Calendar.HOUR, 18);
        start = cal.getTime();

        cal.set(Calendar.DATE, 31);
        cal.set(Calendar.HOUR, 21);
        end = cal.getTime();

        votes.add(new VotingRound("#TwPhCh003",
                "Rundt",
                "http://www.flickr.com/groups/twphch/discuss/72157618624761142/", start, end));

        cal.set(Calendar.MONTH, Calendar.JUNE);

        cal.set(Calendar.DATE, 5);
        cal.set(Calendar.HOUR, 18);
        start = cal.getTime();

        cal.set(Calendar.DATE, 7);
        cal.set(Calendar.HOUR, 21);
        end = cal.getTime();

        votes.add(new VotingRound("#TwPhCh004",
                "De 4 elementer: Jord, Vann, Vind, Brann",
                "http://www.flickr.com/groups/twphch/discuss/72157618959245304/", start, end));

        cal.set(Calendar.DATE, 12);
        cal.set(Calendar.HOUR, 18);
        start = cal.getTime();

        cal.set(Calendar.DATE, 14);
        cal.set(Calendar.HOUR, 21);
        end = cal.getTime();

        votes.add(new VotingRound("#TwPhCh005",
                "Gatelangs",
                "http://www.flickr.com/groups/twphch/discuss/72157619286062184/", start, end));

        cal.set(Calendar.DATE, 19);
        cal.set(Calendar.HOUR, 18);
        start = cal.getTime();

        cal.set(Calendar.DATE, 21);
        cal.set(Calendar.HOUR, 21);
        end = cal.getTime();

        votes.add(new VotingRound("#TwPhCh006",
                "Bevegelse",
                "http://www.flickr.com/groups/twphch/discuss/72157619551509299/", start, end));

        return votes;
    }
}