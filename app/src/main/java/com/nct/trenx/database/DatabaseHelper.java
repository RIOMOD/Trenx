package com.nct.trenx.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nct.trenx.model.Exercise;
import com.nct.trenx.model.User;
import com.nct.trenx.model.WorkoutHistory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "Trenx_Data_Production";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating database tables...");
        db.execSQL("CREATE TABLE exercises (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, reps TEXT, category TEXT, description TEXT, " +
                "image_name TEXT, day TEXT, difficulty TEXT, is_liked INTEGER DEFAULT 0)");

        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, fullName TEXT, email TEXT UNIQUE, password TEXT, " +
                "goals TEXT, gender TEXT, fitnessLevel TEXT, " +
                "height INTEGER, weight INTEGER, weightGoal INTEGER, " +
                "maxPushups TEXT, maxPullups TEXT, maxDips TEXT, maxSquats TEXT)");

        db.execSQL("CREATE TABLE user_likes (user_id INTEGER, exercise_name TEXT, " +
                "PRIMARY KEY(user_id, exercise_name))");

        db.execSQL("CREATE TABLE workout_history (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, date TEXT, day_name TEXT, difficulty TEXT, " +
                "progress_percent INTEGER, duration_seconds INTEGER, muscle_groups TEXT)");

        DatabaseSeeder.seed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if (oldV < 3) {
            db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, fullName TEXT, email TEXT UNIQUE, password TEXT, " +
                    "goals TEXT, gender TEXT, fitnessLevel TEXT, " +
                    "height INTEGER, weight INTEGER, weightGoal INTEGER, " +
                    "maxPushups TEXT, maxPullups TEXT, maxDips TEXT, maxSquats TEXT)");
        }
        if (oldV < 4) {
            db.execSQL("CREATE TABLE IF NOT EXISTS user_likes (user_id INTEGER, exercise_name TEXT, " +
                    "PRIMARY KEY(user_id, exercise_name))");
        }
        if (oldV < 5) {
            db.execSQL("CREATE TABLE IF NOT EXISTS workout_history (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id INTEGER, date TEXT, day_name TEXT, difficulty TEXT, " +
                    "progress_percent INTEGER, duration_seconds INTEGER, muscle_groups TEXT)");
        }
    }

    public long registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("username", user.getUsername());
        v.put("fullName", user.getFullName());
        v.put("email", user.getEmail());
        v.put("password", user.getPassword());
        v.put("goals", user.getGoals());
        v.put("gender", user.getGender());
        v.put("fitnessLevel", user.getFitnessLevel());
        v.put("height", user.getHeight());
        v.put("weight", user.getWeight());
        v.put("weightGoal", user.getWeightGoal());
        v.put("maxPushups", user.getMaxPushups());
        v.put("maxPullups", user.getMaxPullups());
        v.put("maxDips", user.getMaxDips());
        v.put("maxSquats", user.getMaxSquats());
        return db.insert("users", null, v);
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});
        if (c != null && c.moveToFirst()) {
            User user = mapCursorToUser(c);
            c.close();
            return user;
        }
        if (c != null) c.close();
        return null;
    }

    private User mapCursorToUser(Cursor c) {
        User user = new User();
        user.setId(c.getInt(0));
        user.setUsername(c.getString(1));
        user.setFullName(c.getString(2));
        user.setEmail(c.getString(3));
        user.setPassword(c.getString(4));
        user.setGoals(c.getString(5));
        user.setGender(c.getString(6));
        user.setFitnessLevel(c.getString(7));
        user.setHeight(c.getInt(8));
        user.setWeight(c.getInt(9));
        user.setWeightGoal(c.getInt(10));
        user.setMaxPushups(c.getString(11));
        user.setMaxPullups(c.getString(12));
        user.setMaxDips(c.getString(13));
        user.setMaxSquats(c.getString(14));
        return user;
    }

    public void addWorkoutHistory(int userId, WorkoutHistory h) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("user_id", userId);
        v.put("date", h.getDate());
        v.put("day_name", h.getDayName());
        v.put("difficulty", h.getDifficulty());
        v.put("progress_percent", h.getProgressPercent());
        v.put("duration_seconds", h.getDurationSeconds());
        v.put("muscle_groups", h.getMuscleGroups());
        db.insert("workout_history", null, v);
    }

    public List<WorkoutHistory> getWorkoutHistory(int userId) {
        List<WorkoutHistory> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM workout_history WHERE user_id=? ORDER BY date DESC", 
                new String[]{String.valueOf(userId)});
        if (c != null && c.moveToFirst()) {
            do {
                list.add(new WorkoutHistory(c.getString(2), c.getString(3), c.getString(4),
                        c.getInt(5), 5, c.getInt(6), c.getString(7)));
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public User login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
        if (c != null && c.moveToFirst()) {
            User user = mapCursorToUser(c);
            c.close();
            return user;
        }
        if (c != null) c.close();
        return null;
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercises", null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    public List<Exercise> getExercisesByCategory(String category) {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE category = ?", new String[]{category});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    public void setExerciseLiked(int userId, String exerciseName, boolean liked) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (liked) {
            ContentValues v = new ContentValues();
            v.put("user_id", userId);
            v.put("exercise_name", exerciseName);
            db.insertWithOnConflict("user_likes", null, v, SQLiteDatabase.CONFLICT_REPLACE);
        } else {
            db.delete("user_likes", "user_id=? AND exercise_name=?", new String[]{String.valueOf(userId), exerciseName});
        }
    }

    public boolean isExerciseLiked(int userId, String exerciseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT 1 FROM user_likes WHERE user_id=? AND exercise_name=?", 
                new String[]{String.valueOf(userId), exerciseName});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    public List<Exercise> getExercisesByDayAndDifficulty(String day, String difficulty) {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE day = ? AND difficulty = ?", 
                new String[]{day, difficulty});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    public List<Exercise> searchExercises(String query) {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String wildCardQuery = "%" + query + "%";
        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE name LIKE ? OR category LIKE ?", 
                new String[]{wildCardQuery, wildCardQuery});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    public List<Exercise> getLikedExercises(int userId) {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT e.* FROM exercises e INNER JOIN user_likes l ON e.name = l.exercise_name WHERE l.user_id = ?", 
                new String[]{String.valueOf(userId)});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }
}
