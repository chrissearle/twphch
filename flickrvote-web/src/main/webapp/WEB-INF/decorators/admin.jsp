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
<div id="page">
    <div id="menu">
        <ul>
            <li>
                <s:url action="getPhotographerForm" id="getPhotographerFormUrl"/>
                <s:a href="%{getPhotographerFormUrl}">Get Photographer</s:a>
            </li>
            <li>
                <s:url action="getImageForm" id="getImageFormUrl"/>
                <s:a href="%{getImageFormUrl}">Get Image</s:a>
            </li>
            <li>
                <s:url action="challenges" id="challengesUrl"/>
                <s:a href="%{challengesUrl}">Challenges</s:a>
            </li>
            <li>
                <s:url action="newChallengeForm" id="newChallengeFormUrl"/>
                <s:a href="%{newChallengeFormUrl}">New Challenge</s:a>
            </li>
        </ul>
    </div>
    <div id="content">
        <div id="main">
            <h1>Admin</h1>
            <decorator:body/>
        </div>
    </div>
</div>
</body>
</html>
