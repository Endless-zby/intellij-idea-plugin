package demo.example.demo.factory;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GenerateCodeEntityFactory {

    private TreeMap<String, OutDoc> outDocTree;
    private List<String> functionId;
    private AppSettingsState stateSetting = AppSettingsState.getInstance().getState();

    public final static String BASE_DIR = "/src/main/java/";

    private Project project;

    public GenerateCodeEntityFactory(TreeMap<String, OutDoc> outDocTree) {
        this.outDocTree = outDocTree;
    }

    public GenerateCodeEntityFactory(TreeMap<String, OutDoc> outDocTree, List<String> functionId) {
        this.outDocTree = outDocTree;
        this.functionId = functionId;
    }

    public void generateCode(Project project) throws CloneNotSupportedException, IllegalAccessException {
        if (functionId == null || functionId.size() == 0 || outDocTree == null || outDocTree.size() == 0) {
            return;
        }
        // 移除不需要的接口数据
        List<String> collect = outDocTree.keySet().stream().filter(fun -> !functionId.contains(fun)).toList();
        collect.forEach(ss -> outDocTree.remove(ss));

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Performing Task", true) {
            @Override
            public void run(ProgressIndicator progressIndicator) {
                // 设置任务的总长度
                progressIndicator.setIndeterminate(false);
                progressIndicator.setText("Performing Task...");
                progressIndicator.setFraction(0.0);
                // 模拟任务执行
                for (int i = 0; i <= functionId.size(); i++) {
                    String funId = functionId.get(i);
                    try {
                        OutDoc outDoc = outDocTree.get(funId);
                        TemplateEntity templateEntityIn = new TemplateEntity();
                        TemplateEntity templateEntityOut = new TemplateEntity();
                        templateEntityIn.setAuthor(stateSetting.author);
                        templateEntityIn.setDescription(outDoc.getFunName());
                        templateEntityOut = templateEntityIn.clone();

                        List<InterfaceDocEntity> interfaceDocEntities = outDoc.getInterfaceDocEntities();
                        Map<String, List<InterfaceDocEntity>> groupMap = interfaceDocEntities.stream().collect(Collectors.groupingBy(InterfaceDocEntity::getIo));
                        // in
                        List<InterfaceDocEntity> in = groupMap.get(InterfaceDocEntity.IN_PUT);
                        templateEntityIn.initData(funId, InterfaceDocEntity.IN_PUT, in, project);
                        // out
                        List<InterfaceDocEntity> out = groupMap.get(InterfaceDocEntity.OUT_PUT);
                        templateEntityOut.initData(funId, InterfaceDocEntity.OUT_PUT, out, project);

                        TemplateServiceEntity templateServiceImpl = new TemplateServiceEntity(templateEntityIn, templateEntityOut);
                        templateServiceImpl.setFunctionId(funId);
                        templateServiceImpl.setDescription(outDoc.getFunName());
                        templateServiceImpl.setAuthor(stateSetting.author);
                        templateServiceImpl.setListFlag(outDoc.isListBoolean());
                        Map<String, Object> templateServiceImplMap = CommonUtil.convert(templateServiceImpl);

                        String targetClassName;
                        String newMethodCode;
                        if (project.getName().endsWith("core")) {
                            String path = project.getBaseDir().getPath() + BASE_DIR;
                            Map<String, Object> dataIn = CommonUtil.convert(templateEntityIn);
                            FileUtil.generateCodeToFile(templateEntityIn.getClassName(), dataIn, "entity_template.ftl", path + stateSetting.entityIn.replace(".", File.separator) + File.separator);

                            Map<String, Object> dataOut = CommonUtil.convert(templateEntityOut);

                            FileUtil.generateCodeToFile(templateEntityOut.getClassName(), dataOut, "entity_template.ftl", path + stateSetting.entityOut.replace(".", File.separator) + File.separator);

                            newMethodCode = FileUtil.generateCodeToString(templateServiceImplMap, "serviceInterface_template.ftl");
                            targetClassName = stateSetting.interfaceClass;

                        } else if (project.getName().endsWith("service")) {
                            targetClassName = stateSetting.serviceClass;
                            newMethodCode = FileUtil.generateCodeToString(templateServiceImplMap, "serviceImpl_template.ftl");
                        } else {
                            throw new RuntimeException(String.format("不支持的项目类型[%s]", project.getName()));
                        }
                        JavaPsiFacade psiFacade = JavaPsiFacade.getInstance(project);
                        // 根据类名和全局搜索范围查找类
                        PsiClass targetClass = psiFacade.findClass(targetClassName, GlobalSearchScope.allScope(project));
                        if (targetClass == null) {
                            throw new RuntimeException(String.format("目标类[%s]未找到！", targetClassName));
                        } else {
                            FileUtil.generateAndInsertMethod(project, targetClass, newMethodCode);
                        }
                    }catch (Exception e){
                        continue;
                    }
                    // 更新进度条
                    double fraction = (double) i / 100.0;
                    progressIndicator.setFraction(fraction);
                }
            }
        });
    }
}
