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

package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.PhotographerItem;
import org.springframework.beans.factory.annotation.Autowired;

public class PhotographerAction extends ActionSupport {

    @Autowired
    private PhotographyService service;

    private String id;

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    public String execute() {
        try {
            PhotographerItem photographer = service.retrieveAndStorePhotographer(id);

            addActionMessage(photographer.getName() + " was retrieved/updated.");
        } catch (FlickrServiceException e) {
            addActionError("Error retrieving photographer: " + e.getMessage());
        }

        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
