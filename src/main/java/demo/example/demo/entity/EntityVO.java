package demo.example.demo.entity;

public class EntityVO {

    /**
     * 属性类型
     */
    private String type;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性意义
     */
    private String desc;

    public EntityVO(String type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
