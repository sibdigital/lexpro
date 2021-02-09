package ru.sibdigital.lexpro.dto;

public class KeyValue {
    private String type;
    private Long id;
    private String value;
    private String name;

    public KeyValue(){

    }

    public KeyValue(String type, Long id, String value){
        this.type = type;
        this.id = id;
        this.value = value;
        this.name = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
