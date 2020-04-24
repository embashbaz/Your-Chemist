package com.example.yourchemist.AdapterAndModel;

public class Medecine extends Chemist {

    String scientificName;
    String genericName;
    String countryMade;
    String currency;
    String detailsMed;


    String medUid;
    double price;
    public Medecine(){

    }
    public Medecine(Chemist chemist,String scientificName, String genericName, String countryMade, String currency, String details, double price) {
        this.name = chemist.name;
        this.phone = chemist.phone;
        this.email = chemist.email;
        this.country = chemist.country;
        this.town = chemist.town;
        this.area = chemist.area;
        this.adress = chemist.adress;
        this.areaCode = chemist.areaCode;
        this.uidDb = chemist.uidDb;
        this.scientificName = scientificName;
        this.genericName = genericName;
        this.countryMade = countryMade;
        this.currency = currency;
        this.detailsMed = details;
        this.price = price;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getCountryMade() {
        return countryMade;
    }

    public void setCountryMade(String countryMade) {
        this.countryMade = countryMade;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDetailsMed() {
        return detailsMed;
    }

    public void setDetailsMed(String details) {
        this.detailsMed = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMedUid() {
        return medUid;
    }

    public void setMedUid(String medUid) {
        this.medUid = medUid;
    }



}
