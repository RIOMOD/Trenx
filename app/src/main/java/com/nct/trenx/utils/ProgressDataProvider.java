package com.nct.trenx.utils;

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

/**
 * Cung cấp dữ liệu giả cho trang My Progress.
 * Sau này có thể thay bằng query DB thật.
 */
public class ProgressDataProvider {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private final List<WorkoutHistory> allWorkouts;

    public ProgressDataProvider() {
        allWorkouts = generateDemoData();
    }

    /**
     * Tạo dữ liệu demo: khoảng 15 buổi tập trong tháng 5/2026.
     */
    private List<WorkoutHistory> generateDemoData() {
        List<WorkoutHistory> list = new ArrayList<>();

        // Tuần 1 (5-9 May)
        list.add(new WorkoutHistory("2026-05-05", "Monday", "Intermediate",
                3, 3, 1800, "Bụng"));
        list.add(new WorkoutHistory("2026-05-06", "Tuesday", "Intermediate",
                4, 5, 2400, "Ngực"));
        list.add(new WorkoutHistory("2026-05-07", "Wednesday", "Intermediate",
                3, 3, 1500, "Chân"));
        list.add(new WorkoutHistory("2026-05-08", "Thursday", "Intermediate",
                2, 3, 1200, "Vai"));
        list.add(new WorkoutHistory("2026-05-09", "Friday", "Intermediate",
                4, 4, 2100, "Lưng"));

        // Tuần 2 (12-16 May)
        list.add(new WorkoutHistory("2026-05-12", "Monday", "Intermediate",
                3, 3, 1650, "Bụng"));
        list.add(new WorkoutHistory("2026-05-13", "Tuesday", "Intermediate",
                5, 5, 2700, "Ngực"));
        list.add(new WorkoutHistory("2026-05-14", "Wednesday", "Intermediate",
                3, 3, 1400, "Chân"));
        list.add(new WorkoutHistory("2026-05-15", "Thursday", "Intermediate",
                3, 3, 1350, "Vai"));
        list.add(new WorkoutHistory("2026-05-16", "Friday", "Intermediate",
                4, 4, 2000, "Lưng"));

        // Tuần 3 (19-23 May) — streak vẫn đang liên tục
        list.add(new WorkoutHistory("2026-05-19", "Monday", "Intermediate",
                3, 3, 1700, "Bụng"));
        list.add(new WorkoutHistory("2026-05-20", "Tuesday", "Intermediate",
                4, 5, 2300, "Ngực"));

        // Tuần gần đây (26-28 May) — để tính streak từ hôm nay
        list.add(new WorkoutHistory("2026-05-26", "Monday", "Intermediate",
                3, 3, 1600, "Bụng"));
        list.add(new WorkoutHistory("2026-05-27", "Tuesday", "Intermediate",
                5, 5, 2500, "Ngực"));
        list.add(new WorkoutHistory("2026-05-28", "Wednesday", "Intermediate",
                3, 3, 1450, "Chân"));

        return list;
    }

    /**
     * Lấy danh sách workout theo ngày (format "yyyy-MM-dd").
     */
    public List<WorkoutHistory> getWorkoutsByDate(String dateKey) {
        List<WorkoutHistory> result = new ArrayList<>();
        for (WorkoutHistory w : allWorkouts) {
            if (w.getDate().equals(dateKey)) {
                result.add(w);
            }
        }
        return result;
    }

    /**
     * Lấy Set các ngày có workout trong tháng/năm chỉ định.
     * Trả về Set chứa số ngày (1-31).
     */
    public Set<Integer> getWorkoutDaysInMonth(int year, int month) {
        Set<Integer> days = new HashSet<>();
        String prefix = String.format(Locale.getDefault(), "%04d-%02d-", year, month + 1);
        for (WorkoutHistory w : allWorkouts) {
            if (w.getDate().startsWith(prefix)) {
                String dayStr = w.getDate().substring(8);
                days.add(Integer.parseInt(dayStr));
            }
        }
        return days;
    }

    /**
     * Tính Current Streak: số ngày liên tiếp tập từ hôm nay trở về trước.
     */
    public int getCurrentStreak() {
        Set<String> allDates = new HashSet<>();
        for (WorkoutHistory w : allWorkouts) {
            allDates.add(w.getDate());
        }

        Calendar cal = Calendar.getInstance();
        int streak = 0;

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

    /**
     * Tính Longest Streak: chuỗi ngày liên tiếp dài nhất ever.
     */
    public int getLongestStreak() {
        if (allWorkouts.isEmpty()) return 0;

        // Collect tất cả dates vào sorted set
        Set<String> allDates = new HashSet<>();
        for (WorkoutHistory w : allWorkouts) {
            allDates.add(w.getDate());
        }

        // Sắp xếp
        List<String> sortedDates = new ArrayList<>(allDates);
        java.util.Collections.sort(sortedDates);

        int maxStreak = 1;
        int currentStreak = 1;

        for (int i = 1; i < sortedDates.size(); i++) {
            try {
                Calendar prev = Calendar.getInstance();
                Calendar curr = Calendar.getInstance();
                prev.setTime(DATE_FORMAT.parse(sortedDates.get(i - 1)));
                curr.setTime(DATE_FORMAT.parse(sortedDates.get(i)));

                // Kiểm tra nếu là ngày liên tiếp
                prev.add(Calendar.DAY_OF_MONTH, 1);
                if (prev.get(Calendar.YEAR) == curr.get(Calendar.YEAR)
                        && prev.get(Calendar.MONTH) == curr.get(Calendar.MONTH)
                        && prev.get(Calendar.DAY_OF_MONTH) == curr.get(Calendar.DAY_OF_MONTH)) {
                    currentStreak++;
                } else {
                    currentStreak = 1;
                }
                maxStreak = Math.max(maxStreak, currentStreak);
            } catch (Exception e) {
                currentStreak = 1;
            }
        }
        return maxStreak;
    }

    /**
     * Lấy map nhóm cơ → số ngày kể từ lần tập gần nhất.
     * Dùng cho Personal Heatmap.
     * Trả về: {"Bụng" -> 0, "Ngực" -> 1, "Chân" -> 0, "Vai" -> 13, "Lưng" -> 8}
     */
    public Map<String, Integer> getMuscleRecencyMap() {
        Map<String, Integer> result = new HashMap<>();
        Calendar today = Calendar.getInstance();

        for (WorkoutHistory w : allWorkouts) {
            try {
                Calendar workoutDate = Calendar.getInstance();
                workoutDate.setTime(DATE_FORMAT.parse(w.getDate()));

                long diffMillis = today.getTimeInMillis() - workoutDate.getTimeInMillis();
                int daysAgo = (int) (diffMillis / (1000 * 60 * 60 * 24));

                String[] muscles = w.getMuscleGroups().split(",");
                for (String muscle : muscles) {
                    String m = muscle.trim();
                    if (!result.containsKey(m) || result.get(m) > daysAgo) {
                        result.put(m, daysAgo);
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    /**
     * Format date key từ Calendar object.
     */
    public static String formatDateKey(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(calendar.getTime());
    }

    /**
     * Tổng số workouts.
     */
    public int getTotalWorkouts() {
        return allWorkouts.size();
    }
}
