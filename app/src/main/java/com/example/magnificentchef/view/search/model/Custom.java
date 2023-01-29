package com.example.magnificentchef.view.search.model;

public class Custom {
    String label;
    int image;

    public Custom(String label, int image) {
        this.label = label;
        this.image = image;
    }

    public Custom() {
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
