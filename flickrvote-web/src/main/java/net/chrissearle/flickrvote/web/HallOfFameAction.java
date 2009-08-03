package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HallOfFameAction extends ActionSupport {
    @Autowired
    private PhotographyService photographyService;
    private List<ImageInfo> images;

    @Override
    public String execute() throws Exception {
        images = photographyService.getGoldWinners();

        Collections.sort(images, new Comparator<ImageInfo>() {
            public int compare(ImageInfo o1, ImageInfo o2) {
                return o2.getChallengeTag().compareTo(o1.getChallengeTag());
            }
        });

        return SUCCESS;
    }

    public List<ImageInfo> getImages() {
        return images;
    }
}
