package com.example.connect_odoo_mobile.company;

public class Company {
    private int id;
    private Object name;
    private Object phone;
    private Object email;
    private Object mobile;
    private Object street;
    private Object street2;
    private Object city;
    private Object country_code;
    private Object logo;

    public Company() {
    }

    public Company(int id, Object name, Object phone, Object email, Object mobile, Object street, Object street2, Object city, Object country_code, Object logo) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.mobile = mobile;
        this.street = street;
        this.street2 = street2;
        this.city = city;
        this.country_code = country_code;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getStreet() {
        return street;
    }

    public void setStreet(Object street) {
        this.street = street;
    }

    public Object getStreet2() {
        return street2;
    }

    public void setStreet2(Object street2) {
        this.street2 = street2;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getCountry_code() {
        return country_code;
    }

    public void setCountry_code(Object country_code) {
        this.country_code = country_code;
    }

    public Object getLogo() {
        return logo;
    }

    public void setLogo(Object logo) {
        this.logo = logo;
    }
}
