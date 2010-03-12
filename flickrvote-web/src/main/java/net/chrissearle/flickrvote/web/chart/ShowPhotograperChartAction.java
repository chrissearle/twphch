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

package net.chrissearle.flickrvote.web.chart;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChartService;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class ShowPhotograperChartAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = -2037780221800573153L;

    private JFreeChart chart;

    @Autowired
    private ChartService chartService;
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        chart = chartService.getChartForPhotographer(photographer.getPhotographerId(),
                getText("chart.rank.axis.title"),
                getText("chart.challenge.axis.title"),
                getText("chart.noimage.text"));

        return SUCCESS;
    }

    // this method will get called if we specify <param name="value">chart</param>
    public JFreeChart getChart() {
        return chart;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }
}