<#-- See also org.seasar.doma.gradle.codegen.desc.DaoDesc -->
<#import "/lib.ftl" as lib>
<#if lib.copyright??>
${lib.copyright}
</#if>
<#if packageName??>
package ${packageName};
</#if>

<#list importNames as importName>
import ${importName};
</#list>
import java.util.List;

/**
<#if lib.author??>
 * @author ${lib.author}
</#if>
 */
@Dao<#if configClassSimpleName??>(config = ${configClassSimpleName}.class)</#if>
public interface ${simpleName} {

<#if entityDesc.idEntityPropertyDescs?size gt 0>
    /**
<#list entityDesc.idEntityPropertyDescs as property>
     * @param ${property.name}
</#list>
     * @return the <#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entity
     */
    @Select
    <#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> selectById(<#list entityDesc.idEntityPropertyDescs as property>${property.propertyClassSimpleName} ${property.name}<#if property_has_next>, </#if></#list>);

</#if>
    /**
     * @return all <#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entities
     */
    @Select
    List<<#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if>> selectAll();

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(<#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(<#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(<#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entity);
}
