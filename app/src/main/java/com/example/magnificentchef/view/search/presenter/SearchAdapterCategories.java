package com.example.magnificentchef.view.search.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.view.search.model.Custom;

import java.util.List;

public class SearchAdapterCategories extends RecyclerView.Adapter<SearchAdapterCategories.ViewHolder>{


    public SearchAdapterCategories(List <Custom> categories) {
        this.categories = categories;
    }

        private List<Custom> categories;
        Custom category;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from( parent.getContext()).inflate(R.layout.sort_card,parent,false);
        return new SearchAdapterCategories.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        category =categories.get(position);
        holder.image.setImageResource(categories.get(position).getImage());
        holder.label.setText(categories.get(position).getLabel());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }



        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            View view;
            TextView label;
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                label = itemView.findViewById(R.id.Category_TV);
                image = itemView.findViewById(R.id.card_Image);


            }

            @Override
            public void onClick(View view) {

            }
        }
    }





