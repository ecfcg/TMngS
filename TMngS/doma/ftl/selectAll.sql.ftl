<#-- See also org.seasar.doma.gradle.codegen.desc.SqlDesc -->
select
  /*%expand*/*
from
  ${entityDesc.tableName}
order by
<#list entityDesc.idEntityPropertyDescs as property>
  ${property.columnName}<#if property_has_next>
  ,</#if>
</#list>
