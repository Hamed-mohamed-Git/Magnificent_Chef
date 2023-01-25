package com.example.magnificentchef.model.local.plan_meal;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PlanMeal {
    @NonNull
    @ColumnInfo(name = "MEAL_ID")
    @PrimaryKey private String meal_id;
    @ColumnInfo(name = "NAME")
    private String name;
    @ColumnInfo(name = "AREA")
    private String area;
    @ColumnInfo(name = "CATEGORY")
    private String category;
    @ColumnInfo(name = "DIRECTIONS")
    private String directions;
    @ColumnInfo(name = "VIDEO_URL")
    private String videoUrl;
    @ColumnInfo(name = "IMAGE")
    private String image;
    @ColumnInfo(name = "INGREDIENT")
    private String recipe;
    @ColumnInfo(name = "MEASURE")
    private String measure;
    @ColumnInfo(name = "Date")
    private String date;

    @NonNull
    public String getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(@NonNull String meal_id) {
        this.meal_id = meal_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}

