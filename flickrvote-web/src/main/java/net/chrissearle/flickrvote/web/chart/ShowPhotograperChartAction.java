package net.chrissearle.flickrvote.web.chart;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChartService;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class ShowPhotograperChartAction extends ActionSupport implements SessionAware {

    private String tag;

    private JFreeChart chart;

    @Autowired
    private ChartService chartService;
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        chart = chartService.getChartForPhotographer(photographer.getPhotographerId(),
                getText("chart.rank.axis.title"),
                getText("chart.challenge.axis.title"),
                getText("chart.noimage.text"));

        return SUCCESS;
    }

    // this method will get called if we specify <param name="value">chart</param>
    public JFreeChart getChart() {
        return chart;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }
}