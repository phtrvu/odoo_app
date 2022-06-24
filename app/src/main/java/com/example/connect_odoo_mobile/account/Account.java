package com.example.connect_odoo_mobile.account;

public class Account {
    private String name;
    private String url;
    private String image;
    private String db;
    private String pass;
    private int uid;

    public Account() {
    }

    public Account(String name, String url, String image, String db, String pass, int uid) {
        this.name = name;
        this.url = url;
        this.image = image;
        this.db = db;
        this.pass = pass;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
