package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class JFreeChartChartService implements ChartService {
    public static final Color background = new Color(65, 65, 65);
    public static final Color foreground = new Color(213, 210, 214);
    public static final Color orange = new Color(255, 156, 39);


    private ChallengeService challengeService;
    private PhotographyService photographyService;

    @Autowired
    public JFreeChartChartService(ChallengeService challengeService, PhotographyService photographyService) {
        this.challengeService = challengeService;
        this.photographyService = photographyService;
    }

    public JFreeChart getChartForChallenge(String tag, String scoreAxisTitle, String photographerAxisTitle) {
        ChallengeInfo challenge = challengeService.getChallenge(tag);

        List<ImageInfo> images = challengeService.getImagesForChallenge(tag);

        Collections.sort(images, new Comparator<ImageInfo>() {

            public int compare(ImageInfo o1, ImageInfo o2) {
                return o2.getFinalVoteCount().compareTo(o1.getFinalVoteCount());
            }
        });

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ImageInfo image : images) {
            dataset.setValue(image.getFinalVoteCount(), scoreAxisTitle, image.getPhotographerName());
        }

        return generateChart(challenge.getTag(), challenge.getTitle(), dataset, scoreAxisTitle, photographerAxisTitle);
    }

    private JFreeChart generateChart(String tag, String subtitle, CategoryDataset dataset, String scoreAxisTitle, String photographerAxisTitle) {
        JFreeChart chart = ChartFactory.createBarChart(
                tag,
                photographerAxisTitle,
                scoreAxisTitle,
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                true,
                false
        );

        List<Title> subtitles = new ArrayList<Title>(1);
        TextTitle title = new TextTitle(subtitle);
        title.setPaint(foreground);
        subtitles.add(title);

        chart.setSubtitles(subtitles);

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(Math.PI / 6.0));


        GradientPaint gp = new GradientPaint(
                0.0f, 0.0f, background,
                0.0f, 0.0f, orange
        );

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, gp);
        renderer.setDrawBarOutline(false);
        renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL));

        domainAxis.setLabelPaint(foreground);
        domainAxis.setTickLabelPaint(foreground);
        plot.getRangeAxis().setLabelPaint(foreground);
        plot.getRangeAxis().setTickLabelPaint(foreground);

        chart.setBackgroundPaint(background);
        chart.getTitle().setPaint(foreground);
        plot.setBackgroundPaint(background);
        plot.setDomainGridlinePaint(foreground);
        plot.setRangeGridlinePaint(foreground);


        return chart;
    }

    public JFreeChart getVotingChart(String scoreAxisTitle, String photographerAxisTitle) {
        ChallengeInfo challenge = challengeService.getVotingChallenge();

        List<ImageInfo> images = challengeService.getImagesForChallenge(challenge.getTag());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ImageInfo image : images) {
            dataset.setValue(image.getVoteCount(), scoreAxisTitle, image.getPhotographerName());
        }

        return generateChart(challenge.getTag(), challenge.getTitle(), dataset, scoreAxisTitle, photographerAxisTitle);
    }

    public JFreeChart getChartForPhotographer(String id, String rankAxisTitle, String challengeAxisTitle) {
        List<ImageInfo> images = photographyService.getImagesForPhotographer(id);

        List<ChallengeInfo> challenges = challengeService.getChallenges();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, ImageInfo> imageMap = new HashMap<String, ImageInfo>();
        String photographerName = null;

        for (ImageInfo image : images) {
            imageMap.put(image.getChallengeTag(), image);
            if (photographerName == null) {
                photographerName = image.getPhotographerName();
            }
        }

        for (ChallengeInfo challenge : challenges) {
            if (imageMap.containsKey(challenge.getTag())) {
                dataset.setValue(imageMap.get(challenge.getTag()).getFinalVoteCount(), rankAxisTitle, challenge.getTag());
            } else {
                dataset.setValue(-1, rankAxisTitle, challenge.getTag());
            }
        }

        return generateChart(photographerName == null ? "" : photographerName, "", dataset, rankAxisTitle, challengeAxisTitle);
    }
}
