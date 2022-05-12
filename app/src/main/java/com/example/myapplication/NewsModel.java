package com.example.myapplication;

public class NewsModel {
    String name,description;

    public NewsModel(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }


}
