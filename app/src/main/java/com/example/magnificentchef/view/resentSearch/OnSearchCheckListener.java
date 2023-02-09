package com.example.magnificentchef.view.resentSearch;

import com.example.magnificentchef.view.common.ConnectionState;

public interface OnSearchCheckListener extends ConnectionState {
    void onSearchCategoryListener(String key);
    void onSearchListener(String key);
    void onSearchAreaListener(String key);
    void onSearchIngredientListener(String key);
}
