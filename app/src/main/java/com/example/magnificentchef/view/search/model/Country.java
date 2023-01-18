package com.example.magnificentchef.view.search.model;

public class Country {
    String label;
    int image;

    public Country(String label, int image) {
        this.label = label;
        this.image = image;
    }

    public Country() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
