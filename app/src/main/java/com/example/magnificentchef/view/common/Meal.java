package com.example.magnificentchef.view.common;

public class Meal {
    private final String image;
    private final String category;
    private final String name;

    public Meal(String image, String category, String name) {
        this.image = image;
        this.category = category;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
}
