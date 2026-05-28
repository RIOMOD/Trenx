package com.nct.trenx.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseSeeder {

    public static void seed(SQLiteDatabase db) {
        // --- LINK ẢNH MẪU CHẤT LƯỢNG CAO ---
        String imgAbs = "https://i.ytimg.com/vi/pd3-q2U7JXk/maxresdefault.jpg";
        String imgPushup = "https://i.ytimg.com/vi/srj94JCeuWw/maxresdefault.jpg";
        String imgLegs = "https://thenx.com/cdn/shop/articles/legsandglutes.jpg?v=1610420442";
        String imgPull = "https://chrisheria.com/cdn/shop/articles/main-qimg-84869695d7b5d92823071857945d812d.jpg?v=1555624187";
        String imgFullBody = "https://i.ytimg.com/vi/G5nxGTFBauM/maxresdefault.jpg";

        // ==========================================
        // THỨ 2: CORE SHREDDER (BỤNG)
        // ==========================================
        insertEx(db, "Plank", "3 Sets x 60s", "Bụng", "Giữ người thẳng.", imgAbs, "Monday", "Beginner", 1);
        insertEx(db, "Leg Raises", "3 Sets x 15 Reps", "Bụng", "Nâng chân vuông góc.", imgAbs, "Monday", "Beginner", 0);
        insertEx(db, "Flutter Kicks", "3 Sets x 40s", "Bụng", "Đá chân liên tục.", imgAbs, "Monday", "Beginner", 0);
        insertEx(db, "Hollow Body", "3 Sets x 45s", "Bụng", "Gồng bụng giữ thế thuyền.", imgAbs, "Monday", "Intermediate", 1);
        insertEx(db, "Russian Twists", "3 Sets x 20 Reps", "Bụng", "Xoay người tập cơ liên sườn.", imgAbs, "Monday", "Intermediate", 0);
        insertEx(db, "V-Ups", "3 Sets x 12 Reps", "Bụng", "Gập bụng hình chữ V.", imgAbs, "Monday", "Intermediate", 0);
        insertEx(db, "L-Sit Hold", "4 Sets x 15s", "Bụng", "Nâng người giữ chân thẳng.", imgAbs, "Monday", "Advanced", 1);
        insertEx(db, "Hanging Leg Raises", "4 Sets x 10 Reps", "Bụng", "Treo người nâng chân.", imgAbs, "Monday", "Advanced", 0);

        // ==========================================
        // THỨ 3: CHEST & TRICEPS (NGỰC & TAY SAU)
        // ==========================================
        insertEx(db, "Incline Push-ups", "3 Sets x 15 Reps", "Ngực", "Chống đẩy dốc lên.", imgPushup, "Tuesday", "Beginner", 0);
        insertEx(db, "Knee Push-ups", "3 Sets x 15 Reps", "Ngực", "Chống đẩy bằng đầu gối.", imgPushup, "Tuesday", "Beginner", 0);
        insertEx(db, "Standard Push-ups", "4 Sets x 15 Reps", "Ngực", "Chống đẩy cơ bản.", imgPushup, "Tuesday", "Intermediate", 1);
        insertEx(db, "Diamond Push-ups", "4 Sets x 12 Reps", "Ngực", "Chống đẩy hẹp tay.", imgPushup, "Tuesday", "Intermediate", 0);
        insertEx(db, "Dips", "3 Sets x 10 Reps", "Ngực", "Xà kép tập tay sau.", imgPushup, "Tuesday", "Intermediate", 0);
        insertEx(db, "Explosive Push-ups", "4 Sets x 10 Reps", "Ngực", "Chống đẩy bùng nổ.", imgPushup, "Tuesday", "Advanced", 1);
        insertEx(db, "Archer Push-ups", "4 Sets x 8 Reps", "Ngực", "Chống đẩy kiểu bắn cung.", imgPushup, "Tuesday", "Advanced", 0);

        // ==========================================
        // THỨ 4: QUAD CRUSHER (CHÂN)
        // ==========================================
        insertEx(db, "Air Squats", "3 Sets x 20 Reps", "Chân", "Squat cơ bản.", imgLegs, "Wednesday", "Beginner", 1);
        insertEx(db, "Walking Lunges", "3 Sets x 16 Reps", "Chân", "Bước gập gối.", imgLegs, "Wednesday", "Beginner", 0);
        insertEx(db, "Calf Raises", "3 Sets x 20 Reps", "Chân", "Nhón gót.", imgLegs, "Wednesday", "Beginner", 0);
        insertEx(db, "Bulgarian Split Squat", "3 Sets x 12 Reps", "Chân", "Squat 1 chân gác ghế.", imgLegs, "Wednesday", "Intermediate", 1);
        insertEx(db, "Jump Squats", "4 Sets x 15 Reps", "Chân", "Squat nhảy.", imgLegs, "Wednesday", "Intermediate", 0);
        insertEx(db, "Sumo Squats", "3 Sets x 20 Reps", "Chân", "Squat rộng chân.", imgLegs, "Wednesday", "Intermediate", 0);
        insertEx(db, "Pistol Squats", "4 Sets x 8 Reps", "Chân", "Squat 1 chân.", imgLegs, "Wednesday", "Advanced", 1);
        insertEx(db, "Shrimp Squats", "4 Sets x 10 Reps", "Chân", "Squat kiểu tôm.", imgLegs, "Wednesday", "Advanced", 0);

        // ==========================================
        // THỨ 5: SHOULDER POWER (VAI)
        // ==========================================
        insertEx(db, "Scapular Shrugs", "3 Sets x 15 Reps", "Vai", "Nhún vai tập bả vai.", imgPushup, "Thursday", "Beginner", 0);
        insertEx(db, "Pike Push-ups", "3 Sets x 10 Reps", "Vai", "Chống đẩy hình chữ V.", imgPushup, "Thursday", "Beginner", 0);
        insertEx(db, "Pseudo Push-ups", "3 Sets x 12 Reps", "Vai", "Chống đẩy đổ người tới.", imgPushup, "Thursday", "Intermediate", 1);
        insertEx(db, "Handstand Hold", "3 Sets x 30s", "Vai", "Trồng chuối dựa tường.", imgPushup, "Thursday", "Intermediate", 0);
        insertEx(db, "Handstand Push-ups", "4 Sets x 5 Reps", "Vai", "Chống đẩy trồng chuối.", imgPushup, "Thursday", "Advanced", 1);
        insertEx(db, "90 Degree Hold", "4 Sets x 10s", "Vai", "Giữ người góc 90 độ.", imgPushup, "Thursday", "Advanced", 0);

        // ==========================================
        // THỨ 6: BACK & BICEPS (LƯNG & TAY TRƯỚC)
        // ==========================================
        insertEx(db, "Scapular Pull-ups", "3 Sets x 12 Reps", "Lưng", "Gồng bả vai trên xà.", imgPull, "Friday", "Beginner", 0);
        insertEx(db, "Australian Pull-ups", "3 Sets x 12 Reps", "Lưng", "Hít xà nghiêng.", imgPull, "Friday", "Beginner", 0);
        insertEx(db, "Standard Pull-ups", "4 Sets x 10 Reps", "Lưng", "Hít xà tiêu chuẩn.", imgPull, "Friday", "Intermediate", 1);
        insertEx(db, "Chin-ups", "4 Sets x 10 Reps", "Lưng", "Hít xà ngược tay.", imgPull, "Friday", "Intermediate", 0);
        insertEx(db, "Muscle-up", "4 Sets x 5 Reps", "Lưng", "Lên xà bùng nổ.", imgPull, "Friday", "Advanced", 1);
        insertEx(db, "Front Lever Hold", "4 Sets x 10s", "Lưng", "Giữ người song song mặt đất.", imgPull, "Friday", "Advanced", 0);

        // ==========================================
        // THỨ 7: ACTIVE RECOVERY (PHỤC HỒI)
        // ==========================================
        insertEx(db, "Cat-Cow Stretch", "3 Sets x 15 Reps", "Giãn cơ", "Uốn lưng kiểu mèo.", imgFullBody, "Saturday", "Beginner", 0);
        insertEx(db, "Downward Dog", "3 Sets x 45s", "Giãn cơ", "Tư thế chữ V ngược.", imgFullBody, "Saturday", "Beginner", 0);
        insertEx(db, "Cobra Stretch", "3 Sets x 30s", "Giãn cơ", "Tư thế rắn hổ mang.", imgFullBody, "Saturday", "Intermediate", 1);

        // ==========================================
        // CHỦ NHẬT: FULL BODY MASTER (TOÀN THÂN)
        // ==========================================
        insertEx(db, "Jumping Jacks", "3 Sets x 50 Reps", "Toàn thân", "Nhảy vung tay.", imgFullBody, "Sunday", "Beginner", 0);
        insertEx(db, "Mountain Climbers", "3 Sets x 40s", "Toàn thân", "Leo núi tại chỗ.", imgFullBody, "Sunday", "Beginner", 0);
        insertEx(db, "Burpees", "3 Sets x 15 Reps", "Toàn thân", "Hít đất kết hợp nhảy.", imgFullBody, "Sunday", "Intermediate", 1);
        insertEx(db, "Plank Jacks", "3 Sets x 20 Reps", "Toàn thân", "Plank kết hợp nhảy chân.", imgFullBody, "Sunday", "Intermediate", 0);
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
