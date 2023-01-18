package com.example.magnificentchef.view.home.presenter;

import com.example.magnificentchef.network.Repository;

public class HomePresenter {
    private Repository repository;

    public HomePresenter(Repository repository) {
        this.repository = repository;
    }
    public void getRandomMeal(int mealCount){
        repository.getRandomMeal(mealCount);
    }
}