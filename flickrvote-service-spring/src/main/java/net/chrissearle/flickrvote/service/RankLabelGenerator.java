package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankLabelGenerator extends AbstractCategoryItemLabelGenerator implements CategoryItemLabelGenerator {
    private Map<String, ImageInfo> images;
    private String noImageText;

    public RankLabelGenerator(List<ImageInfo> images, String noImageText) {
        super("", NumberFormat.getInstance());

        this.images = new HashMap<String, ImageInfo>();
        for (ImageInfo image : images) {
            this.images.put(image.getChallengeTag(), image);
        }
        this.noImageText = noImageText;
    }

    public String generateLabel(CategoryDataset categoryDataset, int series, int category) {
        String label = noImageText;

        Comparable tag = categoryDataset.getColumnKey(category);

        if (images.containsKey(tag)) {
            label = images.get(tag).getRank().toString() + ".";
        }

        return label;
    }
}
