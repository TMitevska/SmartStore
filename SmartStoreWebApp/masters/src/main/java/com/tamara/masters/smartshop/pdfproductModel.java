package com.tamara.masters.smartshop;

public class pdfproductModel {
    private String productName;
    private String productType;
    private double productQuantity;
    private double productPrice;

    public pdfproductModel(){}

    public pdfproductModel(String productName, String productType, double productQuantity, double productPrice) {
        this.productName = productName;
        this.productType = productType;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "pdfproductModel{" +
                "productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", productQuantity=" + productQuantity +
                ", productPrice=" + productPrice +
                '}';
    }
}
