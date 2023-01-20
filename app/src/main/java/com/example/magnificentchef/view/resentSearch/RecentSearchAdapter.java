package com.example.magnificentchef.view.resentSearch;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.network.model.MealsItem;

import java.util.ArrayList;
import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.Holder>{
private List<MealsItem> search;

    public RecentSearchAdapter(List<MealsItem> search) {
        this.search = search;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        MealsItem mealsItem = search.get(position);
        holder.search_item_tv.setText(mealsItem.getStrMeal());

    }
    @Override
    public int getItemCount() {
        return search.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

    public TextView search_item_tv;

    public Holder(@NonNull View itemView) {
        super(itemView);
        search_item_tv = itemView.findViewById(R.id.search_tv);
    }

}
    @SuppressLint("NotifyDataSetChanged")
    public void setMealItemList(List<MealsItem> mealItemList){
        search=mealItemList;
        notifyDataSetChanged();
    }
}



