package com.example.magnificentchef.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFiles {
    public static String saveImage(Context applicationContext, Bitmap bitmap, String fileName) throws IOException {
        File applicationFileDirectory = applicationContext.getDir("Meal_Images",Context.MODE_PRIVATE);
        File mealImagesFile = new File(applicationFileDirectory,fileName + ".jpeg");
        if (!mealImagesFile.exists()){
            try(FileOutputStream fileOutputStream = new FileOutputStream(mealImagesFile)){
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
            }
        }
        return mealImagesFile.getAbsolutePath();
    }

    public static String saveImage(Context applicationContext,String url, String fileName) {
        File applicationFileDirectory = applicationContext.getDir("Meal_Images",Context.MODE_PRIVATE);
        File mealImagesFile = new File(applicationFileDirectory,fileName + ".jpeg");

        Glide.with(applicationContext).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (!mealImagesFile.exists()){
                    try(FileOutputStream fileOutputStream = new FileOutputStream(mealImagesFile)){
                        resource.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                        fileOutputStream.flush();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });

        return mealImagesFile.getAbsolutePath();
    }
}
