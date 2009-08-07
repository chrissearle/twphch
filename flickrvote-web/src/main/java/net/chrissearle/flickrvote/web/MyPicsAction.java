package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MyPicsAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;

    @Autowired
    private PhotographyService photographyService;

    private List<ImageInfo> images;

    @Override
    public String execute() throws Exception {
        if (!session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            return "notloggedin";
        }

        Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        images = photographyService.getImagesForPhotographer(photographer.getPhotographerId());

        Collections.sort(images, new Comparator<ImageInfo>() {
            public int compare(ImageInfo o1, ImageInfo o2) {
                return o1.getChallengeTag().compareTo(o2.getChallengeTag());
            }
        });

        return SUCCESS;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public List<ImageInfo> getImages() {
        return images;
    }
}
