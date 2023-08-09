${packageStr}

import lombok.Data;
${importStr}


/**
* @author: ${author}
* @date: ${date}
* @description: ${description}
**/
@Data
public class ${className} <#if extendsStr?has_content><#if entityType == "I">extends CommonIn<#elseif entityType == "O">extends CommonOut</#if></#if> {

    private static final long serialVersionUID = ${randomUUID}L;

<#list parameters as entity>
    /**
     *  ${entity.desc}
     */
    private ${entity.type} ${entity.name};

</#list>

}
