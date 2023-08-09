package demo.example.demo.enums;

public enum ImportEnum {

    BigDecimal("BigDecimal","import java.math.BigDecimal;\n"),
    Data("Data","import lombok.Data;\n"),
    List("List","import java.util.List;\n"),
    CommonOut("CommonOut","import com.gemantic.wealth.consignment.model.out.CommonOut;\n"),
    CommonIn("CommonIn","import com.gemantic.wealth.consignment.model.in.CommonIn;\n");




    private String key;
    private String value;

    ImportEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
