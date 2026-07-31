package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
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

        btnBack.setOnClickListener(v -> finish());

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

        etEmail.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);

        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Áp dụng lớp Resilience để chặn và lọc dữ liệu đầu vào ngay tại UI
            if (!com.nct.trenx.utils.ResilienceLayer.isValidEmail(email)) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!com.nct.trenx.utils.ResilienceLayer.isValidPassword(password)) {
                Toast.makeText(this, "Password must be between 6 and 64 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // Làm sạch email trước khi chuyển vào câu truy vấn
            String sanitizedEmail = com.nct.trenx.utils.ResilienceLayer.sanitizeString(email, 100);

            btnSignIn.setEnabled(false);
            Toast.makeText(this, "Signing in online...", Toast.LENGTH_SHORT).show();

            firebaseRepo.login(sanitizedEmail, password, new FirebaseRepository.AuthCallback() {
                @Override
                public void onSuccess(User user) {
                    btnSignIn.setEnabled(true);
                    DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
                    if (db.getUserByEmail(user.getEmail()) == null) {
                        db.registerUser(user);
                    } else {
                        db.updateUserProfile(user);
                    }
                    PreferenceUtils.saveUserSession(LoginActivity.this, user.getId(), user.getEmail());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailure(String errorMsg) {
                    btnSignIn.setEnabled(true);
                    Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            });
        });

        tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });
    }

    private void updateButtonState() {
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
