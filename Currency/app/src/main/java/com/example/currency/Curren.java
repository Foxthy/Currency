package com.example.currency;

public class Curren {
    private String Name;
    private String Code;
    private Double Currency;

    public Curren(String name, String code, Double currency) {
        Name = name;
        Code = code;
        Currency = currency;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }


    public Double getCurrency() {
        return Currency;
    }

    public void setCurrency(Double currency) {
        Currency = currency;
    }

    @Override
    public String toString() {
        return "Curren{" +
                "Name='" + Name + '\'' +
                ", Code='" + Code + '\'' +
                ", Currency=" + Currency +
                '}';
    }
}
