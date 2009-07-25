package net.chrissearle.flickrvote.service.cli;

import net.chrissearle.flickrvote.service.ChartService;
import net.chrissearle.flickrvote.service.PhotographyService;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PhotographerChartCli extends AbstractCliService {
    public static void main(String[] args) throws IOException {
        PhotographerChartCli app = new PhotographerChartCli();
        app.initialize();

        app.generateChart(args[0]);
    }

    private void generateChart(String id) throws IOException {
        ChartService service = (ChartService) context.getBean("chartService");

        JFreeChart chart = service.getChartForPhotographer(id, "Votes", "Photographer");

        ChartUtilities.saveChartAsPNG(new File(id + ".png"), chart, 500, 1000);
    }
}