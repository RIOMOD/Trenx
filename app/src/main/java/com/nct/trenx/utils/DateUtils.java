package com.nct.trenx.utils;

import java.util.Calendar;
import java.util.Locale;

public final class DateUtils {

    private DateUtils() {
    }

    public static String formatElapsedMillis(long elapsedMillis) {
        int totalSeconds = (int) (elapsedMillis / 1000);
        int mins = totalSeconds / 60;
        int secs = totalSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", mins, secs);
    }

    /**
     * Ví dụ: "Wednesday, 13 May"
     */
    public static String formatDisplayDate(Calendar calendar) {
        return String.format(Locale.getDefault(), "%s, %d %s",
                getDayOfWeekName(calendar.get(Calendar.DAY_OF_WEEK)),
                calendar.get(Calendar.DAY_OF_MONTH),
                getMonthShortName(calendar.get(Calendar.MONTH)));
    }

    private static String getDayOfWeekName(int calendarDayOfWeek) {
        switch (calendarDayOfWeek) {
            case Calendar.MONDAY: return "Monday";
            case Calendar.TUESDAY: return "Tuesday";
            case Calendar.WEDNESDAY: return "Wednesday";
            case Calendar.THURSDAY: return "Thursday";
            case Calendar.FRIDAY: return "Friday";
            case Calendar.SATURDAY: return "Saturday";
            default: return "Sunday";
        }
    }

    private static String getMonthShortName(int month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        if (month >= 0 && month < months.length) {
            return months[month];
        }
        return "";
    }

    /**
     * Trả về tên tháng đầy đủ: "January", "February", ...
     */
    public static String getMonthFullName(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        if (month >= 0 && month < months.length) {
            return months[month];
        }
        return "";
    }

    /**
     * Format Calendar thành date key "yyyy-MM-dd".
     */
    public static String formatDateKey(Calendar calendar) {
        return String.format(Locale.getDefault(), "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Hiển thị ngày dạng "Today, 28 May" hoặc "Wednesday, 13 May".
     */
    public static String getDayDisplayText(Calendar calendar) {
        Calendar today = Calendar.getInstance();
        if (today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
            return String.format(Locale.getDefault(), "Today, %d %s",
                    calendar.get(Calendar.DAY_OF_MONTH),
                    getMonthShortName(calendar.get(Calendar.MONTH)));
        }
        return formatDisplayDate(calendar);
    }
}
