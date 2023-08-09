package demo.example.demo.entity;


import java.util.List;


public class OutDoc {

    private String funId;

    private String funName;

    private String isList = "N";

    private String entityIn;

    private String entityOut;

    private List<InterfaceDocEntity> interfaceDocEntities;

    public boolean isListBoolean(){
       return "Y".equals(isList);
    }

    public OutDoc() {
    }

    public OutDoc(String funId, String funName, String isList, String entityIn, String entityOut, List<InterfaceDocEntity> interfaceDocEntities) {
        this.funId = funId;
        this.funName = funName;
        this.isList = isList;
        this.entityIn = entityIn;
        this.entityOut = entityOut;
        this.interfaceDocEntities = interfaceDocEntities;
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

    public String getIsList() {
        return isList;
    }

    public void setIsList(String isList) {
        this.isList = isList;
    }

    public String getEntityIn() {
        return entityIn;
    }

    public void setEntityIn(String entityIn) {
        this.entityIn = entityIn;
    }

    public String getEntityOut() {
        return entityOut;
    }

    public void setEntityOut(String entityOut) {
        this.entityOut = entityOut;
    }

    public List<InterfaceDocEntity> getInterfaceDocEntities() {
        return interfaceDocEntities;
    }

    public void setInterfaceDocEntities(List<InterfaceDocEntity> interfaceDocEntities) {
        this.interfaceDocEntities = interfaceDocEntities;
    }
}
