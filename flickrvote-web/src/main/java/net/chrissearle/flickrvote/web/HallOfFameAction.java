/*
 * Copyright 2009 Chris Searle
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

package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.WinnerService;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.DisplayImage;
import net.chrissearle.flickrvote.web.model.ListControl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class HallOfFameAction extends ActionSupport implements Preparable {
    @Autowired
    private WinnerService winnerService;

    private List<DisplayImage> displayImages;

    private ListControl listControl = new ListControl(true, false, true, true);

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
        Set<ImageItem> images = winnerService.getGoldWinners();

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

    public ListControl getListControl() {
        return listControl;
    }
}
