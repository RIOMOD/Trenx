package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nct.trenx.R;
import com.nct.trenx.database.DatabaseHelper;
import com.nct.trenx.model.User;
import com.nct.trenx.utils.PreferenceUtils;

/**
 * Màn hình Cài đặt tài khoản (Account Settings).
 */
public class AccountSettingsActivity extends BaseActivity {

    private EditText etUsername, etEmail, etFullName, etCity, etState, etBio;
    private Button btnSaveChanges, btnDeleteAccount;
    private DatabaseHelper dbHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        dbHelper = new DatabaseHelper(this);

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etFullName = findViewById(R.id.etFullName);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etBio = findViewById(R.id.etBio);

        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        loadUserData();

        if (btnSaveChanges != null) {
            btnSaveChanges.setOnClickListener(v -> saveUserData());
        }

        if (btnDeleteAccount != null) {
            btnDeleteAccount.setOnClickListener(v -> showDeleteAccountDialog());
        }

        View btnChangePwd = findViewById(R.id.btnChangePassword);
        if (btnChangePwd != null) {
            btnChangePwd.setOnClickListener(v -> showChangePasswordBottomSheet());
        }
    }

    private void showChangePasswordBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_change_password, null);
        dialog.setContentView(dialogView);

        ImageView btnClose = dialogView.findViewById(R.id.btn_close);
        EditText etNewPwd = dialogView.findViewById(R.id.et_new_password);
        EditText etOldPwd = dialogView.findViewById(R.id.et_old_password);
        Button btnConfirm = dialogView.findViewById(R.id.btn_confirm_change_password);

        ImageView ivToggleNew = dialogView.findViewById(R.id.iv_toggle_new_pwd);
        ImageView ivToggleOld = dialogView.findViewById(R.id.iv_toggle_old_pwd);

        final boolean[] isNewVisible = {false};
        final boolean[] isOldVisible = {false};

        if (ivToggleNew != null && etNewPwd != null) {
            ivToggleNew.setOnClickListener(v -> {
                isNewVisible[0] = !isNewVisible[0];
                etNewPwd.setTransformationMethod(isNewVisible[0] ? null : new PasswordTransformationMethod());
                etNewPwd.setSelection(etNewPwd.getText().length());
            });
        }

        if (ivToggleOld != null && etOldPwd != null) {
            ivToggleOld.setOnClickListener(v -> {
                isOldVisible[0] = !isOldVisible[0];
                etOldPwd.setTransformationMethod(isOldVisible[0] ? null : new PasswordTransformationMethod());
                etOldPwd.setSelection(etOldPwd.getText().length());
            });
        }

        if (btnClose != null) btnClose.setOnClickListener(v -> dialog.dismiss());

        if (btnConfirm != null) {
            btnConfirm.setEnabled(true);
            btnConfirm.setTextColor(getResources().getColor(android.R.color.black));
            btnConfirm.setOnClickListener(v -> {
                String newPwd = etNewPwd != null ? etNewPwd.getText().toString().trim() : "";
                String oldPwd = etOldPwd != null ? etOldPwd.getText().toString().trim() : "";

                if (newPwd.length() < 6) {
                    Toast.makeText(this, "New password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (currentUser != null) {
                    currentUser.setPassword(newPwd);
                    dbHelper.updateUserProfile(currentUser);
                }
                
                Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        }

        dialog.show();
    }

    private void loadUserData() {
        int userId = PreferenceUtils.getUserId(this);
        currentUser = dbHelper.getUserById(userId);

        if (currentUser != null) {
            etUsername.setText(currentUser.getUsername());
            etEmail.setText(currentUser.getEmail());
            etFullName.setText(currentUser.getFullName());
            etCity.setText("TP. HCM");
        } else {
            String currentEmail = PreferenceUtils.getUserEmail(this);
            currentUser = dbHelper.getUserByEmail(currentEmail);
            if (currentUser != null) {
                etUsername.setText(currentUser.getUsername());
                etEmail.setText(currentUser.getEmail());
                etFullName.setText(currentUser.getFullName());
            } else {
                etUsername.setText("nct2923");
                etEmail.setText(currentEmail != null && !currentEmail.isEmpty() ? currentEmail : "thu113112111@gmail.com");
                etFullName.setText("Nguyen Cong Tru");
            }
            etCity.setText("TP. HCM");
        }
    }

    private void saveUserData() {
        String newName = etFullName.getText().toString().trim();
        String newUsername = etUsername.getText().toString().trim();

        if (currentUser != null) {
            currentUser.setFullName(newName);
            currentUser.setUsername(newUsername);
            dbHelper.updateUserProfile(currentUser);
        }

        Toast.makeText(this, "Account settings saved successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDeleteAccountDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to permanently delete your account? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> {
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
