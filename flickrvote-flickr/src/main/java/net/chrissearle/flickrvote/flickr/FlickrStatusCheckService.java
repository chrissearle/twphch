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

package net.chrissearle.flickrvote.flickr;

import net.chrissearle.flickrvote.flickr.model.FlickrImageStatus;

import java.util.Date;
import java.util.Set;

public interface FlickrStatusCheckService {
    Set<FlickrImageStatus> checkSearch(String tag, Date earliestDate);

    FlickrImageStatus checkSearch(String tag, Date earliestDate, String imageId);
}