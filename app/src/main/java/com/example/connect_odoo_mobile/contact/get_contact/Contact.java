package com.example.connect_odoo_mobile.contact.get_contact;

public class Contact {
    private int id;
    private String name;
    private String company_name;
    private String email;
    private String image;

    public Contact() {
    }

    public Contact(int id, String name, String company_name, String email, String image) {
        this.id = id;
        this.name = name;
        this.company_name = company_name;
        this.email = email;
        this.image = image;
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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
