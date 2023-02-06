package com.example.magnificentchef.view.home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class InspirationTransFormer implements ViewPager2.PageTransformer {

    private final int offscreenPageLimit;

    public static final Float DEFAULT_TRANSLATION_X = .0f;
    public static final Float DEFAULT_TRANSLATION_FACTOR = 1.2f;

    public static final Float SCALE_FACTOR = .14f;
    public static final Float DEFAULT_SCALE = 1f;

    public static final Float ALPHA_FACTOR = .3f;
    public static final Float DEFAULT_ALPHA = 1f;

    public InspirationTransFormer(int offscreenPageLimit) {
        this.offscreenPageLimit = offscreenPageLimit;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setElevation(-Math.abs(position));

        if (position <= 0f){
            page.setTranslationX(DEFAULT_TRANSLATION_X);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
            page.setAlpha(DEFAULT_ALPHA );
        }
        else if (position <= offscreenPageLimit - 1){
            page.setScaleX(-SCALE_FACTOR * position + DEFAULT_SCALE);
            page.setScaleY(-SCALE_FACTOR * position + DEFAULT_SCALE);
            page.setAlpha(-ALPHA_FACTOR * position + DEFAULT_ALPHA);
            page.setTranslationX(-(page.getWidth() + 20)*position);
        }else{
            page.setTranslationX(DEFAULT_TRANSLATION_X);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
            page.setAlpha(DEFAULT_ALPHA + position);
        }

    }
}
