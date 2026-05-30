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

        // Header
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Account
        LinearLayout btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(this, R.string.edit_profile, Toast.LENGTH_SHORT).show();
            // Mở màn hình chỉnh sửa hồ sơ nếu có
        });

        // Preferences
        LinearLayout btnLanguage = findViewById(R.id.btnLanguage);
        tvCurrentLanguage = findViewById(R.id.tvCurrentLanguage);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchNotifications = findViewById(R.id.switchNotifications);

        btnLanguage.setOnClickListener(v -> showLanguageDialog());

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked != PreferenceUtils.isDarkMode(this)) {
                PreferenceUtils.setDarkMode(this, isChecked);
            }
        });

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PreferenceUtils.setNotificationsEnabled(this, isChecked);
            int msgRes = isChecked ? R.string.notifications_enabled : R.string.notifications_disabled;
            Toast.makeText(this, msgRes, Toast.LENGTH_SHORT).show();
        });

        // About
        LinearLayout btnPrivacy = findViewById(R.id.btnPrivacy);
        btnPrivacy.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nct.com/privacy-policy"));
            startActivity(browserIntent);
        });

        // Logout
        LinearLayout btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> showLogoutDialog());

        // Cập nhật giao diện theo cấu hình đã lưu
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
                .setTitle(R.string.select_language)
                .setSingleChoiceItems(languages, checkedItem, (dialog, which) -> {
                    String selectedLang = codes[which];
                    if (!selectedLang.equals(PreferenceUtils.getLanguage(this))) {
                        PreferenceUtils.setLanguage(this, selectedLang);
                        dialog.dismiss();
                        
                        // Khởi động lại app từ Splash để áp dụng ngôn ngữ mới hoàn toàn
                        Intent intent = new Intent(this, SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout)
                .setMessage(R.string.logout_confirm)
                .setPositiveButton(R.string.logout, (dialog, which) -> {
                    Toast.makeText(this, R.string.logged_out, Toast.LENGTH_SHORT).show();
                    // Clear token/session if needed
                    finish();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
