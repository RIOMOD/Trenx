package com.nct.trenx.model;

import androidx.annotation.StringRes;

/**
 * Thông tin buổi tập theo ngày trong tuần (dashboard).
 */
public class WorkoutDayInfo {
    private final int dayNameResId;
    private final int workoutTitleResId;
    private final String imageUrl;
    private final String dbDayKey;

    public WorkoutDayInfo(@StringRes int dayNameResId, @StringRes int workoutTitleResId, String imageUrl, String dbDayKey) {
        this.dayNameResId = dayNameResId;
        this.workoutTitleResId = workoutTitleResId;
        this.imageUrl = imageUrl;
        this.dbDayKey = dbDayKey;
    }

    public int getDayNameResId() {
        return dayNameResId;
    }

    public int getWorkoutTitleResId() {
        return workoutTitleResId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDbDayKey() {
        return dbDayKey;
    }
}
