match (source)-[r]->(target)
where
<#assign segment>
    <#if id??>
        and type(r) = "${type}"
        <#if id??>
            and id(r) = ${id}
        <#else>
            and r.name = "${name?string}"
        </#if>
    <#else>
        and all(l1 in ["${source.labels?join("\",\"", "")}"] where l1 in labels(source))
        and all(l2 in ["${target.labels?join("\",\"", "")}"] where l2 in labels(target))
        <#if source.id??>
            and id(source) = ${source.id}
        <#else>
            and source.name = "${source.name?string}"
        </#if>
        <#if target.id??>
            and id(target) = ${target.id}
        <#else>
            and target.name = "${target.name?string}"
        </#if>
        <#if type??>
            and type(r) = "${type}"
        </#if>
    </#if>
</#assign>
${segment?trim?remove_beginning("and")}
return r