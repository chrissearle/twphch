<%--
  ~ Copyright 2010 Chris Searle
  ~
  ~    Licensed under the Apache License, Version 2.0 (the "License");
  ~    you may not use this file except in compliance with the License.
  ~    You may obtain a copy of the License at
  ~
  ~        http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~    Unless required by applicable law or agreed to in writing, software
  ~    distributed under the License is distributed on an "AS IS" BASIS,
  ~    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~    See the License for the specific language governing permissions and
  --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="fv" uri="/flickrvote-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
    <title><decorator:title default="Twitter Photo Challenge"/></title>
    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1"/>

    <!-- **** reset stylesheet **** -->
    <link href="<s:url value='/styles/reset.css'/>" rel="stylesheet" type="text/css" media="all"/>

    <!-- **** layout stylesheet **** -->
    <link href="<s:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>

    <!-- **** colour scheme stylesheet **** -->
    <link href="<s:url value='/styles/orange.css'/>" rel="stylesheet" type="text/css" media="all"/>

    <link rel="alternate" type="application/rss+xml" title="<s:text name="rss.link.fame"/>"
          href="<s:url namespace="/rss" action="hallOfFame"/>"/>
    <link rel="alternate" type="application/rss+xml" title="<s:text name="rss.link.front"/>"
          href="<s:url namespace="/rss" action="current"/>"/>

    <s:action name="loadJQuery" namespace="/common" executeResult="true"/>
    <s:action name="loadBlockControl" namespace="/common" executeResult="true"/>
    <s:action name="loadAnywhere" namespace="/common" executeResult="true"/>

    <s:if test="#session.flickrUser">
        <s:action name="loadHighcharts" namespace="/common" executeResult="true"/>
        <s:action name="photographerChartJs" namespace="/stats" executeResult="true">
            <s:param name="small" value="true"/>
        </s:action>
    </s:if>


    <decorator:head/>

</head>

