package com.nct.trenx.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nct.trenx.R;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static void loadExerciseThumb(@NonNull Context context, @NonNull ImageView imageView,
                                         String imageUrl) {
        loadExerciseThumb(context, imageView, imageUrl, R.color.card_dark);
    }

    public static void loadExerciseThumb(@NonNull Context context, @NonNull ImageView imageView,
                                         String imageUrl, @ColorRes int placeholderColor) {
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(placeholderColor)
                .into(imageView);
    }

    public static void loadExerciseThumb(@NonNull Fragment fragment, @NonNull ImageView imageView,
                                         String imageUrl, @ColorRes int placeholderColor) {
        Glide.with(fragment)
                .load(imageUrl)
                .centerCrop()
                .placeholder(placeholderColor)
                .into(imageView);
    }
}
