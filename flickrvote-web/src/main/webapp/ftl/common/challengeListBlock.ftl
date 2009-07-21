<div class="sidebaritem">
    <h1><@s.text name="sidebar.olderchallenges.title"/></h1>

    <div class="sbilinks">
        <ul>
            <@s.iterator id="challenge" value="challenges">
            <li>
                <@s.url id="showLink" namespace="/" action="show">
                <@s.param name="challengeTag"><@s.property value="#challenge.tag"/></@s.param>
                </@s.url>
                <@s.a href="%{showLink}">#<@s.property value="#challenge.tag"/>
                -
                <@s.property value="#challenge.title"/></@s.a>
            </li>
            </@s.iterator>
        </ul>
    </div>
</div>
