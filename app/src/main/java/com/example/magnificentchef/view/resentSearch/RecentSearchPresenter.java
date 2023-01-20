package com.example.magnificentchef.view.resentSearch;

import com.example.magnificentchef.network.Repository;

public class RecentSearchPresenter {
    private Repository repository;

    public RecentSearchPresenter(Repository repository) {
        this.repository = repository;
    }
    public void getMealsByKey(String keyLetter){
        repository.getMealsByKey(keyLetter);
    }

}
