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
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("username", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getUsername(), 50));
            v.put("fullName", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getFullName(), 100));
            v.put("email", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getEmail(), 100));
            v.put("password", user.getPassword());
            v.put("goals", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getGoals(), 200));
            v.put("gender", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getGender(), 20));
            v.put("fitnessLevel", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getFitnessLevel(), 50));
            v.put("height", user.getHeight());
            v.put("weight", user.getWeight());
            v.put("weightGoal", user.getWeightGoal());
            v.put("maxPushups", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getMaxPushups(), 20));
            v.put("maxPullups", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getMaxPullups(), 20));
            v.put("maxDips", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getMaxDips(), 20));
            v.put("maxSquats", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getMaxSquats(), 20));
            return db.insert("users", null, v);
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi đăng ký user: " + (user != null ? user.getEmail() : "null"), e);
            return -1;
        }
    }

    public User getUserByEmail(String email) {
        Cursor c = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sanitizedEmail = com.nct.trenx.utils.ResilienceLayer.sanitizeString(email, 100);
            c = db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{sanitizedEmail});
            if (c != null && c.moveToFirst()) {
                return mapCursorToUser(c);
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy user bằng email: " + email, e);
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(c);
        }
        return null;
    }

    public User getUserById(int id) {
        Cursor c = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("SELECT * FROM users WHERE id=?", new String[]{String.valueOf(id)});
            if (c != null && c.moveToFirst()) {
                return mapCursorToUser(c);
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy user bằng id: " + id, e);
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(c);
        }
        return null;
    }

    public boolean updateUserProfile(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("username", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getUsername(), 50));
            v.put("fullName", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getFullName(), 100));
            v.put("email", com.nct.trenx.utils.ResilienceLayer.sanitizeString(user.getEmail(), 100));
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                v.put("password", user.getPassword());
            }
            if (user.getGoals() != null) v.put("goals", user.getGoals());
            if (user.getGender() != null) v.put("gender", user.getGender());
            if (user.getFitnessLevel() != null) v.put("fitnessLevel", user.getFitnessLevel());
            v.put("height", user.getHeight());
            v.put("weight", user.getWeight());
            v.put("weightGoal", user.getWeightGoal());
            int rows = db.update("users", v, "id=?", new String[]{String.valueOf(user.getId())});
            return rows > 0;
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi cập nhật thông tin user: " + (user != null ? user.getId() : "null"), e);
            return false;
        }
    }

    public boolean updateUserPassword(String email, String newPassword) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("password", newPassword);
            String sanitizedEmail = com.nct.trenx.utils.ResilienceLayer.sanitizeString(email, 100);
            int rows = db.update("users", v, "email=?", new String[]{sanitizedEmail});
            return rows > 0;
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi cập nhật mật khẩu user: " + email, e);
            return false;
        }
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
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("user_id", userId);
            v.put("date", com.nct.trenx.utils.ResilienceLayer.sanitizeString(h.getDate(), 50));
            v.put("day_name", com.nct.trenx.utils.ResilienceLayer.sanitizeString(h.getDayName(), 30));
            v.put("difficulty", com.nct.trenx.utils.ResilienceLayer.sanitizeString(h.getDifficulty(), 30));
            v.put("progress_percent", h.getProgressPercent());
            v.put("duration_seconds", h.getDurationSeconds());
            v.put("muscle_groups", com.nct.trenx.utils.ResilienceLayer.sanitizeString(h.getMuscleGroups(), 200));
            db.insert("workout_history", null, v);
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi thêm lịch sử tập cho user " + userId, e);
        }
    }

    public List<WorkoutHistory> getWorkoutHistory(int userId) {
        List<WorkoutHistory> list = new ArrayList<>();
        Cursor c = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery("SELECT * FROM workout_history WHERE user_id=? ORDER BY date DESC", 
                    new String[]{String.valueOf(userId)});
            if (c != null && c.moveToFirst()) {
                do {
                    list.add(new WorkoutHistory(c.getString(2), c.getString(3), c.getString(4),
                            c.getInt(5), 5, c.getInt(6), c.getString(7)));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy lịch sử tập cho user: " + userId, e);
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(c);
        }
        return list;
    }

    public User login(String email, String password) {
        Cursor c = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sanitizedEmail = com.nct.trenx.utils.ResilienceLayer.sanitizeString(email, 100);
            c = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{sanitizedEmail, password});
            if (c != null && c.moveToFirst()) {
                return mapCursorToUser(c);
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi đăng nhập: " + email, e);
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(c);
        }
        return null;
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM exercises", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy danh sách tất cả bài tập", e);
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(cursor);
        }
        return list;
    }

    public List<Exercise> getExercisesByCategory(String category) {
        List<Exercise> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sanitizedCategory = com.nct.trenx.utils.ResilienceLayer.sanitizeString(category, 50);
            cursor = db.rawQuery("SELECT * FROM exercises WHERE category = ?", new String[]{sanitizedCategory});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy danh sách bài tập theo category: " + category, e);
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(cursor);
        }
        return list;
    }

    public void setExerciseLiked(int userId, String exerciseName, boolean liked) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String sanitizedName = com.nct.trenx.utils.ResilienceLayer.sanitizeString(exerciseName, 100);
            if (liked) {
                ContentValues v = new ContentValues();
                v.put("user_id", userId);
                v.put("exercise_name", sanitizedName);
                db.insertWithOnConflict("user_likes", null, v, SQLiteDatabase.CONFLICT_REPLACE);
            } else {
                db.delete("user_likes", "user_id=? AND exercise_name=?", new String[]{String.valueOf(userId), sanitizedName});
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi thích/bỏ thích bài tập: " + exerciseName, e);
        }
    }

    public boolean isExerciseLiked(int userId, String exerciseName) {
        Cursor c = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sanitizedName = com.nct.trenx.utils.ResilienceLayer.sanitizeString(exerciseName, 100);
            c = db.rawQuery("SELECT 1 FROM user_likes WHERE user_id=? AND exercise_name=?", 
                    new String[]{String.valueOf(userId), sanitizedName});
            return c != null && c.getCount() > 0;
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi kiểm tra thích bài tập: " + exerciseName, e);
            return false;
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(c);
        }
    }

    public List<Exercise> getExercisesByDayAndDifficulty(String day, String difficulty) {
        List<Exercise> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sanitizedDay = com.nct.trenx.utils.ResilienceLayer.sanitizeString(day, 50);
            String sanitizedDiff = com.nct.trenx.utils.ResilienceLayer.sanitizeString(difficulty, 20);

            cursor = db.rawQuery("SELECT * FROM exercises WHERE (day LIKE ? OR category LIKE ?) AND difficulty = ?", 
                    new String[]{"%" + sanitizedDay + "%", "%" + sanitizedDay + "%", sanitizedDiff});
            if (cursor == null || !cursor.moveToFirst()) {
                com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(cursor);
                cursor = db.rawQuery("SELECT * FROM exercises WHERE day LIKE ? OR category LIKE ?", 
                        new String[]{"%" + sanitizedDay + "%", "%" + sanitizedDay + "%"});
            }
            if (cursor == null || !cursor.moveToFirst()) {
                com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(cursor);
                cursor = db.rawQuery("SELECT * FROM exercises LIMIT 7", null);
            }

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy bài tập theo ngày và độ khó", e);
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(cursor);
        }
        return list;
    }

    public List<Exercise> searchExercises(String query) {
        List<Exercise> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sanitizedQuery = com.nct.trenx.utils.ResilienceLayer.sanitizeSearchQuery(query);
            String wildCardQuery = "%" + sanitizedQuery + "%";
            cursor = db.rawQuery("SELECT * FROM exercises WHERE name LIKE ? OR category LIKE ?", 
                    new String[]{wildCardQuery, wildCardQuery});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi tìm kiếm bài tập với query: " + query, e);
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(cursor);
        }
        return list;
    }

    public List<Exercise> getLikedExercises(int userId) {
        List<Exercise> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT e.* FROM exercises e INNER JOIN user_likes l ON e.name = l.exercise_name WHERE l.user_id = ?", 
                    new String[]{String.valueOf(userId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi lấy danh sách bài tập đã thích", e);
        } finally {
            com.nct.trenx.utils.ResilienceLayer.safeCloseCursor(cursor);
        }
        return list;
    }
}
