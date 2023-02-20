package com.example.magnificentchef.view.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.example.magnificentchef.R;
import com.example.magnificentchef.databinding.OnboardingCardBinding;

public class OnBoardingAdapter extends PagerAdapter {
    Context context;
       private OnBoardingInterface onBoardingInterface;
    private OnboardingCardBinding onboardingCardBinding;

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
        onboardingCardBinding= DataBindingUtil.inflate((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE),
                R.layout.onboarding_card,container,false);
       onboardingCardBinding.setAction(onBoardingInterface);
        switch (position) {
            case 0:
                onboardingCardBinding.setOnBoardingInformation(new BoardingInformation(R.drawable.selected,
                        R.drawable.unselected,
                        R.drawable.unselected,
                        R.drawable.onboard,
                        "The key to a good meal is simplicity and the right seasoning",
                        0));
                         break;
            case 1:
                onboardingCardBinding.setOnBoardingInformation(new BoardingInformation(R.drawable.unselected,
                        R.drawable.selected,
                        R.drawable.unselected,
                        R.drawable.story_bording,
                        "A recipe is a story that ends with a good meal",
                        1));
                        break;
            case 2:
                onboardingCardBinding.setOnBoardingInformation(new BoardingInformation(R.drawable.unselected,
                        R.drawable.unselected,
                        R.drawable.selected,
                        R.drawable.meet,
                        "The secret ingredient to every meal is love",
                        2));
                onboardingCardBinding.skip.setVisibility(View.GONE);
                onboardingCardBinding.nextBtn.setVisibility(View.GONE);
                onboardingCardBinding.circle1.setVisibility(View.GONE);
                onboardingCardBinding.circle2.setVisibility(View.GONE);
                onboardingCardBinding.circle3.setVisibility(View.GONE);
                onboardingCardBinding.getStartedOnboarding.setVisibility(View.VISIBLE);


                break;

        }
        /*skip.setOnClickListener(new View.OnClickListener() {
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
*/
        container.addView(onboardingCardBinding.getRoot());
        return onboardingCardBinding.getRoot();

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
