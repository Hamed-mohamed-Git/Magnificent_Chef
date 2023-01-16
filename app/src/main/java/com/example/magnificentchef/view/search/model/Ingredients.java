package com.example.magnificentchef.view.search.model;

public class Ingredients {
    private String label;
    private int image;

    public Ingredients(String label, int image) {
        this.label = label;
        this.image = image;
    }

    public Ingredients() {
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
