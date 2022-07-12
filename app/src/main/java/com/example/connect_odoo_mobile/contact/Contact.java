package com.example.connect_odoo_mobile.contact;

public class Contact {
    private int id;
    private String name;
    private String email;
    private String image;
    private String company_name;
    private String company_type;

    private String street;
    private String street2;
    private String zip;

    private String country;
    private String website;
    private String phone;
    private String mobile;
    private String comment;

    public Contact() {
    }

    public Contact(int id, String name, String company_name, String email, String image) {
        this.id = id;
        this.name = name;
        this.company_name = company_name;
        this.email = email;
        this.image = image;
    }

    public Contact(String name, String email, String image, String company_name,
                   String street, String street2, String zip, String country,
                   String website, String phone, String mobile, String comment, String company_type) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.company_name = company_name;
        this.street = street;
        this.street2 = street2;
        this.zip = zip;
        this.country = country;
        this.website = website;
        this.phone = phone;
        this.mobile = mobile;
        this.comment = comment;
        this.company_type = company_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getName() {
        if (!name.equals("")) {
            return name;
        } else {
            return false;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCompany_name() {
        if (!company_name.equals("")) {
            return company_name;
        } else {
            return false;
        }
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Object getEmail() {
        if (!email.equals("")) {
            return email;
        } else {
            return false;
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getImage() {
        if (!image.equals("")) {
            return image;
        } else {
            return false;
        }
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getStreet() {
        if(!street.equals("")){
            return street;
        }
        else {
            return false;
        }
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Object getStreet2() {
        if(!street2.equals("")){
            return street2;
        }
        else {
            return false;
        }
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public Object getZip() {
        if(!zip.equals("")){
            return zip;
        }
        else {
            return false;
        }
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Object getCountry() {
        if(!country.equals("")){
            return country;
        }
        else {
            return false;
        }
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getWebsite() {
        if(!website.equals("")){
            return website;
        }
        else {
            return false;
        }
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Object getPhone() {
        if(!phone.equals("")){
            return phone;
        }
        else {
            return false;
        }
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getMobile() {
        if(!mobile.equals("")){
            return mobile;
        }
        else {
            return false;
        }
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getComment() {
        if(!comment.equals("")){
            return comment;
        }
        else {
            return false;
        }
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }
}
