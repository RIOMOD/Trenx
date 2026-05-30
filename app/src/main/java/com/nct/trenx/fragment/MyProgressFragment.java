package com.nct.trenx.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nct.trenx.R;
import com.nct.trenx.activity.SettingsActivity;
import com.nct.trenx.model.WorkoutHistory;
import com.nct.trenx.utils.DateUtils;
import com.nct.trenx.utils.NavigationUtils;
import com.nct.trenx.utils.ProgressDataProvider;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyProgressFragment extends Fragment {

    private ProgressDataProvider dataProvider;
    private Calendar displayedMonth; 
    private Calendar selectedDate;   

    private ImageView btnSettings;
    private CardView tabPreviousWorkouts, tabPersonalHeatmap;
    private TextView tvTabPrevious, tvTabHeatmap;
    private LinearLayout contentPreviousWorkouts, contentPersonalHeatmap;

    private LinearLayout calendarGrid;
    private TextView tvMonthYear;
    private ImageView btnPrevMonth, btnNextMonth;
    private TextView tvTodayBtn;

    private TextView tvCurrentStreak, tvLongestStreak;
    private TextView tvDateTitle;
    private LinearLayout layoutEmptyState, layoutHistoryCard;
    private TextView tvHistoryDaysAgo, tvHistoryProgressPct, tvHistoryProgressText;
    private TextView tvHistoryTime, tvHistoryDifficulty;
    private CardView cardWorkoutHistory;

    private TextView tvTotalWorkouts, tvFollowers, tvFollowing, tvUserName, tvUserLevel;

    private LinearLayout layoutMuscleList;
    private ImageView maskChest, maskAbs, maskLegs, maskBack, maskShoulders;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        dataProvider = new ProgressDataProvider();
        displayedMonth = Calendar.getInstance();
        selectedDate = Calendar.getInstance();

        initViews(view);
        setupTabSwitching();
        setupCalendarNavigation();
        setupStreaks();
        buildCalendar();
        updateActivitySection();
        buildMuscleList();
        updateUserStats();

        return view;
    }

    private void initViews(View view) {
        btnSettings = view.findViewById(R.id.btn_settings);
        tabPreviousWorkouts = view.findViewById(R.id.tab_previous_workouts);
        tabPersonalHeatmap = view.findViewById(R.id.tab_personal_heatmap);
        tvTabPrevious = view.findViewById(R.id.tv_tab_previous);
        tvTabHeatmap = view.findViewById(R.id.tv_tab_heatmap);
        contentPreviousWorkouts = view.findViewById(R.id.content_previous_workouts);
        contentPersonalHeatmap = view.findViewById(R.id.content_personal_heatmap);

        calendarGrid = view.findViewById(R.id.calendar_grid);
        tvMonthYear = view.findViewById(R.id.tv_month_year);
        btnPrevMonth = view.findViewById(R.id.btn_prev_month);
        btnNextMonth = view.findViewById(R.id.btn_next_month);
        tvTodayBtn = view.findViewById(R.id.tvTodayBtn);

        tvCurrentStreak = view.findViewById(R.id.tv_current_streak);
        tvLongestStreak = view.findViewById(R.id.tv_longest_streak);

        tvDateTitle = view.findViewById(R.id.tv_date_title);
        layoutEmptyState = view.findViewById(R.id.layout_empty_state);
        layoutHistoryCard = view.findViewById(R.id.layout_history_card);
        tvHistoryDaysAgo = view.findViewById(R.id.tv_history_days_ago);
        tvHistoryProgressPct = view.findViewById(R.id.tv_history_progress_pct);
        tvHistoryProgressText = view.findViewById(R.id.tv_history_progress_text);
        tvHistoryTime = view.findViewById(R.id.tv_history_time);
        tvHistoryDifficulty = view.findViewById(R.id.tv_history_difficulty);
        cardWorkoutHistory = view.findViewById(R.id.card_workout_history);

        tvTotalWorkouts = view.findViewById(R.id.tvTotalWorkouts);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvFollowing = view.findViewById(R.id.tvFollowing);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserLevel = view.findViewById(R.id.tvUserLevel);

        layoutMuscleList = view.findViewById(R.id.layout_muscle_list);
        maskChest = view.findViewById(R.id.iv_mask_chest);
        maskAbs = view.findViewById(R.id.iv_mask_abs);
        maskLegs = view.findViewById(R.id.iv_mask_legs_front);
        maskBack = view.findViewById(R.id.iv_mask_back);
        maskShoulders = view.findViewById(R.id.iv_mask_shoulders);

        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> startActivity(new Intent(requireActivity(), SettingsActivity.class)));
        }

        if (cardWorkoutHistory != null) {
            cardWorkoutHistory.setOnClickListener(v -> NavigationUtils.startTraining(requireContext()));
        }

        if (tvTodayBtn != null) {
            tvTodayBtn.setOnClickListener(v -> {
                displayedMonth = Calendar.getInstance();
                selectedDate = Calendar.getInstance();
                buildCalendar();
                updateActivitySection();
            });
        }
    }

    private void setupTabSwitching() {
        tabPreviousWorkouts.setOnClickListener(v -> switchTab(true));
        tabPersonalHeatmap.setOnClickListener(v -> switchTab(false));
    }

    private void switchTab(boolean isPreviousWorkouts) {
        int colorSurface = getThemeColor(android.R.attr.colorBackground);
        int textColorPrimary = getThemeColor(android.R.attr.textColorPrimary);
        int textColorSecondary = getThemeColor(android.R.attr.textColorSecondary);

        if (isPreviousWorkouts) {
            tabPreviousWorkouts.setCardBackgroundColor(colorSurface);
            tabPreviousWorkouts.setCardElevation(dpToPx(2));
            tvTabPrevious.setTextColor(textColorPrimary);
            tvTabPrevious.setTypeface(null, Typeface.BOLD);

            tabPersonalHeatmap.setCardBackgroundColor(Color.TRANSPARENT);
            tabPersonalHeatmap.setCardElevation(0);
            tvTabHeatmap.setTextColor(textColorSecondary);
            tvTabHeatmap.setTypeface(null, Typeface.NORMAL);

            contentPreviousWorkouts.setVisibility(View.VISIBLE);
            contentPersonalHeatmap.setVisibility(View.GONE);
        } else {
            tabPersonalHeatmap.setCardBackgroundColor(colorSurface);
            tabPersonalHeatmap.setCardElevation(dpToPx(2));
            tvTabHeatmap.setTextColor(textColorPrimary);
            tvTabHeatmap.setTypeface(null, Typeface.BOLD);

            tabPreviousWorkouts.setCardBackgroundColor(Color.TRANSPARENT);
            tabPreviousWorkouts.setCardElevation(0);
            tvTabPrevious.setTextColor(textColorSecondary);
            tvTabPrevious.setTypeface(null, Typeface.NORMAL);

            contentPreviousWorkouts.setVisibility(View.GONE);
            contentPersonalHeatmap.setVisibility(View.VISIBLE);
        }
    }

    private int getThemeColor(@AttrRes int attrRes) {
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }

    private void setupCalendarNavigation() {
        btnPrevMonth.setOnClickListener(v -> {
            displayedMonth.add(Calendar.MONTH, -1);
            buildCalendar();
        });
        btnNextMonth.setOnClickListener(v -> {
            displayedMonth.add(Calendar.MONTH, 1);
            buildCalendar();
        });
    }

    private void buildCalendar() {
        calendarGrid.removeAllViews();
        int year = displayedMonth.get(Calendar.YEAR);
        int month = displayedMonth.get(Calendar.MONTH);
        tvMonthYear.setText(DateUtils.getMonthFullName(month) + " " + year);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int startOffset = (dayOfWeek == Calendar.SUNDAY) ? 6 : dayOfWeek - Calendar.MONDAY;

        Calendar today = Calendar.getInstance();
        int todayDay = today.get(Calendar.DAY_OF_MONTH), todayMonth = today.get(Calendar.MONTH), todayYear = today.get(Calendar.YEAR);
        int selectedDay = selectedDate.get(Calendar.DAY_OF_MONTH), selectedMonth = selectedDate.get(Calendar.MONTH), selectedYear = selectedDate.get(Calendar.YEAR);

        Set<Integer> workoutDays = dataProvider.getWorkoutDaysInMonth(year, month);
        int dayCounter = 1;

        for (int row = 0; row < 6; row++) {
            LinearLayout rowLayout = new LinearLayout(requireContext());
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setWeightSum(7);
            
            for (int col = 0; col < 7; col++) {
                int cellIndex = row * 7 + col;
                if (cellIndex < startOffset || dayCounter > daysInMonth) {
                    rowLayout.addView(new View(requireContext()), new LinearLayout.LayoutParams(0, dpToPx(48), 1f));
                } else {
                    int day = dayCounter;
                    boolean isToday = (day == todayDay && month == todayMonth && year == todayYear);
                    boolean isSelected = (day == selectedDay && month == selectedMonth && year == selectedYear);
                    rowLayout.addView(createDayCell(day, isToday, isSelected, workoutDays.contains(day), year, month));
                    dayCounter++;
                }
            }
            calendarGrid.addView(rowLayout);
            if (dayCounter > daysInMonth) break;
        }
    }

    private View createDayCell(int day, boolean isToday, boolean isSelected, boolean hasWorkout, int year, int month) {
        FrameLayout container = new FrameLayout(requireContext());
        container.setLayoutParams(new LinearLayout.LayoutParams(0, dpToPx(48), 1f));
        container.setClickable(true);
        container.setFocusable(true);

        if (isToday) {
            View circle = new View(requireContext());
            circle.setLayoutParams(new FrameLayout.LayoutParams(dpToPx(40), dpToPx(40), Gravity.CENTER));
            circle.setBackgroundResource(R.drawable.bg_circle_today);
            container.addView(circle);
        } else if (isSelected) {
            View circle = new View(requireContext());
            circle.setLayoutParams(new FrameLayout.LayoutParams(dpToPx(40), dpToPx(40), Gravity.CENTER));
            circle.setBackgroundResource(R.drawable.bg_circle_gray);
            circle.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getThemeColor(android.R.attr.textColorSecondary)));
            container.addView(circle);
        }

        TextView tvDay = new TextView(requireContext());
        tvDay.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        tvDay.setGravity(Gravity.CENTER);
        tvDay.setText(String.valueOf(day));
        tvDay.setTextSize(14);
        tvDay.setTextColor(isToday ? Color.WHITE : getThemeColor(isSelected ? android.R.attr.colorBackground : android.R.attr.textColorPrimary));
        if (isToday || isSelected) tvDay.setTypeface(null, Typeface.BOLD);
        container.addView(tvDay);

        container.setOnClickListener(v -> {
            selectedDate.set(year, month, day);
            buildCalendar(); 
            updateActivitySection();
        });
        return container;
    }

    private void updateActivitySection() {
        tvDateTitle.setText(DateUtils.getDayDisplayText(getContext(), selectedDate));
        List<WorkoutHistory> workouts = dataProvider.getWorkoutsByDate(DateUtils.formatDateKey(selectedDate));

        if (workouts.isEmpty()) {
            layoutEmptyState.setVisibility(View.VISIBLE);
            layoutHistoryCard.setVisibility(View.GONE);
        } else {
            layoutEmptyState.setVisibility(View.GONE);
            layoutHistoryCard.setVisibility(View.VISIBLE);
            WorkoutHistory w = workouts.get(0);
            tvHistoryProgressPct.setText(w.getProgressPercent() + "%");
            tvHistoryProgressText.setText(getString(R.string.progress_pct_format, w.getProgressPercent()));
            tvHistoryTime.setText(getString(R.string.time_spent_format, w.getFormattedDuration()));
            tvHistoryDifficulty.setText(w.getDifficulty());
        }
    }

    private void buildMuscleList() {
        layoutMuscleList.removeAllViews();
        Map<String, Integer> muscleRecency = dataProvider.getMuscleRecencyMap();
        String[] muscleNames = {"Bụng", "Ngực", "Chân", "Vai", "Lưng"};
        String[] muscleLabels = {getString(R.string.muscle_abs), getString(R.string.muscle_chest), getString(R.string.muscle_legs), getString(R.string.muscle_shoulders), getString(R.string.muscle_back)};

        for (int i = 0; i < muscleNames.length; i++) {
            String muscleName = muscleNames[i];
            Integer daysAgo = muscleRecency.get(muscleName);
            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -1);
            lp.topMargin = dpToPx(12);
            row.setLayoutParams(lp);
            row.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
            row.setBackgroundResource(R.drawable.bg_rounded_white);
            row.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getThemeColor(android.R.attr.colorBackground)));

            View dot = new View(requireContext());
            dot.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(12), dpToPx(12)));
            dot.setBackgroundResource(daysAgo != null && daysAgo <= 1 ? R.drawable.bg_dot_red : (daysAgo != null && daysAgo <= 3 ? R.drawable.bg_dot_orange : R.drawable.bg_dot_gray_dark));
            row.addView(dot);

            TextView tv = new TextView(requireContext());
            tv.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1f));
            tv.setPadding(dpToPx(12), 0, 0, 0);
            tv.setText(muscleLabels[i]);
            tv.setTextColor(getThemeColor(android.R.attr.textColorPrimary));
            tv.setTextSize(15);
            tv.setTypeface(null, Typeface.BOLD);
            row.addView(tv);

            layoutMuscleList.addView(row);
        }
    }

    private void updateUserStats() {
        tvTotalWorkouts.setText(getString(R.string.workouts_count_format, 936));
        tvFollowers.setText(getString(R.string.followers_count_format, 16));
        tvFollowing.setText(getString(R.string.following_count_format, 7));
        tvUserName.setText("Nguyen Cong Tru");
        tvUserLevel.setText(getString(R.string.intermediate) + "  •  " + getString(R.string.strength_building));
    }

    private void setupStreaks() {
        tvCurrentStreak.setText(getString(R.string.days_count_format, dataProvider.getCurrentStreak()));
        tvLongestStreak.setText(getString(R.string.days_count_format, dataProvider.getLongestStreak()));
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}
