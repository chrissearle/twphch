<@s.url id="myAccountLink" namespace="/account" action="account"/>
<@s.a href="%{myAccountLink}"><@s.property value="#session.flickrUser.photographerName"/></@s.a>
|
<@s.a href="%{myAccountLink}"><@s.text name="link.account"/></@s.a>
<@s.if test="#session.flickrUser.admin">
|
<@s.url id="adminLink" namespace="/admin" action="challenges"/>
<@s.a href="%{adminLink}"><@s.text name="link.admin"/></@s.a>
</@s.if>
|
<@s.url id="logoutLink" namespace="/" action="logout"/>
<@s.a href="%{logoutLink}"><@s.text name="link.logout"/></@s.a>
