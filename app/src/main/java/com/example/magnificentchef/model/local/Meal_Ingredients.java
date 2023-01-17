package com.example.magnificentchef.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName ="MealIngredients")
public class Meal_Ingredients {
    //@ForeignKey(parentColumns = {""})
    public String id;

    @ColumnInfo(name = "name")
    @NonNull
    public String title;

}
