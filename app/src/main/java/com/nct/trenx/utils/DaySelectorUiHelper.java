package com.nct.trenx.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.AttrRes;

import com.nct.trenx.R;

public final class DaySelectorUiHelper {

    private DaySelectorUiHelper() {
    }

    public static void applySelectedDay(Context context, TextView[] dayViews, int selectedDayOfWeek) {
        if (context == null || dayViews == null) {
            return;
        }
        // Sửa thành như thế này sếp nhé:
        int colorPrimary = getThemeColor(context, android.R.attr.colorPrimary);
        int colorOnPrimary = getThemeColor(context, android.R.attr.colorBackground);
        int textColorSecondary = getThemeColor(context, android.R.attr.textColorSecondary);

        for (int i = 1; i < dayViews.length; i++) {
            TextView dayView = dayViews[i];
            if (dayView == null) {
                continue;
            }
            if (i == selectedDayOfWeek) {
                dayView.setBackgroundResource(R.drawable.bg_selected_day);
                dayView.setTextColor(colorOnPrimary);
            } else {
                dayView.setBackground(null);
                dayView.setTextColor(textColorSecondary);
            }
        }
    }

    private static int getThemeColor(Context context, @AttrRes int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }
}
