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
import net.chrissearle.mail.SimpleMailService;
import org.springframework.beans.factory.annotation.Autowired;

public class SendMailAction extends ActionSupport {
    private String title;
    private String text;

    @Autowired
    private SimpleMailService mailService;

    @Override
    public String execute() throws Exception {
        mailService.sendPost(title, text);

        addActionMessage("Mail sent");

        return SUCCESS;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }
}
