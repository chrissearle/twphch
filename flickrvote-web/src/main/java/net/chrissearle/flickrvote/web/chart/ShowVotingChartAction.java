package net.chrissearle.flickrvote.web.chart;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChartService;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowVotingChartAction extends ActionSupport {
    private JFreeChart chart;

    @Autowired
    private ChartService chartService;

    @Override
    public String execute() throws Exception {
        chart = chartService.getVotingChart();

        return SUCCESS;
    }

    // this method will get called if we specify <param name="value">chart</param>
    public JFreeChart getChart() {
        return chart;
    }

}
