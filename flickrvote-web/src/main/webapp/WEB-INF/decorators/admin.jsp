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
    <decorator:head/>
</head>
<body id="page-home">
<div id="container">
    <div id="header">
        <h1>Twitter PhotoChallenge Admin</h1>

        <h2><s:url namespace="/" value="current" id="mainLink"/><s:a href="%{mainLink}">Main Pages</s:a></h2><br/>
        <hr/>
    </div>
    <!-- end header -->

    <div id="left">
        <h3>Admin Menu</h3>

        <p>
            <s:url action="getPhotographerForm" id="getPhotographerFormUrl"/>
            <s:a href="%{getPhotographerFormUrl}">Get Photographer</s:a>
            <br/>
            <s:url action="getImageForm" id="getImageFormUrl"/>
            <s:a href="%{getImageFormUrl}">Get Image</s:a>
            <br/>
            <s:url action="challenges" id="challengesUrl"/>
            <s:a href="%{challengesUrl}">Challenges</s:a>
            <br/>
            <s:url action="newChallengeForm" id="newChallengeFormUrl"/>
            <s:a href="%{newChallengeFormUrl}">New Challenge</s:a>
            <br/>
            <s:url action="cronOpenVoting" id="cronOpenVotingUrl"/>
            <s:a href="%{cronOpenVotingUrl}">Cron: Open Voting</s:a>
            <br/>
            <s:url action="cronNewChallenge" id="cronNewChallengeUrl"/>
            <s:a href="%{cronNewChallengeUrl}">Cron: New Challenge</s:a>
            <br/>
            <s:url action="cronResults" id="cronResultsUrl"/>
            <s:a href="%{cronResultsUrl}">Cron: Results</s:a>
            <br/>
            <s:url action="frob" id="frobUrl"/>
            <s:a href="%{frobUrl}">Show admin frob URL</s:a>
        </p>
    </div>
    <!-- end left division -->

    <div id="main">
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
