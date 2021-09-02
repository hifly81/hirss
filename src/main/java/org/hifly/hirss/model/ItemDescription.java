package org.hifly.hirss.model;


public class ItemDescription {

    //TODO model as mime type
    protected String type;
    protected String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
