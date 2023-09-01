match (n<#list labels as label>:${label}</#list>)
<#if id??>
    where id(n)=${id}
<#else >
    where n.name="${name}"
</#if>
detach delete n