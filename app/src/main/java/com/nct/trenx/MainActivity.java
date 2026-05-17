package com.nct.trenx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private String currentDayName;
    private String workoutTitle;
    private TextView tvTodayDay, tvTodayTitle;
    private ImageView ivTodayWorkout;
    private TextView[] dayViews; // Mảng quản lý 7 TextView ngày

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- 1. ÁNH XẠ CÁC VIEW CƠ BẢN ---
        tvTodayDay = findViewById(R.id.tvTodayDay);
        tvTodayTitle = findViewById(R.id.tvTodayTitle);
        ivTodayWorkout = findViewById(R.id.ivTodayWorkout);
        CardView cardWorkout = findViewById(R.id.cardWorkout);
        CardView cardLikedWorkouts = findViewById(R.id.cardLikedWorkouts);
        CardView cardLikedPrograms = findViewById(R.id.cardLikedPrograms);

        // --- 2. SETUP BOTTOM NAVIGATION (FIX LỖI CỤC TRẮNG) ---
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_dashboard); // Luôn sáng ở Dashboard khi mở App
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_dashboard) return true;
                // Xử lý các mục khác ở đây
                return true;
            });
        }

        // --- 3. KHỞI TẠO THANH CHỌN NGÀY (MO, TU, WE...) ---
        initDaySelector();

        // --- 4. MẶC ĐỊNH LOAD BÀI TẬP THEO THỨ THỰC TẾ HÔM NAY ---
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        updateWorkoutByDay(dayOfWeek);

        // --- 5. CÁC SỰ KIỆN CLICK CHUYỂN TRANG ---

        // Vào chi tiết bài tập đang hiển thị
        if (cardWorkout != null) {
            cardWorkout.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
                intent.putExtra("DAY_NAME", currentDayName);
                intent.putExtra("WORKOUT_TITLE", workoutTitle);
                startActivity(intent);
            });
        }

        // Vào trang Liked Workouts
        if (cardLikedWorkouts != null) {
            cardLikedWorkouts.setOnClickListener(v ->
                    startActivity(new Intent(MainActivity.this, LikedWorkoutsActivity.class)));
        }

        // Vào trang Liked Programs
        if (cardLikedPrograms != null) {
            cardLikedPrograms.setOnClickListener(v ->
                    startActivity(new Intent(MainActivity.this, LikedProgramsActivity.class)));
        }
    }

    // Hàm ánh xạ 7 TextView ngày và gắn sự kiện click
    private void initDaySelector() {
        dayViews = new TextView[]{
                null, // Index 0 bỏ qua
                findViewById(R.id.tvSu), // 1: Sunday
                findViewById(R.id.tvMo), // 2: Monday
                findViewById(R.id.tvTu), // 3: Tuesday
                findViewById(R.id.tvWe), // 4: Wednesday
                findViewById(R.id.tvTh), // 5: Thursday
                findViewById(R.id.tvFr), // 6: Friday
                findViewById(R.id.tvSa)  // 7: Saturday
        };

        for (int i = 1; i < dayViews.length; i++) {
            final int selectedDay = i;
            if (dayViews[i] != null) {
                dayViews[i].setOnClickListener(v -> updateWorkoutByDay(selectedDay));
            }
        }
    }

    // Hàm cập nhật toàn bộ UI (Card + Thanh lịch) theo ngày được chọn
    private void updateWorkoutByDay(int dayOfWeek) {
        // A. Cập nhật trạng thái "Cục trắng" trên thanh lịch
        for (int i = 1; i < dayViews.length; i++) {
            if (dayViews[i] != null) {
                if (i == dayOfWeek) {
                    dayViews[i].setBackgroundResource(R.drawable.bg_selected_day);
                    dayViews[i].setTextColor(getResources().getColor(android.R.color.black));
                } else {
                    dayViews[i].setBackground(null);
                    dayViews[i].setTextColor(getResources().getColor(R.color.text_white));
                }
            }
        }

        // B. Cập nhật Nội dung bài tập (Ảnh + Chữ)
        String sessionImageUrl;
        String coreImgUrl = "https://i.ytimg.com/vi/pd3-q2U7JXk/maxresdefault.jpg";
        String chestImgUrl = "https://i.ytimg.com/vi/srj94JCeuWw/maxresdefault.jpg";
        String legsImgUrl = "https://thenx.com/cdn/shop/articles/legsandglutes.jpg?v=1610420442";
        String fullBodyImgUrl = "https://i.ytimg.com/vi/G5nxGTFBauM/maxresdefault.jpg";

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                currentDayName = "Monday"; workoutTitle = "Core Shredder"; sessionImageUrl = coreImgUrl; break;
            case Calendar.TUESDAY:
                currentDayName = "Tuesday"; workoutTitle = "Chest & Triceps"; sessionImageUrl = chestImgUrl; break;
            case Calendar.WEDNESDAY:
                currentDayName = "Wednesday"; workoutTitle = "Quad Crusher"; sessionImageUrl = legsImgUrl; break;
            case Calendar.THURSDAY:
                currentDayName = "Thursday"; workoutTitle = "Shoulder Power"; sessionImageUrl = chestImgUrl; break;
            case Calendar.FRIDAY:
                currentDayName = "Friday"; workoutTitle = "Back & Biceps"; sessionImageUrl = fullBodyImgUrl; break;
            case Calendar.SATURDAY:
                currentDayName = "Saturday"; workoutTitle = "Active Recovery"; sessionImageUrl = fullBodyImgUrl; break;
            default: // Sunday
                currentDayName = "Sunday"; workoutTitle = "Full Body Master"; sessionImageUrl = fullBodyImgUrl; break;
        }

        tvTodayDay.setText(currentDayName.toUpperCase());
        tvTodayTitle.setText(workoutTitle);

        if (ivTodayWorkout != null) {
            Glide.with(this)
                    .load(sessionImageUrl)
                    .centerCrop()
                    .placeholder(R.color.surface_dark)
                    .into(ivTodayWorkout);
        }
    }
}