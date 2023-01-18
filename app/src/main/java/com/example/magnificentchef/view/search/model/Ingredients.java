package com.example.magnificentchef.view.search.model;

import com.google.gson.annotations.SerializedName;

public class Ingredients {
    @SerializedName("strDescription")
    private String strDescription;

    @SerializedName("strIngredient")
    private String strIngredient;

    @SerializedName("strType")
    private String strType;

    @SerializedName("idIngredient")
    private String idIngredient;


    public final static String urlBase="https://www.themealdb.com/images/ingredients/";
    public final static String urlTail="-Small.png";


    public Ingredients(String strDescription, String strIngredient, String strType, String idIngredient) {
        this.strDescription = strDescription;
        this.strIngredient = strIngredient;
        this.strType = strType;
        this.idIngredient = idIngredient;
    }

    public Ingredients() {
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }
    public String getIngredientImage(){
        return urlBase+strIngredient+urlTail;
    }
}
