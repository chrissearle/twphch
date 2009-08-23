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
import net.chrissearle.flickrvote.service.PhotographyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class SearchTagCheckAction extends ActionSupport {
    @Autowired
    private PhotographyService photographyService;

    private String tag;

    @Override
    public String execute() throws Exception {
        Map<String, String> searchIssues = photographyService.checkSearch(tag);

        for (final Map.Entry<String, String> entry : searchIssues.entrySet()) {
            addActionMessage("Issue with picture ID " + entry.getKey() + ": " + entry.getValue());
        }

        return SUCCESS;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
