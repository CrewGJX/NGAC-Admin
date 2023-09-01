match (source)
where
<#if source.id??>
    id(source) = ${source.id}
<#else>
    all(l1 in ["${source.labels?join("\",\"", "")}"] where l1 in labels(source))
    and source.name = "${source.name?string}"
</#if>
with source
match (target)
where
<#if target.id??>
    id(target) = ${target.id}
<#else>
    all(l2 in ["${target.labels?join("\",\"", "")}"] where l2 in labels(target))
    and target.name = "${target.name?string}"
</#if>
merge (source)-[r:${relationLabel} ${propertyMap}]->(target)