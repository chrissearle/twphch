<@s.url id="myPicsLink" namespace="/" action="myPics"/>
<@s.a href="%{myPicsLink}"><@s.property value="#session.flickrUser.name"/></@s.a>
<@s.if test="#session.flickrUser.administrator">
 |
<@s.url id="adminLink" namespace="/admin" action="challenges"/>
<@s.a href="%{adminLink}"><@s.text name="link.admin"/></@s.a>
</@s.if>
 |
<@s.url id="logoutLink" namespace="/" action="logout"/>
<@s.a href="%{logoutLink}"><@s.text name="link.logout"/></@s.a>
