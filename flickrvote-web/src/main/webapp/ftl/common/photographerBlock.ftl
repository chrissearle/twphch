<div class="sidebaritem">
    <h1 id="block_photographer"><@s.text name="sidebar.photographer.title"/></h1>

    <div id="block_photographer_content">
        <p>
            <@s.url id="myPicsLink" namespace="/account" action="mypics"/>
            <@s.a href="%{myPicsLink}"><@s.text name="mypics.link.text"/></@s.a>
        </p>

        <@s.if test="!#request['hidePhotographerChart']">
            <@s.url id="chartFull" namespace="/stats" action="photographerChart"/>
            <div id="photographerChart" style="width: 140px; height: 300px"></div>
            <br/>
            <@s.a href="%{chartFull}"><@s.text name="sidebar.link.viewfullsize"/></@s.a>
        </@s.if>
    </div>
</div>