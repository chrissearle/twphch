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

import net.chrissearle.flickrvote.web.model.Image;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageTag extends TagSupport {
    private static final long serialVersionUID = -3442973149583766220L;

    private transient Logger logger = Logger.getLogger(ImageTag.class.getName());

    private Image image;
    private Boolean showBadge = true;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Boolean isShowBadge() {
        return showBadge;
    }

    public void setShowBadge(Boolean showBadge) {
        this.showBadge = showBadge;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder text = new StringBuilder();

            if (showBadge) {
                String badge = null;
                if (image.getRank() == 1) {
                    badge = "gold";
                }
                if (image.getRank() == 2) {
                    badge = "silver";
                }
                if (image.getRank() == 3) {
                    badge = "bronze";
                }

                if (badge != null) {
                    text.append("<img src=\"/twitterphotochallenge/images/");
                    text.append(badge);
                    text.append(".jpg\" alt=\"");
                    text.append(badge);
                    text.append("\"/>");
                    text.append("<br/>");
                    text.append("<br/>");
                }
            }

            text.append("<a href=\"");
            text.append(image.getPageUrl());
            text.append("\">");

            text.append("<img src=\"");
            text.append(image.getImageUrl());
            text.append("\" alt=\"");
            text.append(image.getImageTitle());
            text.append("\"/>");

            text.append("</a>");

            out.print(text.toString());
        } catch (IOException e) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Unable to generate image: " + e.getMessage());
            }
        }

        return super.doStartTag();
    }
}