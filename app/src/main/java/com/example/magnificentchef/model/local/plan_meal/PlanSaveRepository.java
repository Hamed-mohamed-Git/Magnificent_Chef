package com.example.magnificentchef.model.local.plan_meal;

import com.example.magnificentchef.model.local.Local;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanSaveRepository {
    private final Local local;
    public final SavePlanMealDelegate savePlanMealDelegate;
    public PlanSaveRepository(Local local, SavePlanMealDelegate databaseDelegate) {
        this.local = local;
        this.savePlanMealDelegate = databaseDelegate;
    }

    public void insertPlanMeal(PlanMeal planMeal){
        local.mealPlanDAO().insert(planMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        savePlanMealDelegate.onSubscribe();
                    }

                    @Override
                    public void onComplete() {
                        savePlanMealDelegate.onComplete();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        savePlanMealDelegate.onError(e.getMessage());
                    }
                });
    }

    public void deletePlanMeal(PlanMeal planMeal){
        local.mealPlanDAO().delete(planMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        savePlanMealDelegate.onSubscribe();
                    }

                    @Override
                    public void onComplete() {
                        savePlanMealDelegate.onComplete();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        savePlanMealDelegate.onError(e.getMessage());
                    }
                });
    }

    public void plannedMeals(){
        local.mealPlanDAO().getMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PlanMeal>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        savePlanMealDelegate.onSubscribe();
                    }

                    @Override
                    public void onSuccess(@NonNull List<PlanMeal> planMeals) {
                        savePlanMealDelegate.onSuccess(planMeals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        savePlanMealDelegate.onError(e.getMessage());
                    }
                });
    }
}
