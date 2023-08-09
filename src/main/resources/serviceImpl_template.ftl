<#if listFlag == true>
/**
 * ${description}
 * @param fun${functionId}RequestIn
 * @return
 * @throws Exception
 */
    @Override
    public List<${templateEntityOut.className}> fun${functionId}(${templateEntityIn.className} fun${functionId}RequestIn) throws Exception {
        Fun${functionId}Requst request = new Fun${functionId}Requst();
        ArrayList<${templateEntityOut.className}> fun${functionId}ResponseOuts = new ArrayList<>();
        try {
            BeanUtils.copyProperties(fun${functionId}RequestIn,request);
            request.setFunctionId(${functionId});
            AmResultList<Fun${functionId}Response> fun${functionId}Responses = consignmentServiceUtil.getPlatformAmService(fun${functionId}RequestIn).fun${functionId}(request);
            if(CollectionUtils.isEmpty(fun${functionId}Responses)){
                return Collections.emptyList();
            }
            fun${functionId}Responses.forEach(fun${functionId}Response -> {
                Fun${functionId}ResponseOut fun${functionId}ResponseOut = new Fun${functionId}ResponseOut();
                BeanUtils.copyProperties(fun${functionId}Response,fun${functionId}ResponseOut);
                <#if templateEntityOut.autoDate == true>
                    fun${functionId}ResponseOut.setDateTime(Java8TimeUtil.dateAndTimeToMillis(fun${functionId}ResponseOut.getCurrDate(),fun${functionId}ResponseOut.getCurrTime()));
                </#if>
                fun${functionId}ResponseOuts.add(fun${functionId}ResponseOut);
                });
            resultLog.info("fun${functionId} success. , in {} out:{}", fun${functionId}RequestIn, fun${functionId}ResponseOuts.size());
        }catch (Exception e){
            resultLog.error("fun${functionId} error:{}",fun${functionId}RequestIn,e);
            throw e;
        }
        return fun${functionId}ResponseOuts;
    }
<#elseif listFlag == false>
/**
 * ${description}
 * @param fun${functionId}RequestIn
 * @return ${templateEntityOut.className}
 * @throws Exception
 */
    @Override
    public ${templateEntityOut.className} fun${functionId}(${templateEntityIn.className} fun${functionId}RequestIn) throws Exception {

        Fun${functionId}Requst request = new Fun${functionId}Requst();
        ${templateEntityOut.className} responseOut = new ${templateEntityOut.className}();
        try {
            BeanUtils.copyProperties(fun${functionId}RequestIn,request);
            fun${functionId}RequestIn.setFunctionId(${functionId});
            Fun${functionId}Response fun${functionId}Response = consignmentServiceUtil.getPlatformAmService(fun${functionId}RequestIn).fun${functionId}(request);
            BeanUtils.copyProperties(fun${functionId}Response, responseOut);
            <#if templateEntityOut.autoDate == true>
            responseOut.setDateTime(Java8TimeUtil.dateAndTimeToMillis(responseOut.getCurrDate(),responseOut.getCurrTime()));
            </#if>
            resultLog.info("fun${functionId} success. , in {} out:{}", fun${functionId}RequestIn, responseOut);
        }catch (Exception e){
            responseOut.setErrorNo(CommonOut.ERROR);
            resultLog.error("fun${functionId} error:{}",fun${functionId}RequestIn,e);
        }
        return responseOut;
    }
</#if>
