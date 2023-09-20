match (source)-[r]->(target)
where
<#assign segment>
    <#if id??>
        <#if id??>
            and id(r) = ${id}
        <#else>
            and type(r) = "${type}"
            and r.name = "${name?string}"
        </#if>
    <#else>
        <#if source.id??>
            and id(source) = ${source.id}
        <#else>
            and all(l1 in ["${source.labels?join("\",\"", "")}"] where l1 in labels(source))
            and source.name = "${source.name?string}"
        </#if>
        <#if target.id??>
            and id(target) = ${target.id}
        <#else>
            and all(l2 in ["${target.labels?join("\",\"", "")}"] where l2 in labels(target))
            and target.name = "${target.name?string}"
        </#if>
        <#if type??>
            and type(r) = "${type}"
        </#if>
    </#if>
</#assign>
${segment?trim?remove_beginning("and")}
return r