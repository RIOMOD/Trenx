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
import com.nct.trenx.model.WorkoutDayInfo;
import com.nct.trenx.utils.DaySelectorUiHelper;
import com.nct.trenx.utils.ImageUtils;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.WorkoutUtils;

import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private String currentDayName;
    private String workoutTitle;
    private TextView tvTodayDay, tvTodayTitle;
    private ImageView ivTodayWorkout;
    private TextView[] dayViews;

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

        initDaySelector(view);

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        updateWorkoutByDay(dayOfWeek);

        if (cardWorkout != null) {
            cardWorkout.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ExerciseActivity.class);
                intent.putExtra(IntentExtras.DAY_NAME, currentDayName);
                intent.putExtra(IntentExtras.WORKOUT_TITLE, workoutTitle);
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
        currentDayName = schedule.getDayName();
        workoutTitle = schedule.getWorkoutTitle();

        tvTodayDay.setText(currentDayName.toUpperCase());
        tvTodayTitle.setText(workoutTitle);

        if (ivTodayWorkout != null) {
            ImageUtils.loadExerciseThumb(this, ivTodayWorkout, schedule.getImageUrl(),
                    R.color.surface_dark);
        }
    }
}
