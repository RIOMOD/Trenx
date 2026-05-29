package com.nct.trenx.database;

import android.content.Context;

import com.nct.trenx.model.Exercise;

import java.util.List;

/**
 * Điểm truy cập dữ liệu bài tập — bọc {@link DatabaseHelper}.
 */
public class ExerciseRepository {

    private final DatabaseHelper databaseHelper;

    public ExerciseRepository(Context context) {
        databaseHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public List<Exercise> getExercisesByDayAndDifficulty(String day, String difficulty) {
        return databaseHelper.getExercisesByDayAndDifficulty(day, difficulty);
    }

    public List<Exercise> getLikedExercises() {
        return databaseHelper.getLikedExercises();
    }
}
