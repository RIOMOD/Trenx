package com.nct.trenx.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nct.trenx.R;
import com.nct.trenx.database.DatabaseHelper;
import com.nct.trenx.database.FirebaseRepository;
import com.nct.trenx.model.User;
import com.nct.trenx.utils.PreferenceUtils;

/**
 * Màn hình Cài đặt tài khoản (Account Settings) có tính năng Chọn Ảnh Đại Diện linh hoạt.
 */
public class AccountSettingsActivity extends BaseActivity {

    private ImageView ivProfileAvatar, ivEditAvatar;
    private EditText etUsername, etEmail, etFullName, etCity, etState, etBio;
    private Button btnSaveChanges, btnDeleteAccount;
    private DatabaseHelper dbHelper;
    private FirebaseRepository firebaseRepo;
    private User currentUser;

    // Launcher mở thư viện ảnh thiết bị
    private final ActivityResultLauncher<Intent> avatarPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        try {
                            getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } catch (Exception ignored) {}

                        String uriString = selectedImageUri.toString();
                        if (ivProfileAvatar != null) {
                            ivProfileAvatar.setImageURI(selectedImageUri);
                        }

                        PreferenceUtils.saveAvatarUri(AccountSettingsActivity.this, uriString);
                        Toast.makeText(AccountSettingsActivity.this, "Uploading avatar photo...", Toast.LENGTH_SHORT).show();

                        // Tải ảnh đại diện lên Firebase Storage đám mây
                        if (firebaseRepo != null) {
                            firebaseRepo.uploadAvatar(selectedImageUri, new FirebaseRepository.UploadCallback() {
                                @Override
                                public void onSuccess(String downloadUrl) {
                                    PreferenceUtils.saveAvatarUri(AccountSettingsActivity.this, downloadUrl);
                                    Toast.makeText(AccountSettingsActivity.this, "Avatar photo uploaded to cloud!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(String errorMsg) {
                                    // Đã lưu URI local thành công
                                }
                            });
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        dbHelper = new DatabaseHelper(this);
        firebaseRepo = new FirebaseRepository();

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        ivProfileAvatar = findViewById(R.id.ivProfileAvatar);
        ivEditAvatar = findViewById(R.id.ivEditAvatar);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etFullName = findViewById(R.id.etFullName);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etBio = findViewById(R.id.etBio);

        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        loadUserData();
        loadAvatarImage();

        View.OnClickListener changeAvatarListener = v -> showAvatarChooserBottomSheet();
        if (ivProfileAvatar != null) ivProfileAvatar.setOnClickListener(changeAvatarListener);
        if (ivEditAvatar != null) ivEditAvatar.setOnClickListener(changeAvatarListener);

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

    private void showAvatarChooserBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_choose_avatar, null);
        dialog.setContentView(dialogView);

        CardView cardDan = dialogView.findViewById(R.id.card_avatar_dan);
        CardView cardSteffchen = dialogView.findViewById(R.id.card_avatar_steffchen);
        CardView cardAge18 = dialogView.findViewById(R.id.card_avatar_age18);
        CardView cardAge30 = dialogView.findViewById(R.id.card_avatar_age30);
        CardView cardAge40 = dialogView.findViewById(R.id.card_avatar_age40);
        CardView cardAge50 = dialogView.findViewById(R.id.card_avatar_age50);
        Button btnBrowseGallery = dialogView.findViewById(R.id.btn_browse_gallery);

        View.OnClickListener presetListener = v -> {
            int id = v.getId();
            String resName = "avatar_dan";
            int resId = R.drawable.avatar_dan;

            if (id == R.id.card_avatar_steffchen) {
                resName = "avatar_steffchen";
                resId = R.drawable.avatar_steffchen;
            } else if (id == R.id.card_avatar_age18) {
                resName = "age18_29";
                resId = R.drawable.age18_29;
            } else if (id == R.id.card_avatar_age30) {
                resName = "age30_39";
                resId = R.drawable.age30_39;
            } else if (id == R.id.card_avatar_age40) {
                resName = "age40_49";
                resId = R.drawable.age40_49;
            } else if (id == R.id.card_avatar_age50) {
                resName = "age50";
                resId = R.drawable.age50;
            }

            PreferenceUtils.saveAvatarUri(AccountSettingsActivity.this, "res:" + resName);
            if (ivProfileAvatar != null) {
                ivProfileAvatar.setImageResource(resId);
            }
            Toast.makeText(AccountSettingsActivity.this, "Profile picture updated!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        };

        if (cardDan != null) cardDan.setOnClickListener(presetListener);
        if (cardSteffchen != null) cardSteffchen.setOnClickListener(presetListener);
        if (cardAge18 != null) cardAge18.setOnClickListener(presetListener);
        if (cardAge30 != null) cardAge30.setOnClickListener(presetListener);
        if (cardAge40 != null) cardAge40.setOnClickListener(presetListener);
        if (cardAge50 != null) cardAge50.setOnClickListener(presetListener);

        if (btnBrowseGallery != null) {
            btnBrowseGallery.setOnClickListener(v -> {
                dialog.dismiss();
                openImagePicker();
            });
        }

        dialog.show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        avatarPickerLauncher.launch(Intent.createChooser(intent, "Select Profile Picture"));
    }

    private void loadAvatarImage() {
        String savedAvatarUri = PreferenceUtils.getAvatarUri(this);
        if (savedAvatarUri != null && !savedAvatarUri.isEmpty() && ivProfileAvatar != null) {
            if (savedAvatarUri.startsWith("res:")) {
                String drawableName = savedAvatarUri.substring(4);
                int resId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
                if (resId != 0) {
                    ivProfileAvatar.setImageResource(resId);
                } else {
                    ivProfileAvatar.setImageResource(R.drawable.avatar_dan);
                }
            } else {
                try {
                    ivProfileAvatar.setImageURI(Uri.parse(savedAvatarUri));
                } catch (Exception e) {
                    ivProfileAvatar.setImageResource(R.drawable.avatar_dan);
                }
            }
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

                if (newPwd.length() < 6) {
                    Toast.makeText(this, "New password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (currentUser != null) {
                    currentUser.setPassword(newPwd);
                    dbHelper.updateUserProfile(currentUser);
                    dbHelper.updateUserPassword(currentUser.getEmail(), newPwd);
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
