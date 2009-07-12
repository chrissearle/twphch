<div class="sidebaritem">
    <h1>Older Challenges</h1>

    <div class="sbilinks">
        <ul>
            <@s.iterator id="challenge" value="challengeList">
            <li>
                <@s.url id="showLink" action="show">
                <@s.param name="challengeTag"><@s.property value="#challenge.tag"/></@s.param>
                </@s.url>
                <@s.a href="%{showLink}">#<@s.property value="#challenge.Tag"/>
                -
                <@s.property value="#challenge.title"/></@s.a>
            </li>
            </@s.iterator>
        </ul>
    </div>
</div>
