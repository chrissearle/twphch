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

package net.chrissearle.flickrvote.web.account;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ImageItem;
import org.springframework.beans.factory.annotation.Autowired;

public class RefreshFromFlickrAction extends ActionSupport {
    @Autowired
    private PhotographyService photographyService;

    private String id;

    @Override
    public String execute() throws Exception {

        ImageItem image = photographyService.findImageById(id);

        if (image != null) {
            image = photographyService.retrieveAndStoreImage(image.getId(), image.getChallenge().getTag());

            String[] args = new String[1];
            args[0] = image.getTitle();
            addActionMessage(getText("refresh.message.text", args));
        }

        return SUCCESS;
    }

    public void setId(String id) {
        this.id = id;
    }
}
