package com.example.tamara.smartshop;

public class product {
    private String artikal;
    private String merka;
    private int kolicina;
    private float cena;

    public product() {}
    public product(String artikal, String merka, int kolicina, float cena) {
        super();
        this.artikal = artikal;
        this.merka = merka;
        this.kolicina = kolicina;
        this.cena = cena;
    }
    public String getArtikal() {
        return artikal;
    }
    public void setArtikal(String artikal) {
        this.artikal = artikal;
    }
    public String getMerka() {
        return merka;
    }
    public void setMerka(String merka) {
        this.merka = merka;
    }
    public int getKolicina() {
        return kolicina;
    }
    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
    public float getCena() {
        return cena;
    }
    public void setCena(float cena) {
        this.cena = cena;
    }


}

