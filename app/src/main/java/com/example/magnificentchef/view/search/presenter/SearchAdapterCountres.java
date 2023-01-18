package com.example.magnificentchef.view.search.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.view.search.model.Country;

import java.util.List;

public class SearchAdapterCountres extends RecyclerView.Adapter<SearchAdapterCountres.ViewHolder> {
    public SearchAdapterCountres(List<Country> countries) {
        this.countries = countries;
    }

    private List<Country>countries;
    Country country;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from( parent.getContext()).inflate(R.layout.row_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        country =countries.get(position);
        holder.image.setImageResource(countries.get(position).getImage());
        holder.label.setText(countries.get(position).getLabel());
        //Glide.with(holder.image.getContext()).load(ingredient.getIngredientImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View view;
        TextView label;
        ImageView image;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
          label=itemView.findViewById(R.id.label);
          image=itemView.findViewById(R.id.circle);


       }

        @Override
        public void onClick(View view) {

        }
    }




   }

