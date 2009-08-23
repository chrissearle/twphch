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
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.PhotographerItem;
import net.chrissearle.flickrvote.web.model.DisplayPhotographer;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PhotographerDataAction extends ActionSupport implements Preparable {
    @Autowired
    private PhotographyService photographyService;

    private List<Photographer> photographers;

    private String id;

    public String browse() throws Exception {
        return "browse";
    }

    public void prepare() throws Exception {
        List<PhotographerItem> photographerList = photographyService.getPhotographers();

        photographers = new ArrayList<Photographer>(photographerList.size());

        for (PhotographerItem photographer : photographerList) {
            photographers.add(new DisplayPhotographer(photographer));
        }

        Collections.sort(photographers, new Comparator<Photographer>() {
            public int compare(Photographer o1, Photographer o2) {
                return o1.getPhotographerName().compareTo(o2.getPhotographerName());
            }
        });
    }

    public List<Photographer> getPhotographers() {
        return photographers;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String execute() throws Exception {
        PhotographerItem photographer = photographyService.findById(id);

        if (photographer != null) {
            photographyService.setAdministrator(photographer.getId(), !photographer.isAdministratorFlag());

            addActionMessage("Admin flag toggled for photographer " + photographer.getName());
        } else {
            addActionError("Photographer not found for id: " + id);
        }

        return SUCCESS;
    }
}
