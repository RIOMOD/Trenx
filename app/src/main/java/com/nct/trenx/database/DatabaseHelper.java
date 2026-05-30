package com.nct.trenx.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nct.trenx.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    // Đổi tên database mới nhất để xóa sạch dữ liệu cũ lỗi
    private static final String DATABASE_NAME = "Trenx_Data_Production";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating new database tables...");
        db.execSQL("CREATE TABLE exercises (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, reps TEXT, category TEXT, description TEXT, " +
                "image_name TEXT, day TEXT, difficulty TEXT, is_liked INTEGER DEFAULT 0)");

        DatabaseSeeder.seed(db);
        Log.d(TAG, "Database seeded successfully.");
    }

    public List<Exercise> getExercisesByDayAndDifficulty(String day, String difficulty) {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Chuẩn hóa dữ liệu đầu vào
        String queryDay = (day == null || day.isEmpty()) ? "Monday" : day.trim();
        String queryDiff = (difficulty == null || difficulty.isEmpty()) ? "Intermediate" : difficulty.trim();

        Log.d(TAG, "Querying for: Day=[" + queryDay + "], Diff=[" + queryDiff + "]");

        // Truy vấn chính xác theo từ khóa tiếng Anh trong Database
        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE day = ? AND difficulty = ?", 
                new String[]{queryDay, queryDiff});
        
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(
                            cursor.getString(1), // name
                            cursor.getString(2), // reps
                            cursor.getString(3), // category
                            cursor.getString(4), // description
                            cursor.getString(5), // image_name
                            cursor.getString(6), // day
                            cursor.getString(7)  // difficulty
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Query error", e);
        } finally {
            if (cursor != null) cursor.close();
        }
        
        Log.d(TAG, "Found " + list.size() + " exercises.");
        return list;
    }

    public List<Exercise> getLikedExercises() {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE is_liked = 1", null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(
                            cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)
                    ));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS exercises");
        onCreate(db);
    }
}
