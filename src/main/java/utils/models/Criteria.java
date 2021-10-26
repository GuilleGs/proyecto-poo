package utils.models;

public class Criteria {

    private final String key, criteria;
    private String value;

    public Criteria(String key, String value, String criteria, boolean strValue) {
        if (strValue)
            this.value = String.format("'%s'", value);
        else
            this.value = value;
        this.key = key;
        this.criteria = criteria;
    }

    public Criteria(String key, String value, boolean strValue) {
        if (strValue)
            this.value = String.format("'%s'", value);
        else
            this.value = value;
        this.key = key;
        this.criteria = "=";
    }

    public Criteria(String key, boolean strValue) {
        this.value = null;
        this.key = key;
        this.criteria = "=";
    }

    public String get() {
        return String.format("%s %s %s", this.key, this.criteria, this.value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    }

    public void setValue(String valu
        this.value = value;

    public String getCriteria() {
        return criteria;
    }
}
