package demo.example.demo.enums;

public enum DataTypeEnum {

    C("C","String"),
    N("N","BigDecimal"),
    i("i","int");

    private String firstLetter;

    private String type;

    DataTypeEnum(String firstLetter, String type) {
        this.firstLetter = firstLetter;
        this.type = type;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
