package net.chrissearle.flickrvote.web.model;

import java.io.Serializable;
import java.util.Date;

public class ChallengeAdmin implements Serializable {
    private String tag;
    private String title;

    private Date startDate;

    public Date getStartDate() {
        return new Date(startDate.getTime());
    }

    public void setStartDate(Date startDate) {
        this.startDate = new Date(startDate.getTime());
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
