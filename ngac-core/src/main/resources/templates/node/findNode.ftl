match (n)
where
<#if id??>
    id(n)=${id}
<#else >
    "${label}" in labels(n)
    and n.name="${name}"
</#if>
return n