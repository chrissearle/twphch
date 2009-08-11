package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class FrontPageAction extends ActionSupport {
    @Autowired
    private PhotographyService photographyService;

    private List<DisplayImage> displayImages;

    @Override
    public String execute() throws Exception {
        Set<ImageItem> images = photographyService.getGoldWinners();

        displayImages = new ArrayList<DisplayImage>(images.size());

        for (ImageItem image : images) {
            displayImages.add(new DisplayImage(image));
        }

        Collections.sort(displayImages, new Comparator<DisplayImage>() {

            public int compare(DisplayImage o1, DisplayImage o2) {
                return o2.getChallengeTag().compareTo(o1.getChallengeTag());
            }
        });

        return SUCCESS;
    }

    public List<DisplayImage> getDisplayImages() {
        return displayImages;
    }
}
