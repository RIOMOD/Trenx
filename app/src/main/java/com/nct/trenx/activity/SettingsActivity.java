package com.nct.trenx.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.nct.trenx.R;
import com.nct.trenx.utils.PreferenceUtils;

public class SettingsActivity extends BaseActivity {

    private TextView tvCurrentLanguage;
    private Switch switchDarkMode;
    private Switch switchNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        LinearLayout btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
        });

        tvCurrentLanguage = findViewById(R.id.tvCurrentLanguage);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchNotifications = findViewById(R.id.switchNotifications);

        findViewById(R.id.btnLanguage).setOnClickListener(v -> showLanguageDialog());

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked != PreferenceUtils.isDarkMode(this)) {
                PreferenceUtils.setDarkMode(this, isChecked);
            }
        });

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PreferenceUtils.setNotificationsEnabled(this, isChecked);
        });

        findViewById(R.id.btnPrivacy).setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nct.com/privacy-policy"));
            startActivity(browserIntent);
        });

        // Logout logic
        LinearLayout btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> showLogoutDialog());

        updateUI();
    }

    private void updateUI() {
        String lang = PreferenceUtils.getLanguage(this);
        tvCurrentLanguage.setText(lang.equals("vi") ? "Tiếng Việt" : "English");
        switchDarkMode.setChecked(PreferenceUtils.isDarkMode(this));
        switchNotifications.setChecked(PreferenceUtils.isNotificationsEnabled(this));
    }

    private void showLanguageDialog() {
        String[] languages = {"English", "Tiếng Việt"};
        String[] codes = {"en", "vi"};
        int checkedItem = PreferenceUtils.getLanguage(this).equals("vi") ? 1 : 0;

        new AlertDialog.Builder(this)
                .setTitle("Chọn ngôn ngữ")
                .setSingleChoiceItems(languages, checkedItem, (dialog, which) -> {
                    String selectedLang = codes[which];
                    if (!selectedLang.equals(PreferenceUtils.getLanguage(this))) {
                        PreferenceUtils.setLanguage(this, selectedLang);
                        dialog.dismiss();
                        Intent intent = new Intent(this, SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> {
                    PreferenceUtils.logout(this);
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
