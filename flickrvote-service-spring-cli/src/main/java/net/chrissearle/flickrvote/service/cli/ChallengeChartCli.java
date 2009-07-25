package net.chrissearle.flickrvote.service.cli;

import net.chrissearle.flickrvote.service.ChartService;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ChallengeChartCli extends AbstractCliService {
    public static void main(String[] args) throws IOException {
        ChallengeChartCli app = new ChallengeChartCli();
        app.initialize();

        app.generateChart(args[0]);
    }

    private void generateChart(String tag) throws IOException {
        ChartService service = (ChartService) context.getBean("chartService");

        JFreeChart chart = service.getChartForChallenge(tag, "Votes", "Photographer");

        ChartUtilities.saveChartAsPNG(new File(tag + ".png"), chart, 500, 1000);
    }
}
