package com.example.demo.models;

public enum XepHang {
    EXCELLENT("Excellent"),GOOD("Good"), AVERAGE("Average"), WEAK("Weak");
    private final String xepHang;

    XepHang(String r) {
        this.xepHang = r;
    }

    public String getRank(){
        return this.xepHang;
    }
}
