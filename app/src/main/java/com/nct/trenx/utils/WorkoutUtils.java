package com.nct.trenx.utils;

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
                return new WorkoutDayInfo("Monday", "Core Shredder", IMG_CORE);
            case Calendar.TUESDAY:
                return new WorkoutDayInfo("Tuesday", "Chest & Triceps", IMG_CHEST);
            case Calendar.WEDNESDAY:
                return new WorkoutDayInfo("Wednesday", "Quad Crusher", IMG_LEGS);
            case Calendar.THURSDAY:
                return new WorkoutDayInfo("Thursday", "Shoulder Power", IMG_CHEST);
            case Calendar.FRIDAY:
                return new WorkoutDayInfo("Friday", "Back & Biceps", IMG_FULL_BODY);
            case Calendar.SATURDAY:
                return new WorkoutDayInfo("Saturday", "Active Recovery", IMG_FULL_BODY);
            case Calendar.SUNDAY:
            default:
                return new WorkoutDayInfo("Sunday", "Full Body Master", IMG_FULL_BODY);
        }
    }

    public static WorkoutDayInfo getTodaySchedule() {
        return getScheduleFor(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
    }
}
