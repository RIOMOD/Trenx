package com.nct.trenx.utils;

import android.content.Intent;

/**
 * Hằng số Intent extras dùng chung trong app.
 */
public final class IntentExtras {

    public static final String DAY_NAME = "DAY_NAME";
    /** Chỉ đọc fallback; gửi mới phải dùng {@link #DAY_NAME}. */
    @Deprecated
    public static final String DAY_LEGACY = "DAY";

    public static final String DIFFICULTY = "DIFFICULTY";
    public static final String POSITION = "POSITION";
    public static final String WORKOUT_TITLE = "WORKOUT_TITLE";
    public static final String TOTAL_TIME = "TOTAL_TIME";
    public static final String TARGET_FRAGMENT = "TARGET_FRAGMENT";

    public static final String EXERCISE_NAME = "NAME";
    public static final String EXERCISE_REPS = "REPS";
    public static final String EXERCISE_DESC = "DESC";

    /** Legacy – ExrciselistActivity / LevelActivity */
    public static final String NHOM_CO = "NHOM_CO";

    public static final String DIFFICULTY_BEGINNER = "Beginner";
    public static final String DIFFICULTY_INTERMEDIATE = "Intermediate";
    public static final String DIFFICULTY_ADVANCED = "Advanced";

    public static final String DEFAULT_DAY = "Wednesday";
    public static final String DEFAULT_DIFFICULTY = DIFFICULTY_INTERMEDIATE;

    private IntentExtras() {
    }

    public static String getDayName(Intent intent) {
        if (intent == null) {
            return DEFAULT_DAY;
        }
        String day = intent.getStringExtra(DAY_NAME);
        if (day == null || day.isEmpty()) {
            day = intent.getStringExtra(DAY_LEGACY);
        }
        if (day == null || day.isEmpty()) {
            return DEFAULT_DAY;
        }
        return day;
    }

    public static String getDifficulty(Intent intent) {
        if (intent == null) {
            return DEFAULT_DIFFICULTY;
        }
        String difficulty = intent.getStringExtra(DIFFICULTY);
        if (difficulty == null || difficulty.isEmpty()) {
            return DEFAULT_DIFFICULTY;
        }
        return difficulty;
    }
}
