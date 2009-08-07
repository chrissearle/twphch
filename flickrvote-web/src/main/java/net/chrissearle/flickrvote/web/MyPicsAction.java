package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class MyPicsAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;

    @Autowired
    private PhotographyService photographyService;

    private List<DisplayImage> images;

    @Override
    public String execute() throws Exception {
        if (!session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            return "notloggedin";
        }

        Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        images = new ArrayList<DisplayImage>();

        for (ImageItem image : photographyService.getImagesForPhotographer(photographer.getPhotographerId())) {
            images.add(new DisplayImage(image));
        }

        Collections.sort(images, new Comparator<DisplayImage>() {
            public int compare(DisplayImage o1, DisplayImage o2) {
                return o1.getChallengeTag().compareTo(o2.getChallengeTag());
            }
        });

        return SUCCESS;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public List<DisplayImage> getDisplayImages() {
        return images;
    }
}
