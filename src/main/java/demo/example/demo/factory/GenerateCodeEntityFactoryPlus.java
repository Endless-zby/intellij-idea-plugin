package demo.example.demo.factory;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import demo.example.demo.entity.InterfaceDocEntity;
import demo.example.demo.entity.OutDoc;
import demo.example.demo.entity.TemplateEntity;
import demo.example.demo.entity.TemplateServiceEntity;
import demo.example.demo.setting.AppSettingsState;
import demo.example.demo.util.CommonUtil;
import demo.example.demo.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GenerateCodeEntityFactoryPlus {


    private TreeMap<String, OutDoc> outDocTree;
    private List<String> functionId;
    private AppSettingsState stateSetting = AppSettingsState.getInstance().getState();

    public final static String BASE_DIR = "/src/main/java/";

    private Project project;

    public GenerateCodeEntityFactoryPlus(TreeMap<String, OutDoc> outDocTree) {
        this.outDocTree = outDocTree;
    }

    public GenerateCodeEntityFactoryPlus(TreeMap<String, OutDoc> outDocTree, List<String> functionId) {
        this.outDocTree = outDocTree;
        this.functionId = functionId;
    }

    public void generateCode(Project project) throws CloneNotSupportedException, IllegalAccessException {
        this.project = project;
        if (functionId == null || functionId.size() == 0 || outDocTree == null || outDocTree.size() == 0) {
            return;
        }
        // 移除不需要的接口数据
        List<String> collect = outDocTree.keySet().stream().filter(fun -> !functionId.contains(fun)).toList();
        collect.forEach(ss -> outDocTree.remove(ss));

        String targetClassName = null;
        ArrayList<String> newMethodCodeList = Lists.newArrayList();
        for (String functionId : functionId) {
            OutDoc outDoc = outDocTree.get(functionId);
            TemplateEntity templateEntityIn = new TemplateEntity();
            TemplateEntity templateEntityOut = new TemplateEntity();
            templateEntityIn.setAuthor(stateSetting.author);
            templateEntityIn.setDescription(outDoc.getFunName());
            templateEntityOut = templateEntityIn.clone();

            List<InterfaceDocEntity> interfaceDocEntities = outDoc.getInterfaceDocEntities();
            Map<String, List<InterfaceDocEntity>> groupMap = interfaceDocEntities.stream().collect(Collectors.groupingBy(InterfaceDocEntity::getIo));
            // in
            List<InterfaceDocEntity> in = groupMap.get(InterfaceDocEntity.IN_PUT);
            templateEntityIn.initData(functionId, InterfaceDocEntity.IN_PUT, in, project);
            // out
            List<InterfaceDocEntity> out = groupMap.get(InterfaceDocEntity.OUT_PUT);
            templateEntityOut.initData(functionId, InterfaceDocEntity.OUT_PUT, out, project);

            TemplateServiceEntity templateServiceImpl = new TemplateServiceEntity(templateEntityIn, templateEntityOut);
            templateServiceImpl.setFunctionId(functionId);
            templateServiceImpl.setDescription(outDoc.getFunName());
            templateServiceImpl.setAuthor(stateSetting.author);
            templateServiceImpl.setListFlag(outDoc.isListBoolean());
            Map<String, Object> templateServiceImplMap = CommonUtil.convert(templateServiceImpl);


            String newMethodCode;
            if(project.getName().endsWith("core")){
                String path = project.getBaseDir().getPath() + BASE_DIR;
                Map<String, Object> dataIn = CommonUtil.convert(templateEntityIn);
                FileUtil.generateCodeToFile(templateEntityIn.getClassName(), dataIn, "entity_template.ftl", path + stateSetting.entityIn.replace(".", File.separator) + File.separator);

                Map<String, Object> dataOut = CommonUtil.convert(templateEntityOut);

                FileUtil.generateCodeToFile(templateEntityOut.getClassName(), dataOut, "entity_template.ftl", path + stateSetting.entityOut.replace(".", File.separator) + File.separator);

                newMethodCode = FileUtil.generateCodeToString(templateServiceImplMap, "serviceInterface_template.ftl");
                targetClassName = stateSetting.interfaceClass;
                newMethodCodeList.add(newMethodCode);
            } else if (project.getName().endsWith("service")) {
                targetClassName = stateSetting.serviceClass;
                newMethodCode = FileUtil.generateCodeToString(templateServiceImplMap, "serviceImpl_template.ftl");
                newMethodCodeList.add(newMethodCode);
            }else {
                throw new RuntimeException(String.format("不支持的项目类型[%s]", project.getName()));
            }
        }
        JavaPsiFacade psiFacade = JavaPsiFacade.getInstance(project);
        // 根据类名和全局搜索范围查找类
        PsiClass targetClass = psiFacade.findClass(targetClassName, GlobalSearchScope.allScope(project));
        if (targetClass == null) {
            throw new RuntimeException(String.format("目标类[%s]未找到！", targetClassName));
        }else {
            FileUtil.generateAndInsertMethods(project, targetClass, newMethodCodeList);
        }
        System.out.println("success");
    }

}
