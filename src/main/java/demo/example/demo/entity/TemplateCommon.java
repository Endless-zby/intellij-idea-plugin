package demo.example.demo.entity;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 代码生成器VO
 * 包含：
 * {{packageStr}}
 * {{importStr}}
 * {{className}}
 * {{parameters}}
 * {{annotation}}
 *
 */
public class TemplateCommon {

    private String functionId;

    /**
     * package
     */
    private String packageStr;

    /**
     * import
     */
    private String importStr;

    /**
     * className
     */
    private String className;


    private boolean listFlag = Boolean.FALSE;

    private String author;
    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private String description;

    public TemplateCommon() {
    }

    public TemplateCommon(String functionId, String packageStr, String importStr, String className, boolean listFlag, String author, String date, String description) {
        this.functionId = functionId;
        this.packageStr = packageStr;
        this.importStr = importStr;
        this.className = className;
        this.listFlag = listFlag;
        this.author = author;
        this.date = date;
        this.description = description;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getImportStr() {
        return importStr;
    }

    public void setImportStr(String importStr) {
        this.importStr = importStr;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isListFlag() {
        return listFlag;
    }

    public void setListFlag(boolean listFlag) {
        this.listFlag = listFlag;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
