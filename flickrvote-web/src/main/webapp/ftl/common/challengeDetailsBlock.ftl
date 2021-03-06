<div class="sidebaritem">
    <h1 id="block_challenge"><@s.text name="sidebar.details.title"/></h1>

    <div id="block_challenge_content">
        <h2>#<@s.property value="challenge.challengeTag"/></h2>

        <p><@s.property value="challenge.challengeDescription"/></p>

        <p><@s.property value="challenge.challengeNotes"/></p>

        <h2><@s.text name="sidebar.details.dates"/></h2>

        <p>
        <@s.text name="sidebar.details.start"/>: <@s.date name="challenge.challengeStart" format="dd.MM.yyyy"/>
            <br/>
        <@s.text name="sidebar.details.vote"/>: <@s.date name="challenge.challengeVote" format="dd.MM.yyyy"/>
            <br/>
        <@s.text name="sidebar.details.end"/>: <@s.date name="challenge.challengeEnd" format="dd.MM.yyyy"/>
        </p>

    <@s.if test="challenge.challengeClosed">

    <@s.if test="!#request['hideChallengeResults']">
        <h2><@s.text name="sidebar.details.results"/></h2>

    <@s.url id="chartFull" namespace="/stats" action="challengeChart">
    <@s.param name="tag" value="challenge.challengeTag"/>
    </@s.url>
        <div id="challengeChart" style="width: 140px; height: 300px"></div>
        <br/>
        <@s.a href="%{chartFull}"><@s.text name="sidebar.link.viewfullsize"/></@s.a>
    </@s.if>

    </@s.if>

    <@s.if test="challenge.challengeVoting && #session.flickrUser && voted">
        <h2><@s.text name="sidebar.details.liveresults"/></h2>
    <@s.url id="votechartFull" namespace="/vote" action="voteChart"/>
        <div id="voteChart" style="width: 140px; height: 300px"></div>
        <br/>
        <@s.a href="%{votechartFull}"><@s.text name="sidebar.link.viewfullsize"/></@s.a>
    </@s.if>
    </div>
</div>
