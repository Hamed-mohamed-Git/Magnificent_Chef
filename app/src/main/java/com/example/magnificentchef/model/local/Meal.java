package com.example.magnificentchef.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="Meal")
public class Meal {
    @PrimaryKey(autoGenerate = true)
    public String meal_Id;

    @ColumnInfo(name = "name")
    public String meal_name;

    @ColumnInfo(name = "area")
    public String Area;

    @ColumnInfo(name = "instruction")
    public String Instruction;

    @ColumnInfo(name = "image")
    public String Image;

    @ColumnInfo(name = "Category")
    public String Category;


    public Meal(String meal_Id, String meal_name, String area, String instruction, String image, String category) {
        this.meal_Id = meal_Id;
        this.meal_name = meal_name;
        Area = area;
        Instruction = instruction;
        Image = image;
        Category = category;
    }

    public Meal() {
    }

    public String getMeal_Id() {
        return meal_Id;
    }

    public void setMeal_Id(String meal_Id) {
        this.meal_Id = meal_Id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getInstruction() {
        return Instruction;
    }

    public void setInstruction(String instruction) {
        Instruction = instruction;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
