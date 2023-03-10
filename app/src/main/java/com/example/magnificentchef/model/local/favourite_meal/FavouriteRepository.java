package com.example.magnificentchef.model.local.favourite_meal;

import android.content.Context;

import com.example.magnificentchef.model.local.Local;
import com.example.magnificentchef.utils.SaveFiles;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouriteRepository {
    private final Local local;
    public  final FavouriteMealDelegate favouriteMealDelegate;

    public FavouriteRepository(Local local, FavouriteMealDelegate favouriteDelegates) {
        this.local = local;
        this.favouriteMealDelegate = favouriteDelegates;
    }

    public void insertFavouriteMeal(FavouriteMeal favouriteMeal){
        local.favouriteDAO().insert(favouriteMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        favouriteMealDelegate.onSubscribe();
                    }

                    @Override
                    public void onComplete() {
                        favouriteMealDelegate.onComplete();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        favouriteMealDelegate.onError(e.getMessage());
                    }
                });
    }
    public void deleteFavouriteMeal(FavouriteMeal favouriteMeal){
        local.favouriteDAO().delete(favouriteMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        favouriteMealDelegate.onSubscribe();
                    }

                    @Override
                    public void onComplete() {
                        favouriteMealDelegate.onComplete();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        favouriteMealDelegate.onError(e.getMessage());
                    }
                });
    }
    public void FavouriteMeals(){
        local.favouriteDAO().getFavouriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<FavouriteMeal>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        favouriteMealDelegate.onSubscribe();
                    }

                    @Override
                    public void onSuccess(@NonNull List<FavouriteMeal> favouriteMeals) {
                        favouriteMealDelegate.onSuccess(favouriteMeals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        favouriteMealDelegate.onError(e.getMessage());
                    }
                });
    }
    public void clearFavouriteMealsTableData(){
        local.favouriteDAO().delete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        favouriteMealDelegate.onComplete();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        favouriteMealDelegate.onError(e.getMessage());
                    }
                });
    }
    public void addFavouriteMealList(List<FavouriteMeal> favouriteMealList, Context context){
        for (FavouriteMeal favouriteMeal : favouriteMealList){
            favouriteMeal.setImage(SaveFiles.saveImage(context,favouriteMeal.getImage(),favouriteMeal.getName()));
            insertFavouriteMeal(favouriteMeal);
        }
    }


    public void getFavouriteMealByID(String id,FavouriteDelegate favouriteDelegate){
        local.favouriteDAO().getFavouriteMealByID(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FavouriteMeal>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull FavouriteMeal favouriteMeal) {
                        favouriteDelegate.onFavouriteMeal(favouriteMeal);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
