package com.tamara.masters.smartshop.models;

import org.springframework.stereotype.Component;

@Component
public class productModel {
    private int item_nbr;
    private String family;
    private String name;
    private String unit;

    public productModel(){}

    public productModel(int item_nbr, String family, String name, String unit) {
        this.item_nbr = item_nbr;
        this.family = family;
        this.name = name;
        this.unit = unit;
    }

    public int getItem_nbr() {
        return item_nbr;
    }

    public void setItem_nbr(int item_nbr) {
        this.item_nbr = item_nbr;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