<body>
<div id="main">
    <div id="links">
        <s:url action="current" namespace="/" id="homepageUrl"/><s:a href="%{homepageUrl}"><s:text
            name="link.homepage"/></s:a> |
        <s:action name="flickrLoginLink" namespace="/common" executeResult="true"/> |
        <a href="http://twitter.com/Twphch"><s:text name="link.twitter.title"/></a> |
        <a href="http://www.flickr.com/groups/twphch/"><s:text name="link.flickr.group.title"/></a> |
        <a href="http://52.twphch.com/"><s:text name="link.year.title"/></a>

    </div>
    <div id="logo"><h1><s:text name="main.title"/></h1>

        <h2>&quot;<s:text name="main.motto"/>&quot;</h2>
    </div>
    <!--
        <div id="menu">
            <ul>
            </ul>
        </div>
    -->
    <div id="content">
        <div id="column1">
            <s:if test="#session.flickrUser">
                <s:if test="showEntryBox">
                    <div class="sidebaritem" id="block_my_entry_div">
                        <h1 id="block_my_entry">#<s:property
                                value="challenge.challengeTag"/> : <s:text name="sidebar.my.entry.title"/></h1>

                        <div id="block_my_entry_content">
                            <s:if test="currentPhotographerImage">
                                <p><s:property value="currentPhotographerImage.imageTitle"/></p>

                                <p>
                                    <img width="150px" src="<s:property value="currentPhotographerImage.imageUrl"/>"
                                         alt="<s:property value="currentPhotographerImage.imageTitle"/>"/>
                                </p>
                            </s:if>
                            <s:else>
                                <s:url namespace="/account" action="check" id="checkLink"/>
                                <p>
                                    <s:a href="%{checkLink}"><s:text name="account.check.link"/></s:a>
                                    <br/>
                                    <s:text name="warning.flickr.slow"/>
                                </p>
                            </s:else>
                        </div>
                    </div>
                </s:if>
            </s:if>

            <s:action name="votingChallengeBlock" namespace="/common" executeResult="true"/>
            <s:if test="challenge != null">
                <s:action name="currentChallengeBlock" namespace="/common" executeResult="true"/>
            </s:if>
            <s:action name="challengeListBlock" namespace="/common" executeResult="true"/>
            <s:if test="#session.flickrUser">
                <s:action name="photographerBlock" namespace="/common" executeResult="true"/>
            </s:if>
            <div class="sidebaritem" id="block_blog_div">
                <h1 id="block_blog"><s:text name="sidebar.blog.title"/></h1>

                <div id="block_blog_content">
                    <div class="sbilinks">
                        <ul>
                            <li><a href="http://blog.twphch.com/"><s:text name="sidebar.blog.link.blog"/></a></li>
                            <li><a href="http://blog.twphch.com/faq/"><s:text name="sidebar.blog.link.faq"/></a></li>
                            <li><a href="http://blog.twphch.com/about/"><s:text name="sidebar.blog.link.about"/></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="sidebaritem" id="block_lang_div">
                <h1 id="block_lang"><s:text name="language.title"/></h1>

                <div id="block_lang_content">
                    <div class="sbilinks">
                        <s:url id="enUrl" action="current" namespace="/"><s:param
                                name="request_locale">en</s:param></s:url>
                        <s:url id="noUrl" action="current" namespace="/"><s:param
                                name="request_locale">no</s:param></s:url>
                        <s:url id="seUrl" action="current" namespace="/"><s:param
                                name="request_locale">se</s:param></s:url>
                        <s:url id="dkUrl" action="current" namespace="/"><s:param
                                name="request_locale">dk</s:param></s:url>

                        <s:url id="enFlagUrl" value='/images/famfamfam_flag_icons/png/gb.png'/>
                        <s:url id="noFlagUrl" value='/images/famfamfam_flag_icons/png/no.png'/>
                        <s:url id="seFlagUrl" value='/images/famfamfam_flag_icons/png/se.png'/>
                        <s:url id="dkFlagUrl" value='/images/famfamfam_flag_icons/png/dk.png'/>

                        <ul>
                            <li><s:a href="%{noUrl}"><img src="${noFlagUrl}" alt="no"/></s:a></li>
                            <li><s:a href="%{enUrl}"><img src="${enFlagUrl}" alt="gb"/></s:a></li>
                            <!--
                              <li><s:a href="%{seUrl}"><img src="${seFlagUrl}" alt="se"/></s:a></li>
                              <li><s:a href="%{dkUrl}"><img src="${dkFlagUrl}" alt="dk"/></s:a></li>
                            -->
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div id="column2">
            <fv:announcement/>
            <s:if test="#session.flickrUser">
                <s:if test="#session.flickrUser.twitterAccount == ''">
                    <ul class="actionMessage">
                        <li><s:url id="registerTwitterUrl" action="edit" namespace="/account"/><s:a
                                href="%{registerTwitterUrl}"><s:text name="twitter.not.registered"/></s:a></li>
                    </ul>
                </s:if>
            </s:if>
            <decorator:body/>
        </div>
    </div>
    <div id="footer">
        <p>
            <s:text name="stats.prefix"/>:
            <s:url id="challengeVsEntriesChart" action="challengeVsEntriesChart" namespace="/stats"/>
            <s:a href="%{challengeVsEntriesChart}"><s:text name="stats.challenge.entries"/></s:a>
            |
            <a href="http://www.dcarter.co.uk"><s:text name="footer.design.by"/>&nbsp;dcarter</a>
            |
            <a href="http://github.com/chrissearle/twphch/tree/master"><s:text name="github.link"/></a>
        </p>
    </div>
</div>
<!-- Woopra Code Start -->
<script type="text/javascript" src="//static.woopra.com/js/woopra.v2.js"></script>
<script type="text/javascript">
    <s:if test="#session.flickrUser">
    woopraTracker.addVisitorProperty("name", "<s:property value="#session.flickrUser.username"/>");
    woopraTracker.addVisitorProperty("Full Name", "<s:property value="#session.flickrUser.photographerName"/>");
    woopraTracker.addVisitorProperty("Twitter", "<s:property value="#session.flickrUser.twitterAccount"/>");
    </s:if>
    woopraTracker.track();
</script>
<!-- Woopra Code End -->
</body>
</html>