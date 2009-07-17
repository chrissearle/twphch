<div class="sidebaritem">
    <h1>Voting is OPEN</h1>

    <h2>#<@s.property value="challenge.tag"/></h2>

    <p><@s.property value="challenge.title"/></p>

    <p>Voting ends: <@s.date name="challenge.endDate" format="dd.MM.yyyy"/> kl 21:00</p>

    <@s.if test="#session.flickrUser">
    <p>
        <@s.if test="voted == false">
        <@s.url id="votingLink" namespace="/vote" action="voteForm"/>
        <@s.a href="%{votingLink}">Vote Here</@s.a>
        </@s.if>
        <@s.else>
        <@s.url id="votingPhotos" namespace="/vote" action="showVotePhotos"/>
        <@s.a href="%{votingPhotos}">Photos</@s.a><br/>
        <@s.url id="votingResults" namespace="/vote" action="showVoteResult"/>
        <@s.a href="%{votingResults}">Current Results</@s.a>
        </@s.else>
    </p>
    </@s.if>
    <@s.else>
    <p>You must be logged in to vote.</p>
    <@s.url id="votingPhotos" namespace="/vote" action="showVotePhotos"/>
    <@s.a href="%{votingPhotos}">Photos</@s.a><br/>
    <@s.url id="votingResultsLink" namespace="/vote" action="showVoteResult"/>
    <p><@s.a href="%{votingResultsLink}">Current Results</@s.a></p>
    </@s.else>
</div>
