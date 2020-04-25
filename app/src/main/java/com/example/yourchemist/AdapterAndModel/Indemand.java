package com.example.yourchemist.AdapterAndModel;

public class Indemand {

    public Indemand(){}

    String drugName;
    String townName;
    String countryName;



    String docId;
    int numberRequest;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getNumberRequest() {
        return numberRequest;
    }

    public void setNumberRequest(int numberRequest) {
        this.numberRequest = numberRequest;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Indemand(String drugName, String townName, String countryName, int numberRequest) {
        this.drugName = drugName;
        this.townName = townName;
        this.countryName = countryName;
        this.numberRequest = numberRequest;
    }
}
