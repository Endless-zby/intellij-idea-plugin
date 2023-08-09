package demo.example.demo.entity;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import demo.example.demo.enums.DataTypeEnum;
import demo.example.demo.enums.ImportEnum;
import demo.example.demo.setting.AppSettingsState;
import demo.example.demo.util.CommonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TemplateEntity extends TemplateCommon implements Cloneable {

    private static final String IN = "I";
    private static final String OUT = "O";

    private static final String EXTENDS_IN_NAME = "CommonIn";
    private static final String EXTENDS_OUT_NAME = "CommonOut";
    private static final String REQUEST_IN_NAME = "RequestIn";
    private static final String REQUEST_OUT_NAME = "ResponseOut";
    private static final String PACKAGE_NAME = "package ";




    /**
     * 代码生成器生成的实体对象类 对应 IN 和 OUT
     */
    private List<EntityVO> parameters;

    /**
     * I or O
     */
    private String entityType;

    /**
     * 此类继承自谁
     */
    private String extendsStr;

    /**
     * 自动格式化时间
     */
    private boolean autoDate = false;

    public TemplateEntity(List<EntityVO> parameters, String entityType, String extendsStr) {
        this.parameters = parameters;
        this.entityType = entityType;
        this.extendsStr = extendsStr;
    }

    public TemplateEntity(String functionId, String packageStr, String importStr, String className, boolean listFlag, String author, String date, String description, List<EntityVO> parameters, String entityType, String extendsStr) {
        super(functionId, packageStr, importStr, className, listFlag, author, date, description);
        this.parameters = parameters;
        this.entityType = entityType;
        this.extendsStr = extendsStr;
    }

    @Override
    public TemplateEntity clone() throws CloneNotSupportedException {
        return (TemplateEntity) super.clone();
    }

    public TemplateEntity() {
    }

    public TemplateEntity(String functionId, String packageStr, String importStr, String className, boolean listFlag, String author, String date, String description) {
        super(functionId, packageStr, importStr, className, listFlag, author, date, description);
    }

    public boolean isAutoDate() {
        return autoDate;
    }

    public void setAutoDate(boolean autoDate) {
        this.autoDate = autoDate;
    }

    public List<EntityVO> getParameters() {
        return parameters;
    }

    public void setParameters(List<EntityVO> parameters) {
        this.parameters = parameters;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getExtendsStr() {
        return extendsStr;
    }

    public void setExtendsStr(String extendsStr) {
        this.extendsStr = extendsStr;
    }

    public void initData (String functionId, String io, List<InterfaceDocEntity> interfaceDocEntities, Project project) {

        AppSettingsState state = AppSettingsState.getInstance().getState();

        super.setAuthor(state.author);
        super.setFunctionId(functionId);
        // 执行一些初始化的方法，比如
        // 根据parameters设置entityType
        // 根据functionCode设置className等操作

        PsiClass common = null;
        if(IN.equals(io)){
            super.setClassName("Fun" + functionId + REQUEST_IN_NAME);
            setEntityType(IN);
            setExtendsStr(EXTENDS_IN_NAME);
            //移除父类存在的属性
            System.out.println("111111111111111 :" + state.entityInCommon);
            common = CommonUtil.getClassByName(state.entityInCommon, project);
            super.setPackageStr(PACKAGE_NAME + state.entityIn + ";");
        }
        if(OUT.equals(io)){
            super.setClassName("Fun" + functionId + REQUEST_OUT_NAME);
            setEntityType(OUT);
            setExtendsStr(EXTENDS_OUT_NAME);
            System.out.println("22222222222222 :" + state.entityOutCommon);
            common = CommonUtil.getClassByName(state.entityOutCommon, project);
            super.setPackageStr(PACKAGE_NAME + state.entityOut + ";");
        }
        ArrayList<EntityVO> entityVOS = Lists.newArrayList();

        if(common == null){
            throw new RuntimeException("请检查父类配置");
        }
        PsiField[] fields = common.getFields();
        List<String> commonField = Arrays.stream(fields).toList().stream().map(PsiField::getName).toList();

        interfaceDocEntities.removeIf(ss -> commonField.contains(ss.getParameter()));
        interfaceDocEntities.forEach(obj -> {entityVOS.add(new EntityVO(DataTypeEnum.valueOf(obj.getParameterType().substring(0,1)).getType(), obj.getParameter(), obj.getParameterName()));});

        // 自动加入参数
        if(state.autoDate && OUT.equals(io)){
            boolean hasCurrDate = entityVOS.stream()
                    .map(EntityVO::getName)
                    .filter(name -> name.equals("currDate") || name.equals("currTime"))
                    .distinct()
                    .count() == 2;
            if(hasCurrDate){
                setAutoDate(true);
                entityVOS.add(new EntityVO("Long", "dateTime", "格式化时间戳"));
            }
        }
        setParameters(entityVOS);
        Set<String> parameterTypeList = parameters.stream().map(EntityVO::getType).collect(Collectors.toSet());
        parameterTypeList.add(getExtendsStr());
        StringBuilder importStr = new StringBuilder();
        for (String par : parameterTypeList) {
            try {
                ImportEnum importEnum = ImportEnum.valueOf(par);
                importStr.append(importEnum.getValue());
            } catch (IllegalArgumentException e) {
                System.out.println("字符串在枚举类中不存在");
            }
        }
        super.setImportStr(importStr.toString());
    }
}
