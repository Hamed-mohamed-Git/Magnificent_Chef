package com.example.magnificentchef.view.search.presenter;

import com.example.magnificentchef.model.remote.IngredientNetworkDelegate;
import com.example.magnificentchef.model.remote.Repository;

public class SearchPresenter {
    private Repository repository;
    private IngredientNetworkDelegate ingredientNetworkDelegate;

    public SearchPresenter(Repository repository, IngredientNetworkDelegate ingredientNetworkDelegate) {
        this.repository = repository;
        this.ingredientNetworkDelegate = ingredientNetworkDelegate;
    }

    public void getIngredientData(){
        repository.getIngredientsDetail(ingredientNetworkDelegate);
    }
}
