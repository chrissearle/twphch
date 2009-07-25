package net.chrissearle.flickrvote.service.cli;

import net.chrissearle.flickrvote.service.ChartService;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class VotingChartCli extends AbstractCliService {
    public static void main(String[] args) throws IOException {
        VotingChartCli app = new VotingChartCli();
        app.initialize();

        app.generateChart();
    }

    private void generateChart() throws IOException {
        ChartService service = (ChartService) context.getBean("chartService");

        JFreeChart chart = service.getVotingChart("Votes", "Photographer");

        ChartUtilities.saveChartAsPNG(new File("voting.png"), chart, 500, 1000);
    }
}