package net.chrissearle.flickrvote.web.model;

import com.aetrion.flickr.photos.Photo;

import java.util.List;
import java.util.ArrayList;

import net.chrissearle.flickrvote.model.Challenge;

public class ImageChallenge {
    private final Long id;

    private final String tag;

    private final String name;

    private final List<Image> photos;

    public ImageChallenge(Challenge challenge, List<Photo> images) {
        this.id = challenge.getId();
        this.tag = challenge.getTag();
        this.name = challenge.getName();

        List<Image> pics = new ArrayList<Image>(images.size());

        for (Photo image : images) {
            pics.add(new Image(image));
        }

        photos = pics;
    }

    public Long getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public List<Image> getPhotos() {
        return photos;
    }

    class Image {
        private final String url;
        private final String title;
        private final String owner;

        public Image(Photo pic) {
            this.url = pic.getMediumUrl();
            this.title = pic.getTitle();
            this.owner = pic.getOwner().getRealName();
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }

        public String getOwner() {
            return owner;
        }
    }
}
