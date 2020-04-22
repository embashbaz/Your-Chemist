package com.example.yourchemist.AdapterAndModel;

public class Chemist {


    String name;
    String license;
    String document;
    String phone;
    String email;
    String country;
    String town;
    String area;
    String adress;
    String areaCode;
    String details;
    String uidDb;

    public Chemist(){

    }
    public Chemist(String name, String license, String document, String phone, String email,
                    String country, String town, String area, String adress, String areaCode, String details, String uid) {
        this.name = name;
        this.license = license;
        this.document = document;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.town = town;
        this.area = area;
        this.adress = adress;
        this.areaCode = areaCode;
        this.details = details;
        this.uidDb = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUidDb() {
        return uidDb;
    }

}
