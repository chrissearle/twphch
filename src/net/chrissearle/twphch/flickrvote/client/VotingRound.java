package net.chrissearle.twphch.flickrvote.client;

import java.util.Date;
import java.io.Serializable;

public class VotingRound implements Serializable {
    private String round = null;
    private String name = null;
    private String link = null;
    private Date start = null;
    private Date stop = null;

    public VotingRound() {

    }

    public VotingRound(String round, String name, String link, Date start, Date stop) {
        this.round = round;
        this.name = name;
        this.link = link;
        this.start = start;
        this.stop = stop;
    }

    public String getRound() {
        return round;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public Date getStart() {
        return start;
    }

    public Date getStop() {
        return stop;
    }
}
