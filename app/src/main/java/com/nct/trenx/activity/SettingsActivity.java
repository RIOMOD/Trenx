package com.nct.trenx.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.nct.trenx.R;
import com.nct.trenx.utils.PreferenceUtils;

/**
 * Màn hình Cài đặt (Settings) cập nhật giao diện mới.
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Upgrade Now
        View btnUpgradeNow = findViewById(R.id.btnUpgradeNow);
        if (btnUpgradeNow != null) {
            btnUpgradeNow.setOnClickListener(v -> {
                Intent intent = new Intent(this, PremiumActivity.class);
                startActivity(intent);
            });
        }

        // General section items
        View btnAccountSettings = findViewById(R.id.btnAccountSettings);
        if (btnAccountSettings != null) {
            btnAccountSettings.setOnClickListener(v -> 
                startActivity(new Intent(this, AccountSettingsActivity.class))
            );
        }

        View btnFitnessProfile = findViewById(R.id.btnFitnessProfile);
        if (btnFitnessProfile != null) {
            btnFitnessProfile.setOnClickListener(v -> 
                startActivity(new Intent(this, FitnessProfileActivity.class))
            );
        }

        View btnBlockedUsers = findViewById(R.id.btnBlockedUsers);
        if (btnBlockedUsers != null) {
            btnBlockedUsers.setOnClickListener(v -> 
                startActivity(new Intent(this, BlockedUsersActivity.class))
            );
        }

        View btnNotificationsSettings = findViewById(R.id.btnNotificationsSettings);
        if (btnNotificationsSettings != null) {
            btnNotificationsSettings.setOnClickListener(v -> 
                startActivity(new Intent(this, NotificationsActivity.class))
            );
        }

        setClickToast(R.id.btnShop, "Shop");

        View btnAppearance = findViewById(R.id.btnAppearance);
        if (btnAppearance != null) {
            btnAppearance.setOnClickListener(v -> 
                startActivity(new Intent(this, AppearanceActivity.class))
            );
        }

        // App section items
        setClickToast(R.id.btnRateApp, "Thank you for rating Trenx!");
        setClickToast(R.id.btnContactUs, "Contact us at support@trenx.fit");

        View btnTerms = findViewById(R.id.btnTerms);
        if (btnTerms != null) {
            btnTerms.setOnClickListener(v -> openUrl("https://www.nct.com/terms"));
        }

        View btnPrivacyPolicy = findViewById(R.id.btnPrivacyPolicy);
        if (btnPrivacyPolicy != null) {
            btnPrivacyPolicy.setOnClickListener(v -> openUrl("https://www.nct.com/privacy-policy"));
        }

        // Logout
        Button btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> showLogoutDialog());
        }
    }

    private void setClickToast(int id, String message) {
        View v = findViewById(id);
        if (v != null) {
            v.setOnClickListener(view -> 
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            );
        }
    }

    private void toggleDarkMode() {
        boolean current = PreferenceUtils.isDarkMode(this);
        PreferenceUtils.setDarkMode(this, !current);
        Toast.makeText(this, !current ? "Dark Mode Enabled" : "Light Mode Enabled", Toast.LENGTH_SHORT).show();
    }

    private void openUrl(String url) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Could not open link", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    PreferenceUtils.logout(this);
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
