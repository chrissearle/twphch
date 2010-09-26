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

package net.chrissearle.flickrvote.web.model;

public class ImageStatistic {
    private final String title;
    private final Float statisticValue;
    private final String tag;

    public ImageStatistic(String tag, String title, Float statisticValue) {
        this.tag = tag;
        this.title = title;
        this.statisticValue = statisticValue;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public Float getStatisticValue() {
        return statisticValue;
    }
}
