package com.github.maleksandrowicz93.springbootcreditapi;

public enum ProductEnum {

    CONSUMER("Kredyt konsumpcyjny"),
    MORTGAGE("Kredyt hipoteczny"),
    INVESTMENT("Kredyt inwestycyjny"),
    CONSOLIDATION("Kredyt konsolidayjny"),
    CARD("Karta kredytowa");

    private final String value;

    ProductEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
