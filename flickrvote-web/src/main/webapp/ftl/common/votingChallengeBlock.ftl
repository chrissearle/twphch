<div class="sidebaritem">
    <h1><@s.text name="sidebar.voting.title"/></h1>

    <h2>#<@s.property value="challenge.challengeTag"/></h2>

    <p><@s.property value="challenge.challengeTitle"/></p>

    <p><@s.text name="sidebar.voting.ends"/>: <@s.date name="challenge.challengeEnd" format="dd.MM.yyyy"/> kl 21:00</p>

    <@s.if test="#session.flickrUser">
    <p>
        <@s.if test="voted == false">
        <@s.url id="votingLink" namespace="/vote" action="voteForm"/>
        <@s.a href="%{votingLink}"><@s.text name="sidebar.voting.votehere"/></@s.a>
        </@s.if>
        <@s.else>
        <@s.url id="votingPhotos" namespace="/vote" action="showVotePhotos"/>
        <@s.a href="%{votingPhotos}"><@s.text name="sidebar.voting.photos"/></@s.a><br/>
        <@s.url id="votingResults" namespace="/vote" action="showVoteResult"/>
        <@s.a href="%{votingResults}"><@s.text name="sidebar.voting.results"/></@s.a>
        <br/>
        <@s.text name="you.have.voted"/>
        <br/>
        <@s.url id="clearVote" namespace="/vote" action="clearVote"/>
        <@s.a href="%{clearVote}"><@s.text name="sidebar.voting.clear"/></@s.a><br/>
        </@s.else>
    </p>
    </@s.if>
    <@s.else>
    <p><@s.text name="sidebar.voting.notloggedin"/>.</p>
    <@s.url id="votingPhotos" namespace="/vote" action="showVotePhotos"/>
    <@s.a href="%{votingPhotos}"><@s.text name="sidebar.voting.photos"/></@s.a><br/>
    </@s.else>
</div>
