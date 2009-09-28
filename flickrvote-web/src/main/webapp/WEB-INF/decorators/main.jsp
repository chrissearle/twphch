<%--
  ~ Copyright 2009 Chris Searle
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
  ~    limitations under the License.
  --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>

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

    <script type="text/javascript" src="<s:url value='/js/jquery-1.3.2.min.js'/>"></script>

    <link rel="alternate" type="application/rss+xml" title="<s:text name="rss.link.fame"/>"
          href="<s:url namespace="/rss" action="hallOfFame"/>"/>
    <link rel="alternate" type="application/rss+xml" title="<s:text name="rss.link.front"/>"
          href="<s:url namespace="/rss" action="current"/>"/>

    <decorator:head/>

</head>

<body>
<div id="main">
    <div id="links">
        <s:url action="current" namespace="/" id="homepageUrl"/><s:a href="%{homepageUrl}"><s:text
            name="link.homepage"/></s:a> |
        <s:action name="flickrLoginLink" namespace="/common" executeResult="true"/> |
        <a href="http://twitter.com/Twphch"><s:text name="link.twitter.title"/></a> |
        <a href="http://www.flickr.com/groups/twphch/"><s:text name="link.flickr.group.title"/></a>

    </div>
    <div id="logo"><h1><s:text name="main.title"/></h1>

        <h2>&quot;<s:text name="main.motto"/>&quot;</h2></div>

    <div id="content">
        <div id="column1">
            <s:action name="votingChallengeBlock" namespace="/common" executeResult="true"/>
            <s:if test="challenge != null">
                <s:action name="currentChallengeBlock" namespace="/common" executeResult="true"/>
            </s:if>
            <s:action name="challengeListBlock" namespace="/common" executeResult="true"/>
            <s:if test="#session.flickrUser">
                <s:action name="photographerBlock" namespace="/common" executeResult="true"/>
            </s:if>
            <div class="sidebaritem">
                <h1><s:text name="sidebar.grouprules.title"/></h1>

                <div class="sbilinks">
                    <!-- **** INSERT ADDITIONAL LINKS HERE **** -->
                    <ul>
                        <s:url namespace="/" action="rules" id="rulesLink"/>
                        <li><s:a href="%{rulesLink}"><s:text name="rules.title"/></s:a></li>
                        <s:url namespace="/" action="instructions" id="instructionsLink"/>
                        <li><s:a href="%{instructionsLink}"><s:text name="instructions.title"/></s:a></li>
                        <s:url namespace="/" action="faq" id="faqLink"/>
                        <li><s:a href="%{faqLink}"><s:text name="faq.title"/></s:a></li>
                    </ul>
                </div>
            </div>
            <div class="sidebaritem">
                <h1><s:text name="language.title"/></h1>

                <div class="sbilinks">
                    <s:url id="enUrl" action="current" namespace="/"><s:param name="request_locale">en</s:param></s:url>
                    <s:url id="noUrl" action="current" namespace="/"><s:param name="request_locale">no</s:param></s:url>
                    <s:url id="seUrl" action="current" namespace="/"><s:param name="request_locale">se</s:param></s:url>
                    <s:url id="dkUrl" action="current" namespace="/"><s:param name="request_locale">dk</s:param></s:url>

                    <s:url id="enFlagUrl" value='/images/famfamfam_flag_icons/png/gb.png'/>
                    <s:url id="noFlagUrl" value='/images/famfamfam_flag_icons/png/no.png'/>
                    <s:url id="seFlagUrl" value='/images/famfamfam_flag_icons/png/se.png'/>
                    <s:url id="dkFlagUrl" value='/images/famfamfam_flag_icons/png/dk.png'/>

                    <ul>
                        <li><s:a href="%{noUrl}"><img src="${noFlagUrl}" alt="no"/></s:a></li>
                        <li><s:a href="%{enUrl}"><img src="${enFlagUrl}" alt="gb"/></s:a></li>
                        <li><s:a href="%{seUrl}"><img src="${seFlagUrl}" alt="se"/></s:a></li>
                        <li><s:a href="%{dkUrl}"><img src="${dkFlagUrl}" alt="dk"/></s:a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div id="column2">
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
        <a href="http://www.dcarter.co.uk"><s:text name="footer.design.by"/>&nbsp;dcarter</a>
        |
        <a href="http://github.com/chrissearle/twphch/tree/master"><s:text name="github.link"/></a>
    </div>
</div>
</body>
</html>