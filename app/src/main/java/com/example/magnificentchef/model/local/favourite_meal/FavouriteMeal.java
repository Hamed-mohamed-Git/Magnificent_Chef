package com.example.magnificentchef.model.local.favourite_meal;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class FavouriteMeal {
    @NonNull
    @ColumnInfo(name = "MEAL_ID")
    @PrimaryKey public String meal_id;
    @ColumnInfo(name = "NAME")
    public String name;
    @ColumnInfo(name = "AREA")
    public String area;
    @ColumnInfo(name = "CATEGORY")
    public String category;
    @ColumnInfo(name = "DIRECTIONS")
    public String directions;
    @ColumnInfo(name = "VIDEO_URL")
    public String videoUrl;
    @ColumnInfo(name = "IMAGE")
    public String image;
    @ColumnInfo(name = "RECIPE")
    public String recipe;

}
