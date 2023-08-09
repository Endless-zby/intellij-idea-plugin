package demo.example.demo.listener;


import com.alibaba.excel.context.AnalysisContext;

import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.base.CaseFormat;
import demo.example.demo.entity.InterfaceDocEntity;
import demo.example.demo.entity.OutDoc;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class InterfaceDocEntityListener extends AnalysisEventListener<InterfaceDocEntity> {

    TreeMap<String, OutDoc> outDoc;

    ArrayList<InterfaceDocEntity> funInterfaceDocList = new ArrayList<>();

    private static List<String> ignoreParameter = new ArrayList<>();

    String funName = null;

    String funId = null;

    String isList = null;

    public InterfaceDocEntityListener(TreeMap<String, OutDoc> outDoc) {
        this.outDoc = outDoc;
    }

    public void setIgnoreParameter(List<String> ignoreParameter){
        InterfaceDocEntityListener.ignoreParameter = ignoreParameter;
    }

    @Override
    public void invoke(InterfaceDocEntity interfaceDocEntity, AnalysisContext analysisContext) {

        if(null == interfaceDocEntity){
            return;
        }

        if(StringUtils.isNotEmpty(interfaceDocEntity.getFunName())){
            interfaceDocEntity.setFunName(interfaceDocEntity.getFunName().replace("\n",""));
        }

        if(StringUtils.isEmpty(interfaceDocEntity.getFunId())){
            if(StringUtils.isNotEmpty(interfaceDocEntity.getParameter())){
                String lowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, interfaceDocEntity.getParameter());
                if(ignoreParameter.size() > 0 && (ignoreParameter.contains(lowerCamel) || ignoreParameter.contains(interfaceDocEntity.getParameter()))){
                    return;
                }
                interfaceDocEntity.setParameter(lowerCamel);
                funInterfaceDocList.add(interfaceDocEntity);
            }
        }

        if (isNumeric(interfaceDocEntity.getFunId())) {
            funId = interfaceDocEntity.getFunId();
            funName = interfaceDocEntity.getFunName();
            isList = interfaceDocEntity.getIsList();
            String lowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, interfaceDocEntity.getParameter());
            if(ignoreParameter.size() > 0 && (ignoreParameter.contains(lowerCamel) || ignoreParameter.contains(interfaceDocEntity.getParameter()))){
                return;
            }
            interfaceDocEntity.setParameter(lowerCamel);
            funInterfaceDocList.add(interfaceDocEntity);
        }
        if ("功能编号".equals(interfaceDocEntity.getFunId())) {
            OutDoc outDoc1 = new OutDoc();
            outDoc1.setFunId(funId);
            outDoc1.setFunName(funName);
            outDoc1.setIsList(isList);
            outDoc1.setInterfaceDocEntities(funInterfaceDocList);
            outDoc.put(funId,outDoc1);
            // 清除
            funInterfaceDocList = new ArrayList<>();
            funName = null;
            funId = null;
            isList = null;
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        System.out.println("所有数据解析完成！");
    }
}
