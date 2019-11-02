package com.firestore.smartshop.database.databaseservice.models;

public class SaleModel {
    private String date;
    private int item_nbr;
    private float unit_sales;
    private String year;

    public SaleModel(){}

    public SaleModel(String date, int item_nbr, float unit_sales, String year) {
        this.date = date;
        this.item_nbr = item_nbr;
        this.unit_sales = unit_sales;
        this.year = year;
    }

    @Override
    public String toString() {
        return "SaleModel{" +
                "date='" + date + '\'' +
                ", item_nbr=" + item_nbr +
                ", unit_sales=" + unit_sales +
                ", year='" + year + '\'' +
                '}';
    }

    public SaleModel(String date, int item_nbr, float unit_sales) {
        this.date = date;
        this.item_nbr = item_nbr;
        this.unit_sales = unit_sales;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getItem_nbr() {
        return item_nbr;
    }

    public void setItem_nbr(int item_nbr) {
        this.item_nbr = item_nbr;
    }

    public float getUnit_sales() {
        return unit_sales;
    }

    public void setUnit_sales(float unit_sales) {
        this.unit_sales = unit_sales;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
