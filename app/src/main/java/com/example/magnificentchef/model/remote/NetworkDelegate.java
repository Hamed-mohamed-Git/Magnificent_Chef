package com.example.magnificentchef.model.remote;

import java.util.List;

public interface NetworkDelegate<T> {
    void onSuccessResult(List<T> itemList);
    void onFailureResult(String message);
}
