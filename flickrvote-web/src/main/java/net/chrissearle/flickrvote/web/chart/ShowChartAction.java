package net.chrissearle.flickrvote.web.chart;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;

public class ShowChartAction {

    private String tag;

    private JFreeChart chart;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;

    public String execute() throws Exception {
        ChallengeInfo challenge = challengeService.getChallenge(tag);

        List<ImageInfo> images = challengeService.getImagesForChallenge(tag);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ImageInfo image : images) {
            dataset.setValue(image.getFinalVoteCount(), "Score", image.getPhotographerName());
        }

        chart = ChartFactory.createBarChart3D(
                challenge.getTag(),
                "Photographer",
                "Score",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE);
        
        chart.setBackgroundPaint(java.awt.Color.white);

        return ActionSupport.SUCCESS;
    }

    // this method will get called if we specify <param name="value">chart</param>
    public JFreeChart getChart() {
        return chart;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
