<div class="sidebaritem">
    <h1><@s.text name="sidebar.olderchallenges.title"/></h1>

    <div class="sbilinks">
        <ul>
            <li>
                <@s.url id="fameLink" namespace="/" action="hallOfFame"/>
                <@s.a href="%{fameLink}"><@s.text name="hall.of.fame.title"/></@s.a>
            </li>
            <@s.iterator id="challenge" value="challenges">
            <li>
                <@s.url id="showLink" namespace="/" action="show">
                <@s.param name="challengeTag"><@s.property value="#challenge.challengeTag"/></@s.param>
                </@s.url>
                <@s.a href="%{showLink}">#<@s.property value="#challenge.challengeTag"/>
                -
                <@s.property value="#challenge.challengeDescription"/></@s.a>
            </li>
            </@s.iterator>
        </ul>
    </div>
</div>
