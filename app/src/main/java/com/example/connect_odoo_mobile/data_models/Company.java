package com.example.connect_odoo_mobile.data_models;

public class Company {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String mobile;
    private String street;
    private String street2;
    private String city;
    private String country_code;

    public Company() {
    }

    public Company(int id, String name, String phone, String email, String mobile, String street, String street2, String city, String country_code) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.mobile = mobile;
        this.street = street;
        this.street2 = street2;
        this.city = city;
        this.country_code = country_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}
