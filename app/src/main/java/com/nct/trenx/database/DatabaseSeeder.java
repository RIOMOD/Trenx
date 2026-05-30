package com.nct.trenx.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseSeeder {

    public static void seed(SQLiteDatabase db) {
        String imgAbs = "https://i.ytimg.com/vi/pd3-q2U7JXk/maxresdefault.jpg";
        String imgPushup = "https://i.ytimg.com/vi/srj94JCeuWw/maxresdefault.jpg";
        String imgLegs = "https://thenx.com/cdn/shop/articles/legsandglutes.jpg?v=1610420442";
        String imgPull = "https://chrisheria.com/cdn/shop/articles/main-qimg-84869695d7b5d92823071857945d812d.jpg?v=1555624187";
        String imgFullBody = "https://i.ytimg.com/vi/G5nxGTFBauM/maxresdefault.jpg";

        // MONDAY - CORE
        insertEx(db, "Plank", "3 Sets x 60s", "Bụng", "Giữ người thẳng.", imgAbs, "Monday", "Beginner", 1);
        insertEx(db, "Leg Raises", "3 Sets x 15 Reps", "Bụng", "Nâng chân.", imgAbs, "Monday", "Beginner", 0);
        insertEx(db, "Hollow Body", "3 Sets x 45s", "Bụng", "Gồng bụng.", imgAbs, "Monday", "Intermediate", 1);
        insertEx(db, "L-Sit Hold", "4 Sets x 15s", "Bụng", "Nâng người.", imgAbs, "Monday", "Advanced", 1);

        // TUESDAY - CHEST
        insertEx(db, "Incline Push-ups", "3 Sets x 15 Reps", "Ngực", "Chống đẩy dốc.", imgPushup, "Tuesday", "Beginner", 0);
        insertEx(db, "Standard Push-ups", "4 Sets x 15 Reps", "Ngực", "Chống đẩy.", imgPushup, "Tuesday", "Intermediate", 1);
        insertEx(db, "Archer Push-ups", "4 Sets x 8 Reps", "Ngực", "Chống đẩy bắn cung.", imgPushup, "Tuesday", "Advanced", 0);

        // WEDNESDAY - LEGS
        insertEx(db, "Air Squats", "3 Sets x 20 Reps", "Chân", "Squat cơ bản.", imgLegs, "Wednesday", "Beginner", 1);
        insertEx(db, "Jump Squats", "4 Sets x 15 Reps", "Chân", "Squat nhảy.", imgLegs, "Wednesday", "Intermediate", 0);
        insertEx(db, "Pistol Squats", "4 Sets x 8 Reps", "Chân", "Squat 1 chân.", imgLegs, "Wednesday", "Advanced", 1);

        // THURSDAY - SHOULDERS
        insertEx(db, "Pike Push-ups", "3 Sets x 10 Reps", "Vai", "Chống đẩy chữ V.", imgPushup, "Thursday", "Beginner", 0);
        insertEx(db, "Handstand Hold", "3 Sets x 30s", "Vai", "Trồng chuối.", imgPushup, "Thursday", "Intermediate", 0);
        insertEx(db, "Handstand Push-ups", "4 Sets x 5 Reps", "Vai", "Chống đẩy trồng chuối.", imgPushup, "Thursday", "Advanced", 1);

        // FRIDAY - BACK
        insertEx(db, "Australian Pull-ups", "3 Sets x 12 Reps", "Lưng", "Hít xà nghiêng.", imgPull, "Friday", "Beginner", 0);
        insertEx(db, "Standard Pull-ups", "4 Sets x 10 Reps", "Lưng", "Hít xà.", imgPull, "Friday", "Intermediate", 1);
        insertEx(db, "Muscle-up", "4 Sets x 5 Reps", "Lưng", "Lên xà bùng nổ.", imgPull, "Friday", "Advanced", 1);

        // SATURDAY - ACTIVE RECOVERY
        insertEx(db, "Cat-Cow Stretch", "3 Sets x 15 Reps", "Giãn cơ", "Uốn lưng kiểu mèo.", imgFullBody, "Saturday", "Beginner", 0);
        insertEx(db, "Downward Dog", "3 Sets x 45s", "Giãn cơ", "Tư thế chữ V ngược.", imgFullBody, "Saturday", "Beginner", 0);
        insertEx(db, "Cobra Stretch", "3 Sets x 30s", "Giãn cơ", "Tư thế rắn hổ mang.", imgFullBody, "Saturday", "Intermediate", 1);
        insertEx(db, "Child's Pose", "3 Sets x 60s", "Giãn cơ", "Tư thế em bé.", imgFullBody, "Saturday", "Intermediate", 0);
        insertEx(db, "Bridge Hold", "3 Sets x 30s", "Giãn cơ", "Tư thế cây cầu.", imgFullBody, "Saturday", "Advanced", 1);
        insertEx(db, "Deep Squat Hold", "3 Sets x 60s", "Giãn cơ", "Giữ Squat sâu.", imgFullBody, "Saturday", "Advanced", 0);

        // SUNDAY - FULL BODY
        insertEx(db, "Jumping Jacks", "3 Sets x 50 Reps", "Toàn thân", "Nhảy vung tay.", imgFullBody, "Sunday", "Beginner", 0);
        insertEx(db, "Burpees", "3 Sets x 15 Reps", "Toàn thân", "Hít đất kết hợp nhảy.", imgFullBody, "Sunday", "Intermediate", 1);
        insertEx(db, "Navy Seal Burpees", "4 Sets x 10 Reps", "Toàn thân", "Biến thể Burpees khó.", imgFullBody, "Sunday", "Advanced", 1);
    }

    private static void insertEx(SQLiteDatabase db, String name, String reps, String category, String desc, String img, String day, String diff, int isLiked) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("reps", reps);
        values.put("category", category);
        values.put("description", desc);
        values.put("image_name", img);
        values.put("day", day);
        values.put("difficulty", diff);
        values.put("is_liked", isLiked);
        db.insert("exercises", null, values);
    }
}
