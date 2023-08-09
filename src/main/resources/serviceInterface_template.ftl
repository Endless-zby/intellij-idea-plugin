<#if listFlag == true>
@FunctionDescription(value = "${description}", functionCode = ${functionId})
public List<${templateEntityOut.className}> fun${functionId}(${templateEntityIn.className} in) throws ServiceException;
<#elseif listFlag == false>
@FunctionDescription(value = "${description}", functionCode = ${functionId})
public ${templateEntityOut.className} fun${functionId}(${templateEntityIn.className} in) throws ServiceException;
</#if>
