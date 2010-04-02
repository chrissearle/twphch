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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnnouncementTag extends TagSupport {
    Logger logger = Logger.getLogger(AnnouncementTag.class.getName());
    
    @Override
    public int doStartTag() throws JspException {
        File announcementFile = new File("/etc/flickrvote/announcement.html");

        if (announcementFile.exists() && announcementFile.isFile()) {
            JspWriter out = pageContext.getOut();

            try {
                StringBuilder text = new StringBuilder();

                text.append("<div class='announce'>");
                text.append(getContents(announcementFile));
                text.append("</div>");

                out.print(text.toString());
            } catch (IOException e) {
                if (logger.isLoggable(Level.WARNING)) {
                    logger.warning("Unable to generate announcement: " + e.getMessage());
                }
            }
        }

        return super.doStartTag();
    }

    protected String getContents(File aFile) {
        StringBuilder contents = new StringBuilder();

        try {
            BufferedReader input = new BufferedReader(new FileReader(aFile));

            try {
                String line = null;

                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            }
            finally {
                input.close();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }

}
