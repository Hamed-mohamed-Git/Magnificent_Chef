package com.example.magnificentchef.view.search.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.view.search.model.Ingredients;

public class SearchAdapter extends ArrayAdapter<Ingredients> {

    private Context contexts;
    private Ingredients[] ingredients;

    public SearchAdapter(@NonNull Context context, @NonNull Ingredients[] ingredient) {
        super(context, R.layout.row_search,R.id.label,ingredient);
        contexts =context;
        ingredients =ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) contexts.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_search, parent, false);
            viewHolder= new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.getLabel().setText(ingredients[position].getLabel());
        viewHolder.getImage().setImageResource(ingredients[position].getImage());
        return view;
    }
    private class ViewHolder{

        View view;
        TextView label;
        ImageView image;
        public ViewHolder(View convertView){
            view=convertView;
        }

        public TextView getLabel(){
            label=view.findViewById(R.id.label);
            return label;
        }


        public ImageView getImage(){
            image=view.findViewById(R.id.circle);
            return image;
        }

    }
}
