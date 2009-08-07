package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class HallOfFameAction extends ActionSupport implements Preparable {
    @Autowired
    private PhotographyService photographyService;

    private List<DisplayImage> displayImages;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String executeRss() {
        return "rss";
    }

    public List<DisplayImage> getDisplayImages() {
        return displayImages;
    }

    public void prepare() throws Exception {
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
    }
}
