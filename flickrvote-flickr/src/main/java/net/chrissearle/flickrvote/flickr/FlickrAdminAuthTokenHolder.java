package net.chrissearle.flickrvote.flickr;

public class FlickrAdminAuthTokenHolder {
    private String adminAuthToken;
    private String adminAuthActive;

    public String getAdminAuthToken() {
        return adminAuthToken;
    }

    public void setAdminAuthToken(String adminAuthToken) {
        this.adminAuthToken = adminAuthToken;
    }

    public void setAdminAuthActive(String adminAuthActive) {
        this.adminAuthActive = adminAuthActive;
    }

    public String getAdminAuthActive() {
        return adminAuthActive;
    }

    public boolean isActiveFlag() {
        return "TRUE".equals(adminAuthActive);
    }
}
