package com.example.connect_odoo_mobile.contact;

public class Contact {
    private int id;
    private Object name;
    private Object company_name;
    private Object email;
    private Object image_128;

    public Contact() {
    }

    public Contact(int id, Object name, Object company_name, Object email, Object image_128) {
        this.id = id;
        this.name = name;
        this.company_name = company_name;
        this.email = email;
        this.image_128 = image_128;
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

    public Object getImage_128() {
        return image_128;
    }

    public void setImage_128(Object image_128) {
        this.image_128 = image_128;
    }

}
