package com.nct.trenx.utils;

import android.content.Context;
import android.content.Intent;

import com.nct.trenx.activity.TrainingActivity;

/**
 * Điều hướng tập trung – tránh tạo Intent sai key/extra.
 */
public final class NavigationUtils {

    private NavigationUtils() {
    }

    public static void startTraining(Context context) {
        startTraining(context, IntentExtras.DEFAULT_DAY, IntentExtras.DEFAULT_DIFFICULTY, 0);
    }

    public static void startTraining(Context context, String dayName, String difficulty) {
        startTraining(context, dayName, difficulty, 0);
    }

    public static void startTraining(Context context, String dayName, String difficulty, int position) {
        Intent intent = new Intent(context, TrainingActivity.class);
        intent.putExtra(IntentExtras.DAY_NAME, normalizeDay(dayName));
        intent.putExtra(IntentExtras.DIFFICULTY, normalizeDifficulty(difficulty));
        intent.putExtra(IntentExtras.POSITION, position);
        context.startActivity(intent);
    }

    private static String normalizeDay(String dayName) {
        if (dayName == null || dayName.isEmpty()) {
            return IntentExtras.DEFAULT_DAY;
        }
        return dayName;
    }

    private static String normalizeDifficulty(String difficulty) {
        if (difficulty == null || difficulty.isEmpty()) {
            return IntentExtras.DEFAULT_DIFFICULTY;
        }
        return difficulty;
    }
}
