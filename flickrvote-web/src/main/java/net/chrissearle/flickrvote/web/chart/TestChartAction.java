package net.chrissearle.flickrvote.web.chart;

import com.opensymphony.xwork2.ActionSupport;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Random;

public class TestChartAction {

    private JFreeChart chart;

    public String execute() throws Exception {
        // chart creation logic...
        XYSeries dataSeries = new XYSeries(1); // pass a key for this serie
        Random rand = new Random();

        for (int i = 0; i <= 100; i++) {
            dataSeries.add(i, rand.nextInt());
        }
        XYSeriesCollection xyDataset = new XYSeriesCollection(dataSeries);

        ValueAxis xAxis = new NumberAxis("Raw Marks");
        ValueAxis yAxis = new NumberAxis("Moderated Marks");

        // set my chart variable
        chart =
                new JFreeChart("Moderation Function", JFreeChart.DEFAULT_TITLE_FONT,
                        new XYPlot(xyDataset, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.LINES)),
                        false);
        chart.setBackgroundPaint(java.awt.Color.white);

        return ActionSupport.SUCCESS;
    }

    // this method will get called if we specify <param name="value">chart</param>
    public JFreeChart getChart() {
        return chart;
    }
}
