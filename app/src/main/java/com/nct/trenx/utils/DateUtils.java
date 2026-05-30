package com.nct.trenx.utils;

import android.content.Context;
import com.nct.trenx.R;
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

    public static String formatDisplayDate(Context context, Calendar calendar) {
        return String.format(Locale.getDefault(), "%s, %d %s",
                getDayOfWeekName(context, calendar.get(Calendar.DAY_OF_WEEK)),
                calendar.get(Calendar.DAY_OF_MONTH),
                getMonthShortName(calendar.get(Calendar.MONTH)));
    }

    private static String getDayOfWeekName(Context context, int calendarDayOfWeek) {
        switch (calendarDayOfWeek) {
            case Calendar.MONDAY: return context.getString(R.string.day_mon);
            case Calendar.TUESDAY: return context.getString(R.string.day_tue);
            case Calendar.WEDNESDAY: return context.getString(R.string.day_wed);
            case Calendar.THURSDAY: return context.getString(R.string.day_thu);
            case Calendar.FRIDAY: return context.getString(R.string.day_fri);
            case Calendar.SATURDAY: return context.getString(R.string.day_sat);
            default: return context.getString(R.string.day_sun);
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

    public static String getMonthFullName(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        if (month >= 0 && month < months.length) {
            return months[month];
        }
        return "";
    }

    public static String formatDateKey(Calendar calendar) {
        return String.format(Locale.getDefault(), "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String getDayDisplayText(Context context, Calendar calendar) {
        Calendar today = Calendar.getInstance();
        if (today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
            return String.format(Locale.getDefault(), "%s, %d %s",
                    context.getString(R.string.today),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    getMonthShortName(calendar.get(Calendar.MONTH)));
        }
        return formatDisplayDate(context, calendar);
    }
}
