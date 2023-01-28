package com.example.magnificentchef.model.remote;

import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.model.remote.model.RandomMealResponse;
import com.example.magnificentchef.model.remote.model.ingredient_model.IngredientResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
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
        mealApiService = remote.getMealsApiService();
        randomMealResponseSingleList = new ArrayList<>();
        mealList = new ArrayList<>();
    }

    public void getRandomMeal(int mealCount){
        final int[] count = {0};

        setSingleObservableToList(mealCount);
        mealApiService.getMeal()
                .subscribeOn(Schedulers.io())
                .repeatUntil(()-> count[0] == mealCount)
                .distinct()
                .doOnNext(t -> count[0]++)
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

    public void getMealsByArea(String area){
        mealApiService.getSearchArea(area)
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
    public void getMealById (String id,String key,MealNetworkDelegate mealNetworkDelegate){

        mealApiService.getMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RandomMealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RandomMealResponse randomMealResponse) {
                        mealNetworkDelegate.onSuccessMealResult(randomMealResponse.getMeals(),key);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mealNetworkDelegate.onFailureResult(e.getMessage());

                    }
                });
    }
    public void getMealByCategory(String category){
        remote.getMealsApiService()
                .getMealsByCategory(category)
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

    public void getIngredientsDetail(IngredientNetworkDelegate ingredientNetworkDelegate){
        mealApiService.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<IngredientResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull IngredientResponse ingredientResponse) {
                        ingredientNetworkDelegate.onSuccessIngredientsResult(ingredientResponse.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ingredientNetworkDelegate.onFailIngredientsResult(e.getMessage());

                    }
                });
    }

}
