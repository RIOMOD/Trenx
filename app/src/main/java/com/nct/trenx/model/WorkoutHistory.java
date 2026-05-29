package com.nct.trenx.model;

/**
 * Lịch sử buổi tập — mỗi object đại diện 1 buổi tập đã hoàn thành.
 */
public class WorkoutHistory {

    private final String date;              // "2026-05-13"
    private final String dayName;           // "Wednesday"
    private final String difficulty;        // "Intermediate"
    private final int exercisesCompleted;   // Số bài đã hoàn thành
    private final int totalExercises;       // Tổng số bài
    private final int durationSeconds;      // Thời gian tập (giây)
    private final String muscleGroups;      // "Bụng,Ngực" (CSV)

    public WorkoutHistory(String date, String dayName, String difficulty,
                          int exercisesCompleted, int totalExercises,
                          int durationSeconds, String muscleGroups) {
        this.date = date;
        this.dayName = dayName;
        this.difficulty = difficulty;
        this.exercisesCompleted = exercisesCompleted;
        this.totalExercises = totalExercises;
        this.durationSeconds = durationSeconds;
        this.muscleGroups = muscleGroups;
    }

    public String getDate() {
        return date;
    }

    public String getDayName() {
        return dayName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getExercisesCompleted() {
        return exercisesCompleted;
    }

    public int getTotalExercises() {
        return totalExercises;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public String getMuscleGroups() {
        return muscleGroups;
    }

    /**
     * Tính phần trăm hoàn thành bài tập.
     */
    public int getProgressPercent() {
        if (totalExercises == 0) return 0;
        return (int) ((exercisesCompleted * 100.0) / totalExercises);
    }

    /**
     * Format thời gian tập dạng "MM:SS".
     */
    public String getFormattedDuration() {
        int mins = durationSeconds / 60;
        int secs = durationSeconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }
}
