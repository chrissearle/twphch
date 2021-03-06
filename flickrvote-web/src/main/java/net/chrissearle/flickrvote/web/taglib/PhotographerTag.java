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

package net.chrissearle.flickrvote.web.taglib;

import net.chrissearle.flickrvote.web.model.Photographer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotographerTag extends TagSupport {
    private static final long serialVersionUID = 945856035489099235L;

    private transient Logger logger = Logger.getLogger(PhotographerTag.class.getName());

    private Photographer photographer;

    public Photographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder text = new StringBuilder();

            if (photographer.getIconUrl() != null) {
                text.append("<img class=\"photogIcon\" src=\"");
                text.append(photographer.getIconUrl());
                text.append("\"/>");
                text.append("&nbsp;");
            }

            text.append(photographer.getPhotographerName());

            String twitterAccount = photographer.getTwitterAccount();

            if (twitterAccount != null && !"".equals(twitterAccount)) {
                text.append(" : @").append(twitterAccount);
            }

            out.print(text.toString());
        } catch (IOException e) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to generate photographer: " + e.getMessage());
            }
        }

        return super.doStartTag();
    }
}
