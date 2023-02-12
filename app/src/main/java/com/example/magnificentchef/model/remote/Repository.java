package com.example.magnificentchef.model.remote;

import android.os.Handler;
import android.os.Looper;

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
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

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

    public void getRandomMeal(int mealCount,RandomMealDelegate randomMealDelegate) {
        final int[] count = {0};
        mealApiService.getMeal()
                .subscribeOn(Schedulers.io())
                .repeatUntil(() -> count[0] == mealCount)
                .distinct()
                .doOnNext(ignored -> count[0]++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<RandomMealResponse>() {
                    int count=1;
                    @Override
                    protected void onStart() {
                        //request(Long.MAX_VALUE);
                        request(count++);
                    }

                    @Override
                    public void onNext(RandomMealResponse randomMealResponses) {
                        randomMealDelegate.onSuccessResult(randomMealResponses.getMeals().get(0));
                        request(count++);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        cancel();
                    }
                });
    }

    private void setMealItemsToMealList(List<RandomMealResponse> RandomMealResponses) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (RandomMealResponse randomMealResponse : RandomMealResponses) {
                    mealList.add(randomMealResponse.getMeals().get(0));
                }
                networkDelegate.onSuccessResult(mealList);
            }
        });
    }

    public void getMealsByKey(String letter) {
        mealApiService.getSearchLetter(letter)
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

    public void getMealsByIngredient(String name) {
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

    public void getMealsByArea(String area) {
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

    public void getMealById(String id, String key, MealNetworkDelegate mealNetworkDelegate) {

        mealApiService.getMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RandomMealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RandomMealResponse randomMealResponse) {
                        mealNetworkDelegate.onSuccessMealResult(randomMealResponse.getMeals(), key);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mealNetworkDelegate.onFailureResult(e.getMessage());

                    }
                });
    }
    public void getMealById(String id) {

        mealApiService.getMealById(id)
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

    public void getMealByCategory(String category) {
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

    public void getIngredientsDetail(IngredientNetworkDelegate ingredientNetworkDelegate) {
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
