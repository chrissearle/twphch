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

    <decorator:head/>

</head>

<body>
<div id="main">
    <div id="links">
        <!-- **** INSERT LINKS HERE **** -->
        <s:a href="%{#session.flickrLoginUrl}">Flickr Login</s:a> |
        <a href="http://twitter.com/Twphch">Twitter</a> |
        <a href="http://www.flickr.com/groups/twphch/">Flickr Group</a>

    </div>
    <div id="logo"><h1>Twitter PhotoChallenge</h1>

        <h2>&quot;Follow me on twitter - http://twitter.com/Twphch&quot;</h2></div>

    <div id="menu">
        <ul>
            <li><a id="selected" href="#">Images</a></li>
            <li><a href="#">Results</a></li>
        </ul>
    </div>
    <div id="content">
        <div id="column1">
            <div class="sidebaritem">
                <h1>Current Challenge</h1>

                <p><s:url id="currentLink" action="current"/><s:a href="%{currentLink}">#TwPhCh00X</s:a></p>

                <h2>Dates</h2>

                <p>This is where the current challenge dates go.</p>

                <h2>Challenge topic</h2>

                <p>Description of the challenge - the challenge itself</p>
            </div>
            <div class="sidebaritem">
                <h1>Older Challenges</h1>

                <div class="sbilinks">
                    <!-- **** INSERT ADDITIONAL LINKS HERE **** -->
                    <ul>
                        <s:iterator id="challenge" value="challengeList">
                            <li>
                                <s:url id="showLink" action="show">
                                    <s:param name="challengeId"><s:property value="#challenge.id"/></s:param>
                                </s:url>
                                <s:a href="%{showLink}"><s:property value="#challenge.id"/> - <s:property
                                        value="#challenge.name"/></s:a>
                            </li>
                        </s:iterator>
                    </ul>
                </div>
            </div>
            <div class="sidebaritem">
                <h1>Group Rules</h1>

                <div class="sbilinks">
                    <!-- **** INSERT ADDITIONAL LINKS HERE **** -->
                    <ul>
                        <li><a href="#">Link to rules</a></li>
                        <li><a href="#">Link to how to enter a challenge</a></li>
                    </ul>
                </div>
            </div>
            <div class="sidebaritem">
                <h1>other information</h1>
                <!-- **** INSERT OTHER INFORMATION HERE **** -->
                <p>
                    This space can be used for additional information such as a contact phone number, address
                    or maybe even a graphic.
                </p>
            </div>
        </div>
        <div id="column2">
            <decorator:body/>
        </div>
    </div>
    <div id="footer"><a href="http://www.dcarter.co.uk">design by dcarter</a></div>
</div>
</body>
</html>