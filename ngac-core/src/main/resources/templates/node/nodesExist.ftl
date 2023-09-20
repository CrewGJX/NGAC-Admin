<!--一次性判断多个node是否存在-->
match (n)
<#list nameList>
    where n.name in [
    <#items as name>
        "${name}"<#if name?has_next>,</#if>
    </#items>
    ]
<#else >
<#--    未传入数组，防止越权    -->
    where 1=2
</#list>
return n.name