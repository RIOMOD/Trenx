package com.nct.trenx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tăng version lên 4 để cập nhật cấu trúc bảng có thêm cột image_name
    public DatabaseHelper(Context context) {
        super(context, "TrenxDB", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cập nhật câu lệnh SQL: thêm cột image_name TEXT
        db.execSQL("CREATE TABLE exercises (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, reps TEXT, category TEXT, description TEXT, image_name TEXT)");

        // --- NHÓM NGỰC & TAY SAU ---
        insertSample(db, "Standard Push-ups", "3 Sets x 15 Reps", "Ngực & Tay sau",
                "Chống hai tay xuống sàn rộng bằng vai, giữ lưng thẳng và hạ người xuống.", "img_pushup");
        insertSample(db, "Diamond Push-ups", "3 Sets x 12 Reps", "Ngực & Tay sau",
                "Chụm tay hình kim cương để tập trung tối đa vào cơ tay sau.", "img_diamond");
        insertSample(db, "Incline Push-ups", "3 Sets x 15 Reps", "Ngực & Tay sau",
                "Đặt tay lên bục cao để tập trung vào phần ngực dưới.", "img_incline");
        insertSample(db, "Dips", "3 Sets x 10 Reps", "Ngực & Tay sau",
                "Sử dụng xà kép hoặc ghế để đẩy người lên, tác động sâu vào cơ ngực và tay sau.", "img_dips");
        insertSample(db, "Archer Push-ups", "3 Sets x 8 Reps", "Ngực & Tay sau",
                "Dồn trọng tâm sang từng bên tay để tăng độ khó và sức mạnh đơn phương.", "img_archer");

        // --- NHÓM LƯNG & TAY TRƯỚC ---
        insertSample(db, "Pull-ups", "4 Sets x 8 Reps", "Lưng & Tay trước",
                "Nắm xà rộng hơn vai, kéo người lên đến khi cằm vượt xà.", "img_pullup");
        insertSample(db, "Chin-ups", "3 Sets x 10 Reps", "Lưng & Tay trước",
                "Nắm ngược xà hướng vào lòng bàn tay để ăn nhiều vào bắp tay trước.", "img_chinup");
        insertSample(db, "Australian Pull-ups", "3 Sets x 12 Reps", "Lưng & Tay trước",
                "Kéo xà thấp khi chân vẫn chạm đất, bài tập tuyệt vời để xây dựng cơ lưng cơ bản.", "img_australian");
        insertSample(db, "Close Grip Pull-ups", "3 Sets x 8 Reps", "Lưng & Tay trước",
                "Nắm xà hẹp để tập trung vào cơ xô và giữa lưng.", "img_close_pullup");
        insertSample(db, "Muscle-up Prep", "4 Sets x 5 Reps", "Lưng & Tay trước",
                "Kéo xà cao hết mức có thể (kéo nổ) để chuẩn bị cho kỹ thuật Muscle-up.", "img_muscle_prep");

        // --- NHÓM CƠ BỤNG (CORE) ---
        insertSample(db, "Plank", "3 Sets x 60 Sec", "Cơ bụng (Core)",
                "Giữ người thẳng trên khuỷu tay, siết chặt cơ bụng và hít thở đều.", "img_plank");
        insertSample(db, "Leg Raises", "3 Sets x 15 Reps", "Cơ bụng (Core)",
                "Nằm ngửa và nâng chân thẳng lên vuông góc, sau đó hạ xuống chậm rãi.", "img_legraise");
        insertSample(db, "Hanging Leg Raises", "3 Sets x 10 Reps", "Cơ bụng (Core)",
                "Treo mình trên xà và nâng chân thẳng lên, tác động cực mạnh vào bụng dưới.", "img_hanging_leg");
        insertSample(db, "Russian Twists", "3 Sets x 20 Reps", "Cơ bụng (Core)",
                "Xoay người sang hai bên khi ngồi hổng chân để tập cơ bụng xiên.", "img_russian_twist");
        insertSample(db, "L-Sit Hold", "3 Sets x 15 Sec", "Cơ bụng (Core)",
                "Dùng tay đẩy người lên và giữ chân thẳng tạo thành hình chữ L.", "img_lsit");
    }

    private void insertSample(SQLiteDatabase db, String name, String reps, String category, String description, String imageName) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("reps", reps);
        values.put("category", category);
        values.put("description", description);
        values.put("image_name", imageName); // Lưu tên file ảnh đại diện
        db.insert("exercises", null, values);
    }

    public List<Exercise> getExercisesByCategory(String cat) {
        List<Exercise> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercises WHERE category = ?", new String[]{cat});
        if (cursor.moveToFirst()) {
            do {
                // Lấy thêm cột số 5 (image_name) để truyền vào constructor của Exercise
                list.add(new Exercise(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Xóa bảng cũ và tạo lại khi nâng cấp version
        db.execSQL("DROP TABLE IF EXISTS exercises");
        onCreate(db);
    }
}