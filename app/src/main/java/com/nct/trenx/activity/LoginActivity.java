package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nct.trenx.R;
import com.nct.trenx.database.DatabaseHelper;
import com.nct.trenx.database.FirebaseRepository;
import com.nct.trenx.model.User;
import com.nct.trenx.utils.PreferenceUtils;

public class LoginActivity extends BaseActivity {

    private EditText etEmail, etPassword;
    private Button btnSignIn;
    private ImageView ivTogglePassword;
    private boolean isPasswordVisible = false;
    private DatabaseHelper dbHelper;
    private FirebaseRepository firebaseRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        firebaseRepo = new FirebaseRepository();

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        ivTogglePassword = findViewById(R.id.iv_toggle_password);
        ImageView btnBack = findViewById(R.id.btn_back);
        TextView tvForgotPassword = findViewById(R.id.tv_forgot_password);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        if (ivTogglePassword != null) {
            ivTogglePassword.setOnClickListener(v -> {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    etPassword.setTransformationMethod(null);
                    ivTogglePassword.setAlpha(1.0f);
                } else {
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                    ivTogglePassword.setAlpha(0.6f);
                }
                etPassword.setSelection(etPassword.getText().length());
            });
        }

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButtonState();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        if (etEmail != null) etEmail.addTextChangedListener(watcher);
        if (etPassword != null) etPassword.addTextChangedListener(watcher);

        if (btnSignIn != null) {
            btnSignIn.setOnClickListener(v -> {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (!com.nct.trenx.utils.ResilienceLayer.isValidEmail(email)) {
                    Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!com.nct.trenx.utils.ResilienceLayer.isValidPassword(password)) {
                    Toast.makeText(this, "Password must be between 6 and 64 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                String sanitizedEmail = com.nct.trenx.utils.ResilienceLayer.sanitizeString(email, 100);

                btnSignIn.setEnabled(false);
                Toast.makeText(this, "Signing in...", Toast.LENGTH_SHORT).show();

                // 1. Thử Đăng nhập qua Firebase Auth Online
                firebaseRepo.login(sanitizedEmail, password, new FirebaseRepository.AuthCallback() {
                    @Override
                    public void onSuccess(User user) {
                        btnSignIn.setEnabled(true);
                        // Cập nhật mật khẩu và profile mới vào SQLite
                        user.setPassword(password);
                        if (dbHelper.getUserByEmail(user.getEmail()) == null) {
                            dbHelper.registerUser(user);
                        } else {
                            dbHelper.updateUserProfile(user);
                            dbHelper.updateUserPassword(user.getEmail(), password);
                        }
                        PreferenceUtils.saveUserSession(LoginActivity.this, user.getId(), user.getEmail());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        // 2. Fallback: Nếu Firebase Auth thất bại, kiểm tra trong SQLite local (Hỗ trợ khi vừa đổi MK hoặc offline)
                        User localUser = dbHelper.login(sanitizedEmail, password);
                        if (localUser != null) {
                            btnSignIn.setEnabled(true);
                            PreferenceUtils.saveUserSession(LoginActivity.this, localUser.getId(), localUser.getEmail());
                            Toast.makeText(LoginActivity.this, "Welcome back, " + localUser.getUsername(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            btnSignIn.setEnabled(true);
                            Toast.makeText(LoginActivity.this, "Login failed: " + errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            });
        }

        if (tvForgotPassword != null) {
            tvForgotPassword.setOnClickListener(v -> {
                startActivity(new Intent(this, ForgotPasswordActivity.class));
            });
        }
    }

    private void updateButtonState() {
        if (btnSignIn == null || etEmail == null || etPassword == null) return;
        boolean isValid = etEmail.getText().toString().length() > 0 
                && etPassword.getText().toString().length() >= 6;
        
        btnSignIn.setEnabled(isValid);
        if (isValid) {
            btnSignIn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(android.R.color.black)));
            btnSignIn.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            btnSignIn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    android.graphics.Color.parseColor("#DDDDDD")));
            btnSignIn.setTextColor(android.graphics.Color.parseColor("#888888"));
        }
    }
}
