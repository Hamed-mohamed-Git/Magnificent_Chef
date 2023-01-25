package com.example.magnificentchef.view.meal_details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.view.common.Ingredient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MealDetailsFragment extends Fragment {

    private MealsItem mealsItem;
    private TextView categoryTextView, mealNameTextView, countryTextView, directionTextView;
    private ImageView mealImageView;
    private Button watchButton;
    private RecyclerView ingredientsRecyclerView;
    private Intent intent;
    private List<Ingredient> ingredientsList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealsItem = MealDetailsFragmentArgs
                .fromBundle(getArguments())
                .getMealItem();
        intent  = new Intent(Intent.ACTION_VIEW);
        ingredientsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setMealItemIntoViews(mealsItem);
        try {
            getFields();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        ingredientsRecyclerView.setAdapter(new IngredientsAdapter(ingredientsList));
    }

    private void initView(View view){
        categoryTextView = view.findViewById(R.id.categoryTextView);
        mealNameTextView = view.findViewById(R.id.mealNameTextView);
        countryTextView = view.findViewById(R.id.countryTextView);
        directionTextView = view.findViewById(R.id.directionTextView);
        watchButton =  view.findViewById(R.id.watchButton);
        mealImageView =  view.findViewById(R.id.mealImage);
        ingredientsRecyclerView =  view.findViewById(R.id.ingredientsRecyclerView);
    }

    private void setMealItemIntoViews(MealsItem mealsItem){
        categoryTextView.setText(mealsItem.getStrCategory());
        Glide.with(requireContext()).load(mealsItem.getStrMealThumb()).into(mealImageView);
        mealNameTextView.setText(mealsItem.getStrMeal());
        countryTextView.setText(mealsItem.getStrArea());
        directionTextView.setText(mealsItem.getStrInstructions());
        watchButton.setOnClickListener((v)->{
            intent.setData(Uri.parse(mealsItem.getStrYoutube()));
            intent.setPackage("com.google.android.youtube");
            startActivity(intent);
        });
    }


    private void getFields() throws IllegalAccessException, NoSuchFieldException {
        Field[] fieldList =  mealsItem.getClass().getDeclaredFields();
        for (Field field : fieldList) {
            if (field.getName().startsWith("strIngredient")) {
                field.setAccessible(true);
                if (field.get(mealsItem) != null && !field.get(mealsItem).toString().isEmpty()) {
                    Field measureField = mealsItem.getClass().getDeclaredField("strMeasure" + field.getName().substring(13, field.getName().length()));
                    measureField.setAccessible(true);
                    ingredientsList.add(new Ingredient((String) field.get(mealsItem),
                            (String) measureField.get(mealsItem)));
                }
            }

        }

    }
}