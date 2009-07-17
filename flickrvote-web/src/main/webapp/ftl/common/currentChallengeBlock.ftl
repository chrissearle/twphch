<div class="sidebaritem">
    <h1>Challenge Information</h1>

    <h2>#<@s.property value="challenge.tag"/></h2>

    <p><@s.property value="challenge.title"/></p>

    <h2>Dates</h2>

    <p>
        Start: <@s.date name="challenge.startDate" format="dd.MM.yyyy"/>
        <br/>
        Voting start: <@s.date name="challenge.voteDate" format="dd.MM.yyyy"/>
        <br/>
        End: <@s.date name="challenge.endDate" format="dd.MM.yyyy"/>
    </p>

    <@s.if test="challenge.closed">

    <h2>Results</h2>

    <@s.url id="chart" namespace="/chart" action="showChart">
    <@s.param name="tag" value="challenge.tag"/>
    </@s.url>
    <@s.url id="chartFull" namespace="/" action="showChart">
    <@s.param name="tag" value="challenge.tag"/>
    </@s.url>
    <p>
        <img src="<@s.property value="chart"/>" alt="chart" width="140px"/>
        <br/>
        <@s.a href="%{chartFull}">View full size</@s.a>
    </p>

    </@s.if>

    <@s.if test="challenge.voting">

    <h2>Live Results</h2>
    <@s.url id="votechart" namespace="/chart" action="showVotingChart"/>
    <@s.url id="votechartFull" namespace="/vote" action="showVoteResult"/>
    <p>
        <img src="<@s.property value="votechart"/>" alt="chart" width="140px"/>
        <br/>
        <@s.a href="%{votechartFull}">View full size</@s.a>
    </p>

    </@s.if>
</div>
