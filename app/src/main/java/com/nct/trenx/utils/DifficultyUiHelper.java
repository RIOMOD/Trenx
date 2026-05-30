package com.nct.trenx.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Button;

import androidx.annotation.AttrRes;
import androidx.core.content.ContextCompat;

import com.nct.trenx.R;

public final class DifficultyUiHelper {

    private DifficultyUiHelper() {
    }

    public static void applySelection(Button btnBeginner, Button btnIntermediate, Button btnAdvanced,
                                      String selectedDifficulty) {
        Context context = btnBeginner.getContext();
        
        int colorInactive = getThemeColor(context, android.R.attr.textColorSecondary);
        int colorActive = ContextCompat.getColor(context, R.color.accent_color);

        btnBeginner.setTextColor(colorInactive);
        btnIntermediate.setTextColor(colorInactive);
        btnAdvanced.setTextColor(colorInactive);

        if (IntentExtras.DIFFICULTY_BEGINNER.equals(selectedDifficulty)) {
            btnBeginner.setTextColor(colorActive);
        } else if (IntentExtras.DIFFICULTY_INTERMEDIATE.equals(selectedDifficulty)) {
            btnIntermediate.setTextColor(colorActive);
        } else if (IntentExtras.DIFFICULTY_ADVANCED.equals(selectedDifficulty)) {
            btnAdvanced.setTextColor(colorActive);
        }
    }

    private static int getThemeColor(Context context, @AttrRes int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }
}
