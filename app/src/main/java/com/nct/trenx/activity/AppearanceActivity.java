package com.nct.trenx.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nct.trenx.R;
import com.nct.trenx.utils.PreferenceUtils;

/**
 * Màn hình Cài đặt Giao diện (Appearance).
 */
public class AppearanceActivity extends BaseActivity {

    private View dotLightTheme, dotDarkTheme, dotSystemDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appearance);

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        dotLightTheme = findViewById(R.id.radioLightTheme);
        dotDarkTheme = findViewById(R.id.radioDarkTheme);
        dotSystemDefault = findViewById(R.id.dot_system_default);

        boolean isDarkMode = PreferenceUtils.isDarkMode(this);
        updateSelection(isDarkMode ? 1 : 2); // 0: Light, 1: Dark, 2: System Default

        findViewById(R.id.btnLightTheme).setOnClickListener(v -> {
            PreferenceUtils.setDarkMode(this, false);
            updateSelection(0);
            Toast.makeText(this, "Light Theme Selected", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnDarkTheme).setOnClickListener(v -> {
            PreferenceUtils.setDarkMode(this, true);
            updateSelection(1);
            Toast.makeText(this, "Dark Theme Selected", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnSystemDefault).setOnClickListener(v -> {
            updateSelection(2);
            Toast.makeText(this, "System Default Selected", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateSelection(int mode) {
        if (dotSystemDefault != null) {
            dotSystemDefault.setVisibility(mode == 2 ? View.VISIBLE : View.GONE);
        }
    }
}
