match (n)
where "${label}" in labels(n)
and
<#if id??>
    id(n)=${id}
<#else >
    n.name="${name}"</#if>
return n