<div class="sidebaritem">
    <h1 id="block_challenge_list"><@s.text name="sidebar.olderchallenges.title"/></h1>

    <div id="block_challenge_list_content">
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
                <@s.a href="%{showLink}">#<@s.property value="#challenge.shortTag"/>
                    -
                <@s.property value="#challenge.challengeDescription"/></@s.a>
                </li>
            </@s.iterator>
                <li>
                <@s.url id="challengeListUrl" action="listChallenges" namespace="/"/>
                <@s.a href="%{challengeListUrl}"><@s.text name="link.list.challenges"/></@s.a>
                </li>
            </ul>
        </div>
    </div>
</div>