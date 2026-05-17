package com.nct.trenx;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private String currentDayName;
    private String workoutTitle;
    private TextView tvTodayDay, tvTodayTitle;
    private ImageView ivTodayWorkout;
    private TextView[] dayViews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // --- 1. ÁNH XẠ CÁC VIEW CƠ BẢN ---
        tvTodayDay = view.findViewById(R.id.tvTodayDay);
        tvTodayTitle = view.findViewById(R.id.tvTodayTitle);
        ivTodayWorkout = view.findViewById(R.id.ivTodayWorkout);
        CardView cardWorkout = view.findViewById(R.id.cardWorkout);
        CardView cardLikedWorkouts = view.findViewById(R.id.cardLikedWorkouts);
        CardView cardLikedPrograms = view.findViewById(R.id.cardLikedPrograms);

        // --- 2. KHỞI TẠO THANH CHỌN NGÀY (MO, TU, WE...) ---
        initDaySelector(view);

        // --- 3. MẶC ĐỊNH LOAD BÀI TẬP THEO THỨ THỰC TẾ HÔM NAY ---
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        updateWorkoutByDay(dayOfWeek);

        // --- 4. CÁC SỰ KIỆN CLICK CHUYỂN TRANG ---
        if (cardWorkout != null) {
            cardWorkout.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ExerciseActivity.class);
                intent.putExtra("DAY_NAME", currentDayName);
                intent.putExtra("WORKOUT_TITLE", workoutTitle);
                startActivity(intent);
            });
        }

        if (cardLikedWorkouts != null) {
            cardLikedWorkouts.setOnClickListener(v ->
                    startActivity(new Intent(getContext(), LikedWorkoutsActivity.class)));
        }

        if (cardLikedPrograms != null) {
            cardLikedPrograms.setOnClickListener(v ->
                    startActivity(new Intent(getContext(), LikedProgramsActivity.class)));
        }

        return view;
    }

    private void initDaySelector(View view) {
        dayViews = new TextView[]{
                null, // Index 0 bỏ qua
                view.findViewById(R.id.tvSu), // 1: Sunday
                view.findViewById(R.id.tvMo), // 2: Monday
                view.findViewById(R.id.tvTu), // 3: Tuesday
                view.findViewById(R.id.tvWe), // 4: Wednesday
                view.findViewById(R.id.tvTh), // 5: Thursday
                view.findViewById(R.id.tvFr), // 6: Friday
                view.findViewById(R.id.tvSa)  // 7: Saturday
        };

        for (int i = 1; i < dayViews.length; i++) {
            final int selectedDay = i;
            if (dayViews[i] != null) {
                dayViews[i].setOnClickListener(v -> updateWorkoutByDay(selectedDay));
            }
        }
    }

    private void updateWorkoutByDay(int dayOfWeek) {
        if (!isAdded()) return;

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
