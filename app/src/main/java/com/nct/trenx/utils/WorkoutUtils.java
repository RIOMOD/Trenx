package com.nct.trenx.utils;

import com.nct.trenx.R;
import com.nct.trenx.model.WorkoutDayInfo;

import java.util.Calendar;

public final class WorkoutUtils {

    private static final String IMG_CORE = "https://i.ytimg.com/vi/pd3-q2U7JXk/maxresdefault.jpg";
    private static final String IMG_CHEST = "https://i.ytimg.com/vi/srj94JCeuWw/maxresdefault.jpg";
    private static final String IMG_LEGS = "https://thenx.com/cdn/shop/articles/legsandglutes.jpg?v=1610420442";
    private static final String IMG_FULL_BODY = "https://i.ytimg.com/vi/G5nxGTFBauM/maxresdefault.jpg";

    private WorkoutUtils() {
    }

    public static WorkoutDayInfo getScheduleFor(int calendarDayOfWeek) {
        switch (calendarDayOfWeek) {
            case Calendar.MONDAY:
                return new WorkoutDayInfo(R.string.day_mon, R.string.core_shredder, IMG_CORE, "Monday");
            case Calendar.TUESDAY:
                return new WorkoutDayInfo(R.string.day_tue, R.string.chest_triceps, IMG_CHEST, "Tuesday");
            case Calendar.WEDNESDAY:
                return new WorkoutDayInfo(R.string.day_wed, R.string.quad_crusher, IMG_LEGS, "Wednesday");
            case Calendar.THURSDAY:
                return new WorkoutDayInfo(R.string.day_thu, R.string.chest_triceps, IMG_CHEST, "Thursday");
            case Calendar.FRIDAY:
                return new WorkoutDayInfo(R.string.day_fri, R.string.back_biceps, IMG_FULL_BODY, "Friday");
            case Calendar.SATURDAY:
                return new WorkoutDayInfo(R.string.day_sat, R.string.active_recovery, IMG_FULL_BODY, "Saturday");
            case Calendar.SUNDAY:
            default:
                return new WorkoutDayInfo(R.string.day_sun, R.string.strength_building, IMG_FULL_BODY, "Sunday");
        }
    }

    public static WorkoutDayInfo getTodaySchedule() {
        return getScheduleFor(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
    }
}
