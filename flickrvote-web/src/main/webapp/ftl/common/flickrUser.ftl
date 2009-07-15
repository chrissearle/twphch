<@s.property value="#session.flickrUser.name"/>
<@s.if test="#session.flickrUser.administrator">
|
<@s.url id="adminLink" namespace="/admin" action="challenges"/>
<@s.a href="%{adminLink}">Admin</@s.a>
</@s.if>
|
<@s.url id="logoutLink" namespace="/" action="logout"/>
<@s.a href="%{logoutLink}">Logout</@s.a>
