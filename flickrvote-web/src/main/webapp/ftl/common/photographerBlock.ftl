<div class="sidebaritem">
    <h1><@s.text name="sidebar.photographer.title"/></h1>

    <p>
        <@s.url id="myPicsLink" namespace="/" action="myPics"/>
        <@s.a href="%{myPicsLink}"><@s.text name="mypics.link.text"/></@s.a>
    </p>

    <@s.url id="chart" namespace="/chart" action="showPhotographerChart"/>
    <@s.url id="chartFull" namespace="/" action="showPhotographerChart"/>
    <p>
        <img src="<@s.property value="chart"/>" alt="chart" width="140px"/>
        <br/>
        <@s.a href="%{chartFull}"><@s.text name="sidebar.link.viewfullsize"/></@s.a>
    </p>
</div>
