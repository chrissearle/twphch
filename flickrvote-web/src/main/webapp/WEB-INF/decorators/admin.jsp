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

<!DOCTYPE html PUBLIC
"-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><decorator:title default="Twitter Photo Challenge"/></title>
    <link href="<s:url value='/styles/reset.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/styles/admin.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" src="<s:url value='/js/jquery-1.3.2.min.js'/>"></script>
    <script type="text/javascript" src="<s:url value='/js/jquery.dataTables.min.js'/>"></script>
    <decorator:head/>
</head>
<body id="page-home">
<div id="container">
    <div id="header">
        <h1>Twitter PhotoChallenge Admin</h1>

        <h2><s:url namespace="/" action="current" id="mainLink"/><s:a href="%{mainLink}">Main Pages</s:a></h2><br/>
        <hr/>
    </div>
    <!-- end header -->

    <div id="left">
        <h3>Admin Menu</h3>

        <p>
            <s:url action="challengeForm" id="newChallengeFormUrl"/>
            <s:a href="%{newChallengeFormUrl}">New Challenge</s:a>
            <br/>
            <s:url action="challenges" id="challengesUrl"/>
            <s:a href="%{challengesUrl}">Challenges</s:a>
            <br/>
            <s:url action="photographerList" id="photographersUrl"/>
            <s:a href="%{photographersUrl}">Photographers</s:a>
        </p>

        <h3>Flickr Menu</h3>

        <p>
            <s:url action="getPhotographerForm" id="getPhotographerFormUrl"/>
            <s:a href="%{getPhotographerFormUrl}">Get photographer by ID</s:a>
            <br/>
            <s:url action="getImageForm" id="getImageFormUrl"/>
            <s:a href="%{getImageFormUrl}">Get image by ID</s:a>
            <br/>
            <s:url action="getAdminFrob" id="frobUrl"/>
            <s:a href="%{frobUrl}">Show admin auth link</s:a>
            <br/>
            <s:url action="frontpage" id="frontpageUrl"/>
            <s:a href="%{frontpageUrl}">Flickr Group HTML</s:a>
        </p>

        <h3>Cron Menu</h3>

        <p>
            <s:url action="cronNewChallenge" id="cronNewChallengeUrl"/>
            <s:a href="%{cronNewChallengeUrl}">Announce current</s:a>
            <br/>
            <s:url action="cronOpenVoting" id="cronOpenVotingUrl"/>
            <s:a href="%{cronOpenVotingUrl}">Open voting</s:a>
            <br/>
            <s:url action="cronResults" id="cronResultsUrl"/>
            <s:a href="%{cronResultsUrl}">Calculate/announce results</s:a>
        </p>

        <h3>External Menu</h3>

        <p>
            <s:url action="tweetForm" id="tweetUrl"/>
            <s:a href="%{tweetUrl}">Tweet</s:a>
            <br/>
            <s:url action="mailForm" id="mailUrl"/>
            <s:a href="%{mailUrl}">Mail</s:a>
        </p>
    </div>
    <!-- end left division -->

    <div id="main">
        <s:actionmessage/>
        <s:actionerror/>
        <decorator:body/>
    </div>

    <div id="footer">
        <hr/>

        <p class="left">| <a href=
                "http://jigsaw.w3.org/css-validator/">CSS</a> | <a href=
                "http://validator.w3.org/check?uri=referer">XHTML 1.1</a>
            |</p>

        <p class="right">Some rights reserved <a href=
                "mailto:support@syndicateme.net">syndicateme.net</a> 2007</p>

        <p>&nbsp;</p>
    </div>
    <!-- end footer -->
</div>
<!-- end container -->

</body>
</html>
