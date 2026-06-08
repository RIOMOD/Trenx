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

        // === MONDAY - CORE (Bụng) ===
        // Beginner
        insertEx(db, "Plank", "3 Sets x 60s", "Bụng", "Giữ người thẳng, gồng chặt cơ bụng.", imgAbs, "Monday", "Beginner", 1);
        insertEx(db, "Leg Raises", "3 Sets x 12 Reps", "Bụng", "Nằm ngửa, nâng chân thẳng lên cao.", imgAbs, "Monday", "Beginner", 0);
        insertEx(db, "Crunches", "3 Sets x 20 Reps", "Bụng", "Gập bụng cơ bản.", imgAbs, "Monday", "Beginner", 0);
        insertEx(db, "Mountain Climbers", "3 Sets x 30s", "Bụng", "Tư thế Plank, chạy tại chỗ.", imgAbs, "Monday", "Beginner", 0);
        insertEx(db, "Russian Twists", "3 Sets x 20 Reps", "Bụng", "Ngồi nghiêng người, xoay eo sang hai bên.", imgAbs, "Monday", "Beginner", 0);
        // Intermediate
        insertEx(db, "Hollow Body Hold", "3 Sets x 45s", "Bụng", "Nhấc chân và vai khỏi sàn, giữ cố định.", imgAbs, "Monday", "Intermediate", 1);
        insertEx(db, "Kanging Leg Raises", "3 Sets x 10 Reps", "Bụng", "Treo người trên xà, nâng chân vuông góc.", imgAbs, "Monday", "Intermediate", 0);
        insertEx(db, "Flutter Kicks", "4 Sets x 40s", "Bụng", "Nằm ngửa, đá chân xen kẽ liên tục.", imgAbs, "Monday", "Intermediate", 0);
        insertEx(db, "Bicycle Crunches", "3 Sets x 24 Reps", "Bụng", "Đạp xe kết hợp gập bụng chéo.", imgAbs, "Monday", "Intermediate", 0);
        insertEx(db, "Side Plank", "3 Sets x 45s mỗi bên", "Bụng", "Plank nghiêng để tập cơ liên sườn.", imgAbs, "Monday", "Intermediate", 0);
        // Advanced
        insertEx(db, "L-Sit Hold", "4 Sets x 15s", "Bụng", "Dùng tay nâng toàn bộ cơ thể, chân thẳng.", imgAbs, "Monday", "Advanced", 1);
        insertEx(db, "Dragon Flag", "3 Sets x 8 Reps", "Bụng", "Bài tập bụng huyền thoại của Lý Tiểu Long.", imgAbs, "Monday", "Advanced", 1);
        insertEx(db, "V-Sits", "3 Sets x 15 Reps", "Bụng", "Gập người hình chữ V.", imgAbs, "Monday", "Advanced", 0);
        insertEx(db, "Windshield Wipers", "3 Sets x 12 Reps", "Bụng", "Treo xà, đưa chân sang hai bên như gạt nước.", imgAbs, "Monday", "Advanced", 0);
        insertEx(db, "Ab Wheel Rollouts", "3 Sets x 10 Reps", "Bụng", "Sử dụng con lăn bụng nâng cao.", imgAbs, "Monday", "Advanced", 0);

        // === TUESDAY - CHEST & TRICEPS (Ngực & Tay sau) ===
        // Beginner
        insertEx(db, "Incline Push-ups", "3 Sets x 15 Reps", "Ngực", "Chống đẩy trên ghế hoặc bục cao.", imgPushup, "Tuesday", "Beginner", 0);
        insertEx(db, "Knee Push-ups", "3 Sets x 12 Reps", "Ngực", "Chống đẩy bằng đầu gối.", imgPushup, "Tuesday", "Beginner", 0);
        insertEx(db, "Bench Dips", "3 Sets x 15 Reps", "Tay sau", "Chống tay vào ghế, hạ người xuống.", imgPushup, "Tuesday", "Beginner", 0);
        insertEx(db, "Wall Push-ups", "3 Sets x 20 Reps", "Ngực", "Chống đẩy vào tường.", imgPushup, "Tuesday", "Beginner", 0);
        insertEx(db, "Wide Push-ups", "3 Sets x 12 Reps", "Ngực", "Chống đẩy rộng tay.", imgPushup, "Tuesday", "Beginner", 0);
        // Intermediate
        insertEx(db, "Standard Push-ups", "4 Sets x 15 Reps", "Ngực", "Chống đẩy cơ bản đúng form.", imgPushup, "Tuesday", "Intermediate", 1);
        insertEx(db, "Diamond Push-ups", "3 Sets x 12 Reps", "Ngực/Tay sau", "Chống đẩy hẹp tay hình kim cương.", imgPushup, "Tuesday", "Intermediate", 0);
        insertEx(db, "Tricep Dips", "3 Sets x 10 Reps", "Tay sau", "Hạ người trên xà kép.", imgPushup, "Tuesday", "Intermediate", 1);
        insertEx(db, "Explosive Push-ups", "3 Sets x 10 Reps", "Ngực", "Chống đẩy bùng nổ nhấc tay khỏi sàn.", imgPushup, "Tuesday", "Intermediate", 0);
        insertEx(db, "Pseudo Planche Pushups", "3 Sets x 8 Reps", "Ngực/Vai", "Chống đẩy đổ người về phía trước.", imgPushup, "Tuesday", "Intermediate", 0);
        // Advanced
        insertEx(db, "Archer Push-ups", "4 Sets x 8 Reps", "Ngực", "Chống đẩy bắn cung (dồn lực 1 bên).", imgPushup, "Tuesday", "Advanced", 0);
        insertEx(db, "One Arm Push-ups", "3 Sets x 5 Reps mỗi bên", "Ngực", "Chống đẩy bằng một tay.", imgPushup, "Tuesday", "Advanced", 1);
        insertEx(db, "Weighted Dips", "3 Sets x 8 Reps", "Tay sau", "Hít xà kép kèm thêm tạ.", imgPushup, "Tuesday", "Advanced", 0);
        insertEx(db, "Ring Dips", "3 Sets x 10 Reps", "Tay sau", "Hít xà kép trên vòng treo.", imgPushup, "Tuesday", "Advanced", 0);
        insertEx(db, "Clapping Push-ups", "3 Sets x 10 Reps", "Ngực", "Chống đẩy vỗ tay.", imgPushup, "Tuesday", "Advanced", 0);

        // === WEDNESDAY - LEGS (Chân) ===
        // Beginner
        insertEx(db, "Air Squats", "3 Sets x 20 Reps", "Chân", "Squat cơ bản không tạ.", imgLegs, "Wednesday", "Beginner", 1);
        insertEx(db, "Lunges", "3 Sets x 12 mỗi bên", "Chân", "Bước chân tới trước và hạ thấp người.", imgLegs, "Wednesday", "Beginner", 0);
        insertEx(db, "Calf Raises", "3 Sets x 20 Reps", "Bắp chân", "Kiễng chân lên xuống.", imgLegs, "Wednesday", "Beginner", 0);
        insertEx(db, "Wall Sit", "3 Sets x 45s", "Chân", "Tựa lưng vào tường ở tư thế ngồi.", imgLegs, "Wednesday", "Beginner", 0);
        insertEx(db, "Glute Bridges", "3 Sets x 15 Reps", "Mông", "Nằm ngửa, nâng hông lên cao.", imgLegs, "Wednesday", "Beginner", 0);
        // Intermediate
        insertEx(db, "Jump Squats", "4 Sets x 15 Reps", "Chân", "Squat kết hợp bật nhảy cao.", imgLegs, "Wednesday", "Intermediate", 0);
        insertEx(db, "Bulgarian Split Squats", "3 Sets x 10 mỗi bên", "Chân", "Squat một chân với chân kia đặt trên ghế.", imgLegs, "Wednesday", "Intermediate", 1);
        insertEx(db, "Walking Lunges", "3 Sets x 20 bước", "Chân", "Vừa bước lunges vừa di chuyển.", imgLegs, "Wednesday", "Intermediate", 0);
        insertEx(db, "Box Jumps", "3 Sets x 12 Reps", "Chân", "Nhảy lên bục cao.", imgLegs, "Wednesday", "Intermediate", 0);
        insertEx(db, "Sumo Squats", "3 Sets x 15 Reps", "Chân", "Squat rộng chân tập cơ đùi trong.", imgLegs, "Wednesday", "Intermediate", 0);
        // Advanced
        insertEx(db, "Pistol Squats", "4 Sets x 8 Reps mỗi bên", "Chân", "Squat trên một chân.", imgLegs, "Wednesday", "Advanced", 1);
        insertEx(db, "Shrimp Squats", "3 Sets x 8 Reps mỗi bên", "Chân", "Một biến thể khó khác của squat 1 chân.", imgLegs, "Wednesday", "Advanced", 0);
        insertEx(db, "Explosive Box Jumps", "3 Sets x 10 Reps", "Chân", "Nhảy lên bục rất cao.", imgLegs, "Wednesday", "Advanced", 0);
        insertEx(db, "Dragon Squats", "3 Sets x 8 Reps mỗi bên", "Chân", "Squat chéo chân nâng cao.", imgLegs, "Wednesday", "Advanced", 0);
        insertEx(db, "Nordic Hamstring Curls", "3 Sets x 6 Reps", "Đùi sau", "Hạ người bằng cơ đùi sau.", imgLegs, "Wednesday", "Advanced", 0);

        // === THURSDAY - SHOULDERS (Vai) ===
        // Beginner
        insertEx(db, "Pike Push-ups", "3 Sets x 10 Reps", "Vai", "Chống đẩy hình chữ V ngược.", imgPushup, "Thursday", "Beginner", 0);
        insertEx(db, "Arm Circles", "3 Sets x 60s", "Vai", "Xoay tay vòng tròn liên tục.", imgPushup, "Thursday", "Beginner", 0);
        insertEx(db, "Wall Walks", "3 Sets x 5 Reps", "Vai", "Đi bộ bằng tay lên tường.", imgPushup, "Thursday", "Beginner", 0);
        insertEx(db, "Plank to Downward Dog", "3 Sets x 12 Reps", "Vai", "Chuyển từ Plank sang chữ V ngược.", imgPushup, "Thursday", "Beginner", 0);
        insertEx(db, "Superman", "3 Sets x 15 Reps", "Lưng dưới/Vai", "Nằm sấp, nhấc tay và chân lên.", imgPushup, "Thursday", "Beginner", 0);
        // Intermediate
        insertEx(db, "Handstand Hold (Wall)", "3 Sets x 30s", "Vai", "Trồng chuối dựa tường.", imgPushup, "Thursday", "Intermediate", 0);
        insertEx(db, "Decline Push-ups", "3 Sets x 15 Reps", "Vai/Ngực trên", "Chống đẩy với chân đặt trên cao.", imgPushup, "Thursday", "Intermediate", 1);
        insertEx(db, "Hindu Push-ups", "3 Sets x 10 Reps", "Vai/Ngực", "Chống đẩy kiểu Ấn Độ.", imgPushup, "Thursday", "Intermediate", 0);
        insertEx(db, "Plank Lean", "3 Sets x 30s", "Vai", "Đổ người về trước trong tư thế Plank.", imgPushup, "Thursday", "Intermediate", 0);
        insertEx(db, "Shoulder Taps", "3 Sets x 20 Reps", "Vai/Core", "Plank và chạm tay vào vai đối diện.", imgPushup, "Thursday", "Intermediate", 0);
        // Advanced
        insertEx(db, "Handstand Push-ups", "4 Sets x 5 Reps", "Vai", "Chống đẩy trong tư thế trồng chuối.", imgPushup, "Thursday", "Advanced", 1);
        insertEx(db, "90 Degree Hold", "3 Sets x 10s", "Vai", "Giữ người song song mặt đất (Bent arm).", imgPushup, "Thursday", "Advanced", 1);
        insertEx(db, "One Arm Pike Push-ups", "3 Sets x 5 mỗi bên", "Vai", "Chống đẩy chữ V bằng 1 tay.", imgPushup, "Thursday", "Advanced", 0);
        insertEx(db, "Wall Handstand Push-ups", "3 Sets x 8 Reps", "Vai", "Chống đẩy trồng chuối sâu.", imgPushup, "Thursday", "Advanced", 0);
        insertEx(db, "Tuck Planche", "3 Sets x 15s", "Vai", "Co chân và giữ thăng bằng trên đôi tay.", imgPushup, "Thursday", "Advanced", 1);

        // === FRIDAY - BACK & BICEPS (Lưng & Tay trước) ===
        // Beginner
        insertEx(db, "Australian Pull-ups", "3 Sets x 12 Reps", "Lưng", "Hít xà nghiêng với thanh xà thấp.", imgPull, "Friday", "Beginner", 0);
        insertEx(db, "Dead Hang", "3 Sets x 45s", "Lưng/Cẳng tay", "Treo người tĩnh trên xà.", imgPull, "Friday", "Beginner", 0);
        insertEx(db, "Scapular Pull-ups", "3 Sets x 15 Reps", "Lưng", "Kích hoạt bả vai trên xà.", imgPull, "Friday", "Beginner", 0);
        insertEx(db, "Chin-ups (Assisted)", "3 Sets x 10 Reps", "Tay trước", "Hít xà ngửa tay có hỗ trợ.", imgPull, "Friday", "Beginner", 0);
        insertEx(db, "Inverted Rows", "3 Sets x 12 Reps", "Lưng", "Kéo người trên xà thấp.", imgPull, "Friday", "Beginner", 0);
        // Intermediate
        insertEx(db, "Standard Pull-ups", "4 Sets x 10 Reps", "Lưng", "Hít xà đơn cơ bản đúng form.", imgPull, "Friday", "Intermediate", 1);
        insertEx(db, "Chin-ups", "3 Sets x 10 Reps", "Tay trước", "Hít xà ngửa lòng bàn tay.", imgPull, "Friday", "Intermediate", 1);
        insertEx(db, "Wide Grip Pull-ups", "3 Sets x 8 Reps", "Lưng", "Hít xà rộng tay.", imgPull, "Friday", "Intermediate", 0);
        insertEx(db, "Commando Pull-ups", "3 Sets x 10 Reps", "Lưng/Tay", "Hít xà dọc theo thanh xà.", imgPull, "Friday", "Intermediate", 0);
        insertEx(db, "Archer Rows", "3 Sets x 10 mỗi bên", "Lưng", "Kéo xà nghiêng một bên tay.", imgPull, "Friday", "Intermediate", 0);
        // Advanced
        insertEx(db, "Muscle-up", "4 Sets x 5 Reps", "Lưng/Tay", "Lên xà bùng nổ và đẩy người lên.", imgPull, "Friday", "Advanced", 1);
        insertEx(db, "Front Lever Tuck", "3 Sets x 15s", "Lưng/Core", "Giữ người song song mặt đất (co chân).", imgPull, "Friday", "Advanced", 1);
        insertEx(db, "Archer Pull-ups", "3 Sets x 6 mỗi bên", "Lưng", "Hít xà bắn cung.", imgPull, "Friday", "Advanced", 0);
        insertEx(db, "Weighted Pull-ups", "3 Sets x 8 Reps", "Lưng", "Hít xà kèm thêm tạ nặng.", imgPull, "Friday", "Advanced", 0);
        insertEx(db, "One Arm Chin-up Prep", "3 Sets x 5 mỗi bên", "Tay trước", "Tập bổ trợ cho hít xà 1 tay.", imgPull, "Friday", "Advanced", 0);

        // === SATURDAY - ACTIVE RECOVERY (Phục hồi) ===
        // Beginner
        insertEx(db, "Cat-Cow Stretch", "3 Sets x 15 Reps", "Giãn cơ", "Uốn lưng nhẹ nhàng.", imgFullBody, "Saturday", "Beginner", 0);
        insertEx(db, "Downward Dog", "3 Sets x 45s", "Giãn cơ", "Tư thế yoga chữ V ngược.", imgFullBody, "Saturday", "Beginner", 0);
        insertEx(db, "Child's Pose", "3 Sets x 60s", "Giãn cơ", "Tư thế em bé thư giãn.", imgFullBody, "Saturday", "Beginner", 0);
        insertEx(db, "Neck Rotations", "2 Sets x 10 mỗi bên", "Cổ", "Xoay cổ nhẹ nhàng.", imgFullBody, "Saturday", "Beginner", 0);
        insertEx(db, "Wrist Mobility", "3 Sets x 30s", "Cổ tay", "Xoay và làm nóng cổ tay.", imgFullBody, "Saturday", "Beginner", 0);
        // Intermediate
        insertEx(db, "Cobra Stretch", "3 Sets x 30s", "Giãn cơ", "Tư thế rắn hổ mang giãn bụng.", imgFullBody, "Saturday", "Intermediate", 1);
        insertEx(db, "Bridge Hold", "3 Sets x 30s", "Giãn cơ", "Tư thế cây cầu uốn lưng.", imgFullBody, "Saturday", "Intermediate", 1);
        insertEx(db, "Deep Squat Hold", "3 Sets x 60s", "Khớp hông", "Giữ tư thế squat sâu.", imgFullBody, "Saturday", "Intermediate", 0);
        insertEx(db, "Pigeon Pose", "3 Sets x 45s mỗi bên", "Mông/Hông", "Giãn cơ mông sâu.", imgFullBody, "Saturday", "Intermediate", 0);
        insertEx(db, "Butterfly Stretch", "3 Sets x 60s", "Háng", "Giãn cơ háng tư thế cánh bướm.", imgFullBody, "Saturday", "Intermediate", 0);
        // Advanced
        insertEx(db, "Pancake Stretch", "3 Sets x 60s", "Lưng/Chân", "Gập người khi ngồi dạng chân.", imgFullBody, "Saturday", "Advanced", 0);
        insertEx(db, "Frog Stretch", "3 Sets x 45s", "Hông", "Giãn hông tư thế con ếch.", imgFullBody, "Saturday", "Advanced", 0);
        insertEx(db, "Shoulder Dislocates", "3 Sets x 15 Reps", "Vai", "Xoay khớp vai với gậy hoặc dây.", imgFullBody, "Saturday", "Advanced", 1);
        insertEx(db, "Jefferson Curl", "3 Sets x 10 Reps", "Cột sống", "Cuộn lưng giãn cột sống.", imgFullBody, "Saturday", "Advanced", 0);
        insertEx(db, "Side Splits Prep", "3 Sets x 45s", "Chân", "Tập bổ trợ xoạc ngang.", imgFullBody, "Saturday", "Advanced", 0);

        // === SUNDAY - FULL BODY (Toàn thân) ===
        // Beginner
        insertEx(db, "Jumping Jacks", "3 Sets x 50 Reps", "Toàn thân", "Nhảy vung tay chân.", imgFullBody, "Sunday", "Beginner", 0);
        insertEx(db, "High Knees", "3 Sets x 40s", "Toàn thân", "Chạy nâng cao đùi tại chỗ.", imgFullBody, "Sunday", "Beginner", 0);
        insertEx(db, "Burpees (No Push-up)", "3 Sets x 12 Reps", "Toàn thân", "Burpees cơ bản lược bỏ chống đẩy.", imgFullBody, "Sunday", "Beginner", 0);
        insertEx(db, "Bear Crawls", "3 Sets x 20m", "Toàn thân", "Bò kiểu gấu bằng tay và chân.", imgFullBody, "Sunday", "Beginner", 0);
        insertEx(db, "Plank Jacks", "3 Sets x 30s", "Toàn thân", "Plank kết hợp nhảy tách chân.", imgFullBody, "Sunday", "Beginner", 0);
        // Intermediate
        insertEx(db, "Burpees", "3 Sets x 15 Reps", "Toàn thân", "Hít đất kết hợp bật nhảy.", imgFullBody, "Sunday", "Intermediate", 1);
        insertEx(db, "Tuck Jumps", "3 Sets x 12 Reps", "Toàn thân", "Bật nhảy co gối chạm ngực.", imgFullBody, "Sunday", "Intermediate", 0);
        insertEx(db, "Squat Thrusts", "3 Sets x 15 Reps", "Toàn thân", "Đẩy chân ra sau từ tư thế squat.", imgFullBody, "Sunday", "Intermediate", 0);
        insertEx(db, "Spiderman Push-ups", "3 Sets x 12 Reps", "Toàn thân", "Chống đẩy kết hợp co gối.", imgFullBody, "Sunday", "Intermediate", 0);
        insertEx(db, "Mountain Climber Twist", "3 Sets x 30s", "Toàn thân", "Leo núi chéo chân.", imgFullBody, "Sunday", "Intermediate", 0);
        // Advanced
        insertEx(db, "Navy Seal Burpees", "4 Sets x 10 Reps", "Toàn thân", "Biến thể Burpees cực khó với 3 lần chống đẩy.", imgFullBody, "Sunday", "Advanced", 1);
        insertEx(db, "Muscle-ups", "3 Sets x 5 Reps", "Toàn thân", "Lên xà bùng nổ.", imgFullBody, "Sunday", "Advanced", 1);
        insertEx(db, "Pistol Squat Jumps", "3 Sets x 6 mỗi bên", "Toàn thân", "Squat 1 chân kết hợp bật nhảy.", imgFullBody, "Sunday", "Advanced", 0);
        insertEx(db, "Handstand Walks", "3 Sets x 10m", "Toàn thân", "Đi bộ bằng tay.", imgFullBody, "Sunday", "Advanced", 1);
        insertEx(db, "Explosive Pull-ups", "3 Sets x 10 Reps", "Toàn thân", "Hít xà bùng nổ lên cao.", imgFullBody, "Sunday", "Advanced", 0);
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
