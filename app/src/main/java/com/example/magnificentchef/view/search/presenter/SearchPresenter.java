package com.example.magnificentchef.view.search.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.example.magnificentchef.model.remote.IngredientNetworkDelegate;
import com.example.magnificentchef.model.remote.Repository;

import java.util.Objects;

public class SearchPresenter {
    private final Repository repository;
    private final IngredientNetworkDelegate ingredientNetworkDelegate;
    private final SearchInterface searchInterface;
    private final Context context;

    public SearchPresenter(Repository repository,
                           IngredientNetworkDelegate ingredientNetworkDelegate,
                           SearchInterface searchInterface,
                           Context context) {
        this.repository = repository;
        this.ingredientNetworkDelegate = ingredientNetworkDelegate;
        this.context = context;
        this.searchInterface = searchInterface;
    }

    public void getIngredientData(){
        repository.getIngredientsDetail(ingredientNetworkDelegate);
    }
    public void checkConnection(){
        context.getSystemService(ConnectivityManager.class).registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                new Handler(Looper.getMainLooper()).post(searchInterface::onInternetAvailable);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                new Handler(Looper.getMainLooper()).post(searchInterface::onInternetLost);
            }
            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
            }
        });
        if (context.getSystemService(ConnectivityManager.class).getActiveNetworkInfo()!=null && !context.getSystemService(ConnectivityManager.class).getActiveNetworkInfo().isConnected())
            searchInterface.onInternetAvailable();
        else {
            searchInterface.onInternetLost();
        }
    }
}
