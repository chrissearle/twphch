package net.chrissearle.twphch.flickrvote.client;

import java.util.Date;
import java.io.Serializable;

public class VotingRound implements Serializable {
    private long id;
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

    // We need to expose ID to allow us to call delete back thru to the data level.
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
