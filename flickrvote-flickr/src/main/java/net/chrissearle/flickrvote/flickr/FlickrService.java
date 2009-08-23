/*
 * Copyright 2009 Chris Searle
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

package net.chrissearle.flickrvote.flickr;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FlickrService {
    URL getLoginUrl() throws FlickrServiceException;

    URL getLoginUrl(boolean write) throws FlickrServiceException;

    FlickrPhotographer authenticate(String frob) throws FlickrServiceException;

    List<FlickrImage> searchImagesByTag(String tag, Date earliestDate) throws FlickrServiceException;

    FlickrPhotographer getUserByFlickrId(String id);

    FlickrImage getImageByFlickrId(String id);

    void postForum(String title, String message);

    void postComment(String id, String comment);

    Map<String, String> checkSearch(String tag, Date earliestDate);
}
