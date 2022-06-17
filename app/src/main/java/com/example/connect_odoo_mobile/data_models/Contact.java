package com.example.connect_odoo_mobile.data_models;

public class Contact {
    private int id;
    private Object name;
    private Object company_name;
    private Object email;

    public Contact() {
    }

    public Contact(int id, Object name, Object company_name, Object email) {
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

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getCompany() {
        return company_name;
    }

    public void setCompany(Object company_name) {
        this.company_name = company_name;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }
}
