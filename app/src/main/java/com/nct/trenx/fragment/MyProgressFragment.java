package com.nct.trenx.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nct.trenx.R;
import com.nct.trenx.model.WorkoutHistory;
import com.nct.trenx.utils.DateUtils;
import com.nct.trenx.utils.NavigationUtils;
import com.nct.trenx.utils.ProgressDataProvider;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MyProgressFragment extends Fragment {

    // Data
    private ProgressDataProvider dataProvider;
    private Calendar displayedMonth; // Tháng đang hiển thị trên calendar
    private Calendar selectedDate;   // Ngày đang được chọn

    // Tab switching
    private CardView tabPreviousWorkouts, tabPersonalHeatmap;
    private TextView tvTabPrevious, tvTabHeatmap;
    private LinearLayout contentPreviousWorkouts, contentPersonalHeatmap;

    // Calendar
    private LinearLayout calendarGrid;
    private TextView tvMonthYear;
    private ImageView btnPrevMonth, btnNextMonth;
    private TextView tvTodayBtn;

    // Streak
    private TextView tvCurrentStreak, tvLongestStreak;

    // Activity display
    private TextView tvDateTitle;
    private LinearLayout layoutEmptyState, layoutHistoryCard;
    private TextView tvHistoryDaysAgo, tvHistoryProgressPct, tvHistoryProgressText;
    private TextView tvHistoryTime, tvHistoryDifficulty;
    private CardView cardWorkoutHistory;

    // Heatmap
    private LinearLayout layoutMuscleList;
    private ImageView maskChest, maskAbs, maskLegs, maskBack, maskShoulders;

    public MyProgressFragment() {
        // Required empty constructor
    }

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

        return view;
    }

    // ===================== INIT VIEWS =====================

    private void initViews(View view) {
        // Tabs
        tabPreviousWorkouts = view.findViewById(R.id.tab_previous_workouts);
        tabPersonalHeatmap = view.findViewById(R.id.tab_personal_heatmap);
        tvTabPrevious = view.findViewById(R.id.tv_tab_previous);
        tvTabHeatmap = view.findViewById(R.id.tv_tab_heatmap);
        contentPreviousWorkouts = view.findViewById(R.id.content_previous_workouts);
        contentPersonalHeatmap = view.findViewById(R.id.content_personal_heatmap);

        // Calendar
        calendarGrid = view.findViewById(R.id.calendar_grid);
        tvMonthYear = view.findViewById(R.id.tv_month_year);
        btnPrevMonth = view.findViewById(R.id.btn_prev_month);
        btnNextMonth = view.findViewById(R.id.btn_next_month);
        tvTodayBtn = view.findViewById(R.id.tvTodayBtn);

        // Streak
        tvCurrentStreak = view.findViewById(R.id.tv_current_streak);
        tvLongestStreak = view.findViewById(R.id.tv_longest_streak);

        // Activity
        tvDateTitle = view.findViewById(R.id.tv_date_title);
        layoutEmptyState = view.findViewById(R.id.layout_empty_state);
        layoutHistoryCard = view.findViewById(R.id.layout_history_card);
        tvHistoryDaysAgo = view.findViewById(R.id.tv_history_days_ago);
        tvHistoryProgressPct = view.findViewById(R.id.tv_history_progress_pct);
        tvHistoryProgressText = view.findViewById(R.id.tv_history_progress_text);
        tvHistoryTime = view.findViewById(R.id.tv_history_time);
        tvHistoryDifficulty = view.findViewById(R.id.tv_history_difficulty);
        cardWorkoutHistory = view.findViewById(R.id.card_workout_history);

        // Heatmap
        layoutMuscleList = view.findViewById(R.id.layout_muscle_list);
        maskChest = view.findViewById(R.id.iv_mask_chest);
        maskAbs = view.findViewById(R.id.iv_mask_abs);
        maskLegs = view.findViewById(R.id.iv_mask_legs_front);
        maskBack = view.findViewById(R.id.iv_mask_back);
        maskShoulders = view.findViewById(R.id.iv_mask_shoulders);

        // Workout card click
        if (cardWorkoutHistory != null) {
            cardWorkoutHistory.setOnClickListener(v -> NavigationUtils.startTraining(requireContext()));
        }

        // Today button
        if (tvTodayBtn != null) {
            tvTodayBtn.setOnClickListener(v -> {
                displayedMonth = Calendar.getInstance();
                selectedDate = Calendar.getInstance();
                buildCalendar();
                updateActivitySection();
            });
        }
    }

    // ===================== TAB SWITCHING =====================

    private void setupTabSwitching() {
        tabPreviousWorkouts.setOnClickListener(v -> switchTab(true));
        tabPersonalHeatmap.setOnClickListener(v -> switchTab(false));
    }

    private void switchTab(boolean isPreviousWorkouts) {
        if (isPreviousWorkouts) {
            // Active: Previous Workouts
            tabPreviousWorkouts.setCardBackgroundColor(Color.WHITE);
            tabPreviousWorkouts.setCardElevation(dpToPx(1));
            tvTabPrevious.setTextColor(Color.BLACK);
            tvTabPrevious.setTypeface(null, Typeface.BOLD);

            tabPersonalHeatmap.setCardBackgroundColor(Color.TRANSPARENT);
            tabPersonalHeatmap.setCardElevation(0);
            tvTabHeatmap.setTextColor(Color.parseColor("#666666"));
            tvTabHeatmap.setTypeface(null, Typeface.NORMAL);

            contentPreviousWorkouts.setVisibility(View.VISIBLE);
            contentPersonalHeatmap.setVisibility(View.GONE);
        } else {
            // Active: Personal Heatmap
            tabPersonalHeatmap.setCardBackgroundColor(Color.WHITE);
            tabPersonalHeatmap.setCardElevation(dpToPx(1));
            tvTabHeatmap.setTextColor(Color.BLACK);
            tvTabHeatmap.setTypeface(null, Typeface.BOLD);

            tabPreviousWorkouts.setCardBackgroundColor(Color.TRANSPARENT);
            tabPreviousWorkouts.setCardElevation(0);
            tvTabPrevious.setTextColor(Color.parseColor("#666666"));
            tvTabPrevious.setTypeface(null, Typeface.NORMAL);

            contentPreviousWorkouts.setVisibility(View.GONE);
            contentPersonalHeatmap.setVisibility(View.VISIBLE);
        }
    }

    // ===================== CALENDAR =====================

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

        // Cập nhật title tháng
        tvMonthYear.setText(String.format(Locale.getDefault(), "%s %d",
                DateUtils.getMonthFullName(month), year));

        // Lấy thông tin tháng
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Tính ngày bắt đầu (Monday = 0, Sunday = 6)
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        // Convert: Calendar.MONDAY=2 -> offset=0, TUESDAY=3 -> 1, ..., SUNDAY=1 -> 6
        int startOffset;
        if (dayOfWeek == Calendar.SUNDAY) {
            startOffset = 6;
        } else {
            startOffset = dayOfWeek - Calendar.MONDAY;
        }

        // Ngày hôm nay
        Calendar today = Calendar.getInstance();
        int todayDay = today.get(Calendar.DAY_OF_MONTH);
        int todayMonth = today.get(Calendar.MONTH);
        int todayYear = today.get(Calendar.YEAR);

        // Ngày đang chọn
        int selectedDay = selectedDate.get(Calendar.DAY_OF_MONTH);
        int selectedMonth = selectedDate.get(Calendar.MONTH);
        int selectedYear = selectedDate.get(Calendar.YEAR);

        // Những ngày có workout
        Set<Integer> workoutDays = dataProvider.getWorkoutDaysInMonth(year, month);

        // Build grid theo hàng (mỗi hàng 7 ô)
        int totalCells = startOffset + daysInMonth;
        int rows = (int) Math.ceil(totalCells / 7.0);

        int dayCounter = 1;

        for (int row = 0; row < rows; row++) {
            LinearLayout rowLayout = new LinearLayout(requireContext());
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setWeightSum(7);

            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.topMargin = dpToPx(8);
            rowLayout.setLayoutParams(rowParams);

            for (int col = 0; col < 7; col++) {
                int cellIndex = row * 7 + col;

                if (cellIndex < startOffset || dayCounter > daysInMonth) {
                    // Ô trống
                    View emptyCell = new View(requireContext());
                    LinearLayout.LayoutParams emptyParams = new LinearLayout.LayoutParams(
                            0, dpToPx(48), 1f);
                    emptyCell.setLayoutParams(emptyParams);
                    rowLayout.addView(emptyCell);
                } else {
                    // Ô có ngày
                    int day = dayCounter;
                    boolean isToday = (day == todayDay && month == todayMonth && year == todayYear);
                    boolean isSelected = (day == selectedDay && month == selectedMonth && year == selectedYear);
                    boolean hasWorkout = workoutDays.contains(day);

                    View dayCell = createDayCell(day, isToday, isSelected, hasWorkout, year, month);
                    rowLayout.addView(dayCell);
                    dayCounter++;
                }
            }

            calendarGrid.addView(rowLayout);
        }
    }

    private View createDayCell(int day, boolean isToday, boolean isSelected,
                                boolean hasWorkout, int year, int month) {
        // Container: FrameLayout
        FrameLayout container = new FrameLayout(requireContext());
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                0, dpToPx(48), 1f);
        container.setLayoutParams(containerParams);
        container.setClickable(true);
        container.setFocusable(true);

        // Circle background (today or selected)
        if (isToday) {
            View circle = new View(requireContext());
            FrameLayout.LayoutParams circleParams = new FrameLayout.LayoutParams(
                    dpToPx(40), dpToPx(40));
            circleParams.gravity = Gravity.CENTER;
            circle.setLayoutParams(circleParams);
            circle.setBackgroundResource(R.drawable.bg_circle_today);
            container.addView(circle);
        } else if (isSelected) {
            View circle = new View(requireContext());
            FrameLayout.LayoutParams circleParams = new FrameLayout.LayoutParams(
                    dpToPx(40), dpToPx(40));
            circleParams.gravity = Gravity.CENTER;
            circle.setLayoutParams(circleParams);
            circle.setBackgroundResource(R.drawable.bg_circle_gray);
            container.addView(circle);
        }

        // Day number text
        TextView tvDay = new TextView(requireContext());
        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        tvDay.setLayoutParams(tvParams);
        tvDay.setGravity(Gravity.CENTER);
        tvDay.setText(String.valueOf(day));
        tvDay.setTextSize(14);

        if (isToday) {
            tvDay.setTextColor(Color.WHITE);
            tvDay.setTypeface(null, Typeface.BOLD);
        } else if (isSelected) {
            tvDay.setTextColor(Color.BLACK);
            tvDay.setTypeface(null, Typeface.BOLD);
        } else {
            tvDay.setTextColor(Color.parseColor("#555555"));
        }

        container.addView(tvDay);

        // Workout indicator dot
        if (hasWorkout && !isToday) {
            View dot = new View(requireContext());
            FrameLayout.LayoutParams dotParams = new FrameLayout.LayoutParams(
                    dpToPx(5), dpToPx(5));
            dotParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
            dotParams.bottomMargin = dpToPx(4);
            dot.setLayoutParams(dotParams);
            dot.setBackgroundResource(R.drawable.bg_dot_indicator);
            container.addView(dot);
        }

        // Click handler
        container.setOnClickListener(v -> {
            selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, month);
            selectedDate.set(Calendar.DAY_OF_MONTH, day);
            buildCalendar(); // Rebuild to update selection
            updateActivitySection();
        });

        return container;
    }

    // ===================== STREAK =====================

    private void setupStreaks() {
        int currentStreak = dataProvider.getCurrentStreak();
        int longestStreak = dataProvider.getLongestStreak();

        tvCurrentStreak.setText(currentStreak + " Days");
        tvLongestStreak.setText(longestStreak + " Days");
    }

    // ===================== DAILY ACTIVITY =====================

    private void updateActivitySection() {
        // Update date title
        tvDateTitle.setText(DateUtils.getDayDisplayText(selectedDate));

        // Query workouts for selected date
        String dateKey = DateUtils.formatDateKey(selectedDate);
        List<WorkoutHistory> workouts = dataProvider.getWorkoutsByDate(dateKey);

        if (workouts.isEmpty()) {
            layoutEmptyState.setVisibility(View.VISIBLE);
            layoutHistoryCard.setVisibility(View.GONE);
        } else {
            layoutEmptyState.setVisibility(View.GONE);
            layoutHistoryCard.setVisibility(View.VISIBLE);

            // Hiển thị workout đầu tiên
            WorkoutHistory w = workouts.get(0);

            // Tính "X days ago"
            Calendar today = Calendar.getInstance();
            long diffMillis = today.getTimeInMillis() - selectedDate.getTimeInMillis();
            int daysAgo = (int) (diffMillis / (1000 * 60 * 60 * 24));
            if (daysAgo == 0) {
                tvHistoryDaysAgo.setText("Today");
            } else if (daysAgo == 1) {
                tvHistoryDaysAgo.setText("1 day ago");
            } else {
                tvHistoryDaysAgo.setText(daysAgo + " days ago");
            }

            tvHistoryProgressPct.setText(w.getProgressPercent() + "%");
            tvHistoryProgressText.setText(w.getProgressPercent() + "% Progress");
            tvHistoryTime.setText(w.getFormattedDuration() + " Time");
            tvHistoryDifficulty.setText(w.getDifficulty());
        }
    }

    // ===================== MUSCLE HEATMAP =====================

    private void buildMuscleList() {
        layoutMuscleList.removeAllViews();

        Map<String, Integer> muscleRecency = dataProvider.getMuscleRecencyMap();

        String[] muscleNames = {"Bụng", "Ngực", "Chân", "Vai", "Lưng"};
        String[] muscleLabels = {"Abs (Core)", "Chest", "Legs", "Shoulders", "Back"};

        for (int i = 0; i < muscleNames.length; i++) {
            String muscleName = muscleNames[i];
            String label = muscleLabels[i];
            Integer daysAgo = muscleRecency.get(muscleName);

            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.topMargin = dpToPx(12);
            row.setLayoutParams(rowParams);
            row.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
            row.setBackgroundColor(Color.WHITE);

            // Color indicator
            View colorDot = new View(requireContext());
            LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(
                    dpToPx(12), dpToPx(12));
            colorDot.setLayoutParams(dotParams);

            if (daysAgo != null) {
                if (daysAgo <= 1) {
                    colorDot.setBackgroundResource(R.drawable.bg_dot_red);
                } else if (daysAgo <= 3) {
                    colorDot.setBackgroundResource(R.drawable.bg_dot_orange);
                } else {
                    colorDot.setBackgroundResource(R.drawable.bg_dot_gray_dark);
                }
            } else {
                colorDot.setBackgroundResource(R.drawable.bg_dot_gray_dark);
            }
            row.addView(colorDot);

            // Muscle label
            TextView tvLabel = new TextView(requireContext());
            LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            labelParams.leftMargin = dpToPx(12);
            tvLabel.setLayoutParams(labelParams);
            tvLabel.setText(label + " (" + muscleName + ")");
            tvLabel.setTextColor(Color.BLACK);
            tvLabel.setTextSize(15);
            tvLabel.setTypeface(null, Typeface.BOLD);
            row.addView(tvLabel);

            // Days ago text
            TextView tvDaysAgo = new TextView(requireContext());
            tvDaysAgo.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            if (daysAgo != null) {
                if (daysAgo == 0) {
                    tvDaysAgo.setText("Today");
                } else if (daysAgo == 1) {
                    tvDaysAgo.setText("1 day ago");
                } else {
                    tvDaysAgo.setText(daysAgo + " days ago");
                }
            } else {
                tvDaysAgo.setText("Not trained");
            }
            tvDaysAgo.setTextColor(Color.parseColor("#666666"));
            tvDaysAgo.setTextSize(13);
            row.addView(tvDaysAgo);

            // Apply color to body masks
            ImageView targetMask = null;
            if ("Ngực".equals(muscleName)) targetMask = maskChest;
            else if ("Bụng".equals(muscleName)) targetMask = maskAbs;
            else if ("Chân".equals(muscleName)) targetMask = maskLegs;
            else if ("Lưng".equals(muscleName)) targetMask = maskBack;
            else if ("Vai".equals(muscleName)) targetMask = maskShoulders;

            if (targetMask != null) {
                if (daysAgo != null) {
                    if (daysAgo <= 1) {
                        targetMask.setColorFilter(Color.parseColor("#E53935"));
                    } else if (daysAgo <= 3) {
                        targetMask.setColorFilter(Color.parseColor("#FB8C00"));
                    } else {
                        targetMask.setColorFilter(Color.parseColor("#9E9E9E"));
                    }
                } else {
                    targetMask.setColorFilter(Color.parseColor("#9E9E9E"));
                }
            }

            layoutMuscleList.addView(row);
        }
    }

    // ===================== UTILITY =====================

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
