package com.example.magnificentchef.view.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.magnificentchef.R;

public class OnBoardingAdapter extends PagerAdapter {
    Context context;
    private ImageView photo,circle1,circle2,circle3;
    private TextView title,description,skip;
    private Button next,started;
    private ViewPager viewPager;
    private OnBoardingInterface onBoardingInterface;

    public OnBoardingAdapter(Context context, OnBoardingInterface onBoardingInterface) {
        this.context = context;
        this.onBoardingInterface = onBoardingInterface;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.onboarding_card,container,false);
        photo =view.findViewById(R.id.photo);
        circle1=view.findViewById(R.id.circle1);
        circle2=view.findViewById(R.id.circle2);
        circle3=view.findViewById(R.id.circle3);
        title=view.findViewById(R.id.onboarding_title);
        description=view.findViewById(R.id.onboarding_description);
        next=view.findViewById(R.id.next_btn);
        skip=view.findViewById(R.id.skip);
        started=view.findViewById(R.id.get_Started_onboarding);

        switch (position) {
            case 0:
                photo.setImageResource(R.drawable.onboard);
                circle1.setImageResource(R.drawable.selected);
                circle2.setImageResource(R.drawable.unselected);
                circle3.setImageResource(R.drawable.unselected);
                title.setText("Magnifient Chef");
                description.setText("The key to a good meal is simplicity and the right seasoning");
                started.setVisibility(View.GONE);

                break;
            case 1:
                photo.setImageResource(R.drawable.story_bording);
                circle1.setImageResource(R.drawable.unselected);
                circle2.setImageResource(R.drawable.selected);
                circle3.setImageResource(R.drawable.unselected);
                title.setText("Magnifient Chef");
                description.setText("A recipe is a story that ends with a good meal");
                started.setVisibility(View.GONE);
                break;
            case 2:
                photo.setImageResource(R.drawable.meet);
                circle1.setImageResource(R.drawable.unselected);
                circle2.setImageResource(R.drawable.unselected);
                circle3.setImageResource(R.drawable.selected);
                title.setText("Magnifient Chef");
                description.setText("The secret ingredient to every meal is love");
                skip.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                circle1.setVisibility(View.GONE);
                circle2.setVisibility(View.GONE);
                circle3.setVisibility(View.GONE);
                break;

        }
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBoardingInterface.skip();

            }
        });
       next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBoardingInterface.next(position);

            }
        });
        started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBoardingInterface.started();
            }
        });

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
