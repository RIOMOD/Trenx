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

    public List<Exercise> getLikedExercises(int userId) {
        return databaseHelper.getLikedExercises(userId);
    }

    public List<Exercise> searchExercises(String query) {
        return databaseHelper.searchExercises(query);
    }

    public void setExerciseLiked(int userId, String exerciseName, boolean liked) {
        databaseHelper.setExerciseLiked(userId, exerciseName, liked);
    }

    public boolean isExerciseLiked(int userId, String exerciseName) {
        return databaseHelper.isExerciseLiked(userId, exerciseName);
    }
}
