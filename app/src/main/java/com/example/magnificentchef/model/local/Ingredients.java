package com.example.magnificentchef.model.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="ingredients")
public class Ingredients {
    @PrimaryKey(autoGenerate = true)
    public String ingredients_Id;

    @ColumnInfo(name = "name")
    public String ingredients_name;

    public Ingredients(String ingredients_Id, String ingredients_name) {
        this.ingredients_Id = ingredients_Id;
        this.ingredients_name = ingredients_name;
    }

    public Ingredients() {
    }

    public String getIngredients_Id() {
        return ingredients_Id;
    }

    public void setIngredients_Id(String ingredients_Id) {
        this.ingredients_Id = ingredients_Id;
    }

    public String getIngredients_name() {
        return ingredients_name;
    }

    public void setIngredients_name(String ingredients_name) {
        this.ingredients_name = ingredients_name;
    }
}
