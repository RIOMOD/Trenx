package com.nct.trenx.utils;

import android.graphics.Color;
import android.widget.Button;

public final class DifficultyUiHelper {

    private static final int COLOR_INACTIVE = Color.parseColor("#888888");
    private static final int COLOR_ACTIVE = Color.parseColor("#00FF00");

    private DifficultyUiHelper() {
    }

    public static void applySelection(Button btnBeginner, Button btnIntermediate, Button btnAdvanced,
                                      String selectedDifficulty) {
        btnBeginner.setTextColor(COLOR_INACTIVE);
        btnIntermediate.setTextColor(COLOR_INACTIVE);
        btnAdvanced.setTextColor(COLOR_INACTIVE);

        if (IntentExtras.DIFFICULTY_BEGINNER.equals(selectedDifficulty)) {
            btnBeginner.setTextColor(COLOR_ACTIVE);
        } else if (IntentExtras.DIFFICULTY_INTERMEDIATE.equals(selectedDifficulty)) {
            btnIntermediate.setTextColor(COLOR_ACTIVE);
        } else if (IntentExtras.DIFFICULTY_ADVANCED.equals(selectedDifficulty)) {
            btnAdvanced.setTextColor(COLOR_ACTIVE);
        }
    }
}
