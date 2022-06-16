package com.example.connect_odoo_mobile.data_models;

public class Contact {
    private int id;
    private String name;
    private String company_name;
    private String email;

    public Contact() {
    }

    public Contact(int id, String name, String company_name, String email) {
        this.id = id;
        this.name = name;
        this.company_name = company_name;
        this.email = email;
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

    public String getCompany() {
        return company_name;
    }

    public void setCompany(String company_name) {
        this.company_name = company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
