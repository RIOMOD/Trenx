package com.nct.trenx.model;

/**
 * Thông tin buổi tập theo ngày trong tuần (dashboard).
 */
public class WorkoutDayInfo {
    private final String dayName;
    private final String workoutTitle;
    private final String imageUrl;

    public WorkoutDayInfo(String dayName, String workoutTitle, String imageUrl) {
        this.dayName = dayName;
        this.workoutTitle = workoutTitle;
        this.imageUrl = imageUrl;
    }

    public String getDayName() {
        return dayName;
    }

    public String getWorkoutTitle() {
        return workoutTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
