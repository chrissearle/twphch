package net.chrissearle.flickrvote.web.chart;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChartService;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowChartAction extends ActionSupport {

    private String tag;

    private JFreeChart chart;

    @Autowired
    private ChartService chartService;

    @Override
    public String execute() throws Exception {
        chart = chartService.getChartForChallenge(tag, getText("chart.score.axis.title"), getText("chart.photographer.axis.title"));

        return SUCCESS;
    }

    // this method will get called if we specify <param name="value">chart</param>
    public JFreeChart getChart() {
        return chart;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
