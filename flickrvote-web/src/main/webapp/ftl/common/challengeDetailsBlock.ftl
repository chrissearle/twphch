<div class="sidebaritem">
    <h1><@s.text name="sidebar.details.title"/></h1>

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

    <h2><@s.text name="sidebar.details.results"/></h2>

    <@s.url id="chart" namespace="/chart" action="showChart">
    <@s.param name="tag" value="challenge.challengeTag"/>
    </@s.url>
    <@s.url id="chartFull" namespace="/" action="showChart">
    <@s.param name="tag" value="challenge.challengeTag"/>
    </@s.url>
    <p>
        <img src="<@s.property value="chart"/>" alt="chart" width="140px"/>
        <br/>
        <@s.a href="%{chartFull}"><@s.text name="sidebar.link.viewfullsize"/></@s.a>
    </p>

    </@s.if>

    <@s.if test="challenge.challengeVoting && #session.flickrUser && voted">
    <h2><@s.text name="sidebar.details.liveresults"/></h2>
    <@s.url id="votechart" namespace="/chart" action="showVotingChart"/>
    <@s.url id="votechartFull" namespace="/vote" action="showVoteResult"/>
    <p>
        <img src="<@s.property value="votechart"/>" alt="chart" width="140px"/>
        <br/>
        <@s.a href="%{votechartFull}"><@s.text name="sidebar.link.viewfullsize"/></@s.a>
    </p>
    </@s.if>
</div>
