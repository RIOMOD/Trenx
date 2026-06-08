package com.nct.trenx.utils;

import android.content.Context;
import com.nct.trenx.database.DatabaseHelper;
import com.nct.trenx.model.WorkoutHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ProgressDataProvider {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private final List<WorkoutHistory> allWorkouts;

    public ProgressDataProvider(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        int userId = PreferenceUtils.getUserId(context);
        this.allWorkouts = db.getWorkoutHistory(userId);
    }

    public List<WorkoutHistory> getWorkoutsByDate(String dateKey) {
        List<WorkoutHistory> result = new ArrayList<>();
        for (WorkoutHistory w : allWorkouts) {
            if (w.getDate().equals(dateKey)) {
                result.add(w);
            }
        }
        return result;
    }

    public Set<Integer> getWorkoutDaysInMonth(int year, int month) {
        Set<Integer> days = new HashSet<>();
        // month in java Calendar is 0-indexed, but strings in DB might be 1-indexed (01-12)
        String prefix = String.format(Locale.getDefault(), "%04d-%02d-", year, month + 1);
        for (WorkoutHistory w : allWorkouts) {
            if (w.getDate().startsWith(prefix)) {
                try {
                    String dayStr = w.getDate().substring(8);
                    days.add(Integer.parseInt(dayStr));
                } catch (Exception ignored) {}
            }
        }
        return days;
    }

    public int getCurrentStreak() {
        if (allWorkouts.isEmpty()) return 0;
        Set<String> allDates = new HashSet<>();
        for (WorkoutHistory w : allWorkouts) {
            allDates.add(w.getDate());
        }

        Calendar cal = Calendar.getInstance();
        int streak = 0;
        
        // Check today, if no workout check yesterday, then continue backwards
        String today = DATE_FORMAT.format(cal.getTime());
        if (!allDates.contains(today)) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        while (true) {
            String dateKey = DATE_FORMAT.format(cal.getTime());
            if (allDates.contains(dateKey)) {
                streak++;
                cal.add(Calendar.DAY_OF_MONTH, -1);
            } else {
                break;
            }
        }
        return streak;
    }

    public int getLongestStreak() {
        if (allWorkouts.isEmpty()) return 0;

        Set<String> allDates = new HashSet<>();
        for (WorkoutHistory w : allWorkouts) {
            allDates.add(w.getDate());
        }

        List<String> sortedDates = new ArrayList<>(allDates);
        java.util.Collections.sort(sortedDates);

        int maxStreak = 0;
        int currentStreak = 0;
        Calendar lastDate = null;

        for (String dateStr : sortedDates) {
            try {
                Calendar curr = Calendar.getInstance();
                curr.setTime(DATE_FORMAT.parse(dateStr));

                if (lastDate != null) {
                    Calendar nextExpected = (Calendar) lastDate.clone();
                    nextExpected.add(Calendar.DAY_OF_MONTH, 1);
                    
                    if (isSameDay(nextExpected, curr)) {
                        currentStreak++;
                    } else {
                        currentStreak = 1;
                    }
                } else {
                    currentStreak = 1;
                }
                lastDate = curr;
                maxStreak = Math.max(maxStreak, currentStreak);
            } catch (Exception ignored) {}
        }
        return maxStreak;
    }

    private boolean isSameDay(Calendar c1, Calendar c2) {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
               c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    public Map<String, Integer> getMuscleRecencyMap() {
        Map<String, Integer> result = new HashMap<>();
        Calendar today = Calendar.getInstance();

        for (WorkoutHistory w : allWorkouts) {
            try {
                Calendar workoutDate = Calendar.getInstance();
                workoutDate.setTime(DATE_FORMAT.parse(w.getDate()));

                long diffMillis = today.getTimeInMillis() - workoutDate.getTimeInMillis();
                int daysAgo = (int) (diffMillis / (1000 * 60 * 60 * 24));
                if (daysAgo < 0) daysAgo = 0;

                String[] muscles = w.getMuscleGroups().split(",");
                for (String muscle : muscles) {
                    String m = muscle.trim();
                    if (!result.containsKey(m) || result.get(m) > daysAgo) {
                        result.put(m, daysAgo);
                    }
                }
            } catch (Exception ignored) {}
        }
        return result;
    }

    public static String formatDateKey(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
    }

    public int getTotalWorkouts() {
        return allWorkouts.size();
    }
}
