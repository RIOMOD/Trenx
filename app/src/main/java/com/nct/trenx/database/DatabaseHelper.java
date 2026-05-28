package com.nct.trenx.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nct.trenx.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nâng version lên 12 để cập nhật kho bài tập khổng lồ cho cả tuần
    public DatabaseHelper(Context context) {
        super(context, "TrenxDB", null, 12);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE exercises (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, reps TEXT, category TEXT, description TEXT, " +
                "image_name TEXT, day TEXT, difficulty TEXT, is_liked INTEGER DEFAULT 0)");

        DatabaseSeeder.seed(db);
    }

    public List<Exercise> getExercisesByDayAndDifficulty(String day, String difficulty) {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE day = ? AND difficulty = ?", new String[]{day, difficulty});
        try {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(
                            cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)
                    ));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    public List<Exercise> getLikedExercises() {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE is_liked = 1", null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new Exercise(
                            cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)
                    ));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS exercises");
        onCreate(db);
    }
}
