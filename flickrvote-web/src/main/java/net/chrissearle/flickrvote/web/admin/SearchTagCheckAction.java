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

        for (String key : searchIssues.keySet()) {
            addActionMessage("Issue with picture ID " + key + ": " + searchIssues.get(key));
        }

        return SUCCESS;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
