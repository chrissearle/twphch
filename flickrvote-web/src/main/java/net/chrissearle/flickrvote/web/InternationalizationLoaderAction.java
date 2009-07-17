package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;

/**
 * It appears that results that don't go via an explicit action class don't get the same properties lookup for
 * internationalization. So this class exists just to force that. It may be used by anything that requires i18n
 * and will always just return success.
 */
public class InternationalizationLoaderAction extends ActionSupport {
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
}
