/*
 * Copyright 2010 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.flickr.impl;

import net.chrissearle.flickrvote.flickr.*;
import net.chrissearle.flickrvote.flickr.model.FlickrImage;
import net.chrissearle.flickrvote.flickr.model.FlickrImageStatus;
import net.chrissearle.flickrvote.flickr.model.FlickrImages;
import net.chrissearle.flickrvote.flickr.model.FlickrPhotographer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlickrJStatusCheckService implements FlickrStatusCheckService {
    ImageTagSearchDAO imageTagSearchDAO;
    ImageDAO imageDAO;
    UserDAO userDAO;

    @Autowired
    public FlickrJStatusCheckService(ImageTagSearchDAO imageTagSearchDAO, UserDAO userDAO, ImageDAO imageDAO) {
        this.imageTagSearchDAO = imageTagSearchDAO;
        this.userDAO = userDAO;
        this.imageDAO = imageDAO;
    }

    public Set<FlickrImageStatus> checkSearch(String tag, Date earliestDate) {
        if (earliestDate == null) {
            throw new FlickrServiceException("Earliest date cannot be null");
        }

        Set<FlickrImageStatus> issues = new HashSet<FlickrImageStatus>();

        Map<String, FlickrImageStatus> seenPhotographers = new HashMap<String, FlickrImageStatus>();

        FlickrImages images = imageTagSearchDAO.searchTag(tag);

        for (FlickrImage image : images.getImages()) {
            final String photographerId = image.getPhotographer().getFlickrId();

            image.setPhotographer(userDAO.getUser(photographerId));

            if (checkImageForDateIssue(earliestDate, image, issues)) {
                continue;
            }

            createImageMultipleIssue(seenPhotographers, image, photographerId);
        }

        for (FlickrImageStatus status : seenPhotographers.values()) {
            if (status.getImageCount() > 1) {
                issues.add(status);
            }
        }


        return issues;
    }

    @Override
    public FlickrImageStatus checkSearch(String tag, Date earliestDate, String imageId) {
        Set<FlickrImageStatus> issues = new HashSet<FlickrImageStatus>();

        if (checkImageForDateIssue(earliestDate, imageDAO.getImage(imageId), issues)) {
            return issues.iterator().next();
        } else {
            return null;
        }
    }


    private void createImageMultipleIssue(Map<String, FlickrImageStatus> seenPhotographers, FlickrImage image, String photographerId) {
        if (haveSeenPhotographer(seenPhotographers, photographerId)) {
            FlickrImageStatus status = new FlickrImageStatus(FlickrImageStatus.ImageStatus.MULTIPLE_IMAGES,
                    image.getPhotographer(), image);

            seenPhotographers.put(photographerId, status);
        } else {
            seenPhotographers.get(photographerId).addImage(image);
        }
    }

    private boolean haveSeenPhotographer(Map<String, FlickrImageStatus> seenPhotographers, String photographerId) {
        return !seenPhotographers.containsKey(photographerId);
    }

    private boolean checkImageForDateIssue(Date earliestDate, FlickrImage image, Set<FlickrImageStatus> issues) {
        if (checkImageOnOrAfterDate(earliestDate, image)) {
            FlickrImageStatus status = new FlickrImageStatus(FlickrImageStatus.ImageStatus.TAKEN_DATE,
                    image.getPhotographer(), image);

            issues.add(status);

            return true;
        }

        return false;
    }

    private boolean checkImageOnOrAfterDate(Date earliestDate, FlickrImage image) {
        return image.getTakenDate() != null && image.getTakenDate().getTime() < earliestDate.getTime();
    }
}
