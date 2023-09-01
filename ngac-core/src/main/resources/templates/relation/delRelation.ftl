match (source)-[r]->(target)
where
<#if source.id??>
    id(source) = ${source.id}
<#else>
    all(l1 in ["${source.labels?join("\",\"", "")}"] where l1 in labels(source))
    and source.name = "${source.name?string}"
</#if>
and
<#if target.id??>
    id(target) = ${target.id}
<#else>
    all(l2 in ["${target.labels?join("\",\"", "")}"] where l2 in labels(target))
    and target.name = "${target.name?string}"
</#if>

<#if relation.id??>
    and id(r) = ${relation.id}
<#elseif relation.name??>
    and "${relation.type}" = type(r)
    and r.name = "${relation.name?string}"
</#if>
detach delete r