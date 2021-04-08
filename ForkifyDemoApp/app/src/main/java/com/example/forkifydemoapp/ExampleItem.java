package com.example.forkifydemoapp;

public class ExampleItem {

    private String imageUrl,name,mail,job;
    private int id;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public ExampleItem(String imageUrl, String name, int id, String mail) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.id = id;
        this.mail = mail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
