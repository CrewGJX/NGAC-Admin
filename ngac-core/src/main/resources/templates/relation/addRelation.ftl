match (source)
where all(l1 in ["${source.labels?join("\",\"", "")}"] where l1 in labels(source))
and
<#if source.id??>
    id(source) = ${source.id}
<#else>
    source.name = "${source.name?string}"
</#if>
with source
match (target)
where all(l2 in ["${target.labels?join("\",\"", "")}"] where l2 in labels(target))
and
<#if target.id??>
    id(target) = ${target.id}
<#else>
    target.name = "${target.name?string}"
</#if>
merge (source)-[r:${relationLabel} ${propertyMap}]->(target)