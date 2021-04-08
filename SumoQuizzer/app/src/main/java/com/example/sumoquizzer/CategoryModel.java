package com.example.sumoquizzer;

public class CategoryModel {


    private String url, name;
    private int sets;

    public CategoryModel() {
        //for firebase
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryModel(String url, int sets, String name) {
        this.url = url;
        this.sets = sets;
        this.name = name;
    }


}
