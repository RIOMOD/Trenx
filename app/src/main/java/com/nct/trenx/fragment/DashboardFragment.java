package com.nct.trenx.fragment;

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

import com.nct.trenx.R;
import com.nct.trenx.activity.ExerciseActivity;
import com.nct.trenx.activity.LikedProgramsActivity;
import com.nct.trenx.activity.LikedWorkoutsActivity;
import com.nct.trenx.activity.MainActivity;
import com.nct.trenx.activity.NotificationsActivity;
import com.nct.trenx.activity.SearchActivity;
import com.nct.trenx.model.WorkoutDayInfo;
import com.nct.trenx.utils.DaySelectorUiHelper;
import com.nct.trenx.utils.ImageUtils;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.NotificationStore;
import com.nct.trenx.utils.WorkoutUtils;

import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private int currentDayResId;
    private int workoutTitleResId;
    private String currentDbDayKey;
    private TextView tvTodayDay, tvTodayTitle;
    private ImageView ivTodayWorkout;
    private TextView[] dayViews;
    private TextView tvNotificationBadge;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvTodayDay = view.findViewById(R.id.tv_today_day);
        tvTodayTitle = view.findViewById(R.id.tv_today_title);
        ivTodayWorkout = view.findViewById(R.id.iv_today_workout);
        CardView cardWorkout = view.findViewById(R.id.card_today_workout);
        CardView cardLikedWorkouts = view.findViewById(R.id.card_liked_workouts);
        CardView cardLikedPrograms = view.findViewById(R.id.card_liked_programs);
        
        ImageView ivSearch = view.findViewById(R.id.ivSearch);
        View btnNotifications = view.findViewById(R.id.btn_notifications);
        tvNotificationBadge = view.findViewById(R.id.tv_notification_badge);
        TextView btnCalendar = view.findViewById(R.id.btn_calendar);

        initDaySelector(view);

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        updateWorkoutByDay(dayOfWeek);

        if (cardWorkout != null) {
            cardWorkout.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ExerciseActivity.class);
                intent.putExtra(IntentExtras.DAY_NAME, currentDbDayKey);
                intent.putExtra(IntentExtras.WORKOUT_TITLE, getString(workoutTitleResId));
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

        if (ivSearch != null) {
            ivSearch.setOnClickListener(v -> 
                    startActivity(new Intent(getContext(), SearchActivity.class)));
        }

        if (btnNotifications != null) {
            btnNotifications.setOnClickListener(v -> 
                    startActivity(new Intent(getContext(), NotificationsActivity.class)));
        }

        if (btnCalendar != null) {
            btnCalendar.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(IntentExtras.TARGET_FRAGMENT, R.id.nav_progress);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNotificationBadge();
    }

    private void updateNotificationBadge() {
        if (tvNotificationBadge == null) return;
        
        int unreadCount = NotificationStore.getUnreadCount();
        if (unreadCount > 0) {
            tvNotificationBadge.setVisibility(View.VISIBLE);
            tvNotificationBadge.setText(String.valueOf(unreadCount));
        } else {
            tvNotificationBadge.setVisibility(View.GONE);
        }
    }

    private void initDaySelector(View view) {
        dayViews = new TextView[]{
                null,
                view.findViewById(R.id.tv_day_sun),
                view.findViewById(R.id.tv_day_mon),
                view.findViewById(R.id.tv_day_tue),
                view.findViewById(R.id.tv_day_wed),
                view.findViewById(R.id.tv_day_thu),
                view.findViewById(R.id.tv_day_fri),
                view.findViewById(R.id.tv_day_sat)
        };

        for (int i = 1; i < dayViews.length; i++) {
            final int selectedDay = i;
            if (dayViews[i] != null) {
                dayViews[i].setOnClickListener(v -> updateWorkoutByDay(selectedDay));
            }
        }
    }

    private void updateWorkoutByDay(int dayOfWeek) {
        if (!isAdded()) {
            return;
        }

        DaySelectorUiHelper.applySelectedDay(requireContext(), dayViews, dayOfWeek);

        WorkoutDayInfo schedule = WorkoutUtils.getScheduleFor(dayOfWeek);
        currentDayResId = schedule.getDayNameResId();
        workoutTitleResId = schedule.getWorkoutTitleResId();
        currentDbDayKey = schedule.getDbDayKey();

        tvTodayDay.setText(getString(currentDayResId).toUpperCase());
        tvTodayTitle.setText(getString(workoutTitleResId));

        if (ivTodayWorkout != null) {
            ImageUtils.loadExerciseThumb(this, ivTodayWorkout, schedule.getImageUrl(),
                    R.color.surface_dark);
        }
    }
}
