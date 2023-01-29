package com.example.magnificentchef.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;

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
}
