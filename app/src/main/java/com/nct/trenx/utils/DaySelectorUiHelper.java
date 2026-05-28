package com.nct.trenx.utils;

import android.content.Context;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.nct.trenx.R;

public final class DaySelectorUiHelper {

    private DaySelectorUiHelper() {
    }

    public static void applySelectedDay(Context context, TextView[] dayViews, int selectedDayOfWeek) {
        if (context == null || dayViews == null) {
            return;
        }
        for (int i = 1; i < dayViews.length; i++) {
            TextView dayView = dayViews[i];
            if (dayView == null) {
                continue;
            }
            if (i == selectedDayOfWeek) {
                dayView.setBackgroundResource(R.drawable.bg_selected_day);
                dayView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            } else {
                dayView.setBackground(null);
                dayView.setTextColor(ContextCompat.getColor(context, R.color.text_white));
            }
        }
    }
}
