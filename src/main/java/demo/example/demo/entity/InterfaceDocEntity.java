package demo.example.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

public class InterfaceDocEntity implements Serializable {

    public static final String IN_PUT = "I";
    public static final String OUT_PUT = "O";

    @ExcelProperty(value = "功能编号")
    private String funId;

    @ExcelProperty(value = "功能名称")
    private String funName;

    @ExcelProperty(value = "输入输出")
    private String io;

    @ExcelProperty(value = "参数字段")
    private String parameter;

    @ExcelProperty(value = "参数类型")
    private String parameterType;

    @ExcelProperty(value = "参数名称")
    private String parameterName;

    @ExcelProperty(value = "功能类型")
    private String funType;

    @ExcelProperty(value = "是否结果集")
    private String isList;

    @ExcelProperty(value = "是否为空")
    private String isNull = "N";

    @ExcelProperty(value = "验证模式")
    private String authenticationMode;

    public InterfaceDocEntity() {
    }

    public InterfaceDocEntity(String funId, String funName, String io, String parameter, String parameterType, String parameterName, String funType, String isList, String isNull, String authenticationMode) {
        this.funId = funId;
        this.funName = funName;
        this.io = io;
        this.parameter = parameter;
        this.parameterType = parameterType;
        this.parameterName = parameterName;
        this.funType = funType;
        this.isList = isList;
        this.isNull = isNull;
        this.authenticationMode = authenticationMode;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getIo() {
        return io;
    }

    public void setIo(String io) {
        this.io = io;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getFunType() {
        return funType;
    }

    public void setFunType(String funType) {
        this.funType = funType;
    }

    public String getIsList() {
        return isList;
    }

    public void setIsList(String isList) {
        this.isList = isList;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getAuthenticationMode() {
        return authenticationMode;
    }

    public void setAuthenticationMode(String authenticationMode) {
        this.authenticationMode = authenticationMode;
    }
}
