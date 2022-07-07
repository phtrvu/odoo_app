package com.example.connect_odoo_mobile.contact.contact_detail;

public class ContactDetail {
    private int id;
    private String name;
    private String company_name;
    private String email;
    private String image;
    private String phone;
    private String mobile;
    private String country;
    private String comment;
    private String website;

    public ContactDetail() {
    }

    public ContactDetail(int id, String name, String company_name, String email,
                         String image, String phone, String mobile, String country, String comment, String website) {
        this.id = id;
        this.name = name;
        this.company_name = company_name;
        this.email = email;
        this.image = image;
        this.phone = phone;
        this.mobile = mobile;
        this.country = country;
        this.comment = comment;
        this.website = website;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
