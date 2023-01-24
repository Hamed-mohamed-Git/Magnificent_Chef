package com.example.magnificentchef.view.resentSearch;

import com.example.magnificentchef.model.remote.Repository;

public class RecentSearchPresenter {
    private Repository repository;

    public RecentSearchPresenter(Repository repository) {
        this.repository = repository;
    }
    public void getMealsByKey(String keyLetter){
        repository.getMealsByKey(keyLetter);
    }
    public void getMealsByIngredient(String ingredient){
        repository.getMealsByIngredient(ingredient);
    }

}
