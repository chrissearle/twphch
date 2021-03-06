/*
 * Copyright 2010 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import net.chrissearle.flickrvote.web.model.DisplayPhotographer;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowPhotographerImagesAction extends ActionSupport {
    private String id;

    private List<DisplayImage> images;

    private Photographer photographer;

    @Autowired
    private PhotographyService photographyService;

    @Override
    public String execute() throws Exception {
        photographer = new DisplayPhotographer(photographyService.findById(id));


        images = new ArrayList<DisplayImage>();

        for (ImageItem image : photographyService.getImagesForPhotographer(id)) {
            images.add(new DisplayImage(image));
        }

        Collections.sort(images, new Comparator<DisplayImage>() {
            public int compare(DisplayImage o1, DisplayImage o2) {
                return o2.getChallengeTag().compareTo(o1.getChallengeTag());
            }
        });

        return SUCCESS;
    }

    public void setPhotographerId(String id) {
        this.id = id;
    }

    public List<DisplayImage> getDisplayImages() {
        return images;
    }

    public Photographer getPhotographer() {
        return photographer;
    }
}
