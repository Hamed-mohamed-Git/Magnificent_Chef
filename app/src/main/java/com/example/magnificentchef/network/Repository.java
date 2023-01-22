package com.example.magnificentchef.network;

import com.example.magnificentchef.network.model.MealsItem;
import com.example.magnificentchef.network.model.RandomMealResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {
    private MealApiService mealApiService;
    private final NetworkDelegate<MealsItem> networkDelegate;
    private final Remote remote;
    private final List<Single<RandomMealResponse>> randomMealResponseSingleList;
    private final List<MealsItem> mealList;

    public Repository(NetworkDelegate<MealsItem> networkDelegate, Remote remote) {
        this.networkDelegate = networkDelegate;
        this.remote = remote;
        mealApiService = remote.getRandomMealsApiService();
        randomMealResponseSingleList = new ArrayList<>();
        mealList = new ArrayList<>();
    }

    public void getRandomMeal(int mealCount){
        setSingleObservableToList(mealCount);
        Observable
                .fromIterable(randomMealResponseSingleList)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Single<RandomMealResponse>, ObservableSource<RandomMealResponse>>() {
                    @Override
                    public ObservableSource<RandomMealResponse> apply(Single<RandomMealResponse> responseSingle) throws Throwable {
                        return responseSingle.toObservable();
                    }
                })
                .distinct()
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<RandomMealResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<RandomMealResponse> responses) {
                        setMealItemsToMealList(responses);
                        networkDelegate.onSuccessResult(mealList);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        networkDelegate.onFailureResult(e.getMessage());
                    }
                });

    }

    private void setSingleObservableToList(int mealCount){
        for (int loop = 0;loop < mealCount;loop++){
            randomMealResponseSingleList.add(mealApiService.getMeal());
        }
    }
    private void setMealItemsToMealList(List<RandomMealResponse> RandomMealResponses){
        for (RandomMealResponse randomMealResponse : RandomMealResponses){
            mealList.add(randomMealResponse.getMeals().get(0));
        }
    }

    public void getMealsByKey(String letter){
        mealApiService.getSearchLetter(letter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers .mainThread())
                .subscribe(new SingleObserver<RandomMealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RandomMealResponse randomMealResponse) {
                        networkDelegate.onSuccessResult(randomMealResponse.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        networkDelegate.onFailureResult(e.getMessage());

                    }
                });
    }
    public void getMealsByIngredient(String name){
        mealApiService.getSearchIngredients(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RandomMealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RandomMealResponse randomMealResponse) {
                           networkDelegate.onSuccessResult(randomMealResponse.getMeals());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        networkDelegate.onFailureResult(e.getMessage());

                    }
                });
    }
}
