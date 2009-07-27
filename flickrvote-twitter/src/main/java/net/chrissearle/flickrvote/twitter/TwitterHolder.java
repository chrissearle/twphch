package net.chrissearle.flickrvote.twitter;

public class TwitterHolder {
    private String active;

    public void setActive(String active) {
        this.active = active;
    }

    public String getActive() {
        return active;
    }

    public boolean isActiveFlag() {
        return "TRUE".equals(active);
    }
}
