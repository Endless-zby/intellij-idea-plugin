package demo.example.demo.entity;

public class TemplateServiceEntity extends TemplateCommon{

    private TemplateEntity templateEntityIn;

    private TemplateEntity templateEntityOut;


    public TemplateServiceEntity(String functionId, String packageStr, String importStr, String className, boolean listFlag, String author, String date, String description, TemplateEntity templateEntityIn, TemplateEntity templateEntityOut) {
        super(functionId, packageStr, importStr, className, listFlag, author, date, description);
        this.templateEntityIn = templateEntityIn;
        this.templateEntityOut = templateEntityOut;
    }

    public TemplateServiceEntity(TemplateEntity templateEntityIn, TemplateEntity templateEntityOut) {
        this.templateEntityIn = templateEntityIn;
        this.templateEntityOut = templateEntityOut;
    }

    public TemplateEntity getTemplateEntityIn() {
        return templateEntityIn;
    }

    public void setTemplateEntityIn(TemplateEntity templateEntityIn) {
        this.templateEntityIn = templateEntityIn;
    }

    public TemplateEntity getTemplateEntityOut() {
        return templateEntityOut;
    }

    public void setTemplateEntityOut(TemplateEntity templateEntityOut) {
        this.templateEntityOut = templateEntityOut;
    }
}
