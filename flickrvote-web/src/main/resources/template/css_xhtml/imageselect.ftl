<#include "/${parameters.templateDir}/${parameters.theme}/controlheader.ftl" />
<#assign itemCount = 0/>
<#if parameters.images??>

<@s.iterator id="photo" value="parameters.images">
    <#assign itemCount = itemCount + 1/>
    <#assign itemKey = stack.findValue("id")/>
    <#assign itemValue = parameters.votevalue />
    <#assign itemKeyStr=itemKey.toString() />
    <div class="photobox">
        <h3><@s.property value="#photo.title"/> - <@s.property value="#photo.photographerName"/></h3>
        <p><a id="<@s.property value='#photo.id'/>" class="toggleLink" style="display: none"><@s.text name="show.hide"/></a>
        <input type="checkbox" name="${parameters.name?html}" value="${itemKeyStr?html}" id="${parameters.name?html}-${itemCount}"<#rt/>
                <#if tag.contains(parameters.nameValue, itemKey)>
         checked="checked"<#rt/>
                </#if>
                <#if parameters.disabled?default(false)>
         disabled="disabled"<#rt/>
                </#if>
                <#if parameters.title??>
         title="${parameters.title?html}"<#rt/>
                </#if>
                <#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
                <#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
        />
        <label for="${parameters.name?html}-${itemCount}" class="checkboxLabel">${itemValue?html}</label></p>
        <div id="photobox_<@s.property value='#photo.id'/>">
        <a href="${photo.imageHomePage}"><img src="${photo.imagePictureLink}" alt="${photo.title}"/></a>
        </div>
    </div>
</@s.iterator>
<#else>
  &nbsp;
</#if>
<#include "/${parameters.templateDir}/css_xhtml/controlfooter.ftl" /><#nt/>
