match (source)-[r]->(target)
where all(l1 in ["${source.labels?join("\",\"", "")}"] where l1 in labels(source))
and
<#if source.id??>
    id(source) = ${source.id}
<#else>
    source.name = "${source.name?string}"
</#if>
and all(l2 in ["${target.labels?join("\",\"", "")}"] where l2 in labels(target))
and
<#if target.id??>
    id(target) = ${target.id}
<#else>
    target.name = "${target.name?string}"
</#if>
and "${relation.type}" = type(r)
<#if relation.id??>
    and id(r) = ${relation.id}
<#elseif relation.name??>
    and r.name = "${relation.name?string}"
</#if>
detach delete r