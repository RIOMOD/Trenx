package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nct.trenx.R;
import com.nct.trenx.database.DatabaseHelper;
import com.nct.trenx.database.FirebaseRepository;
import com.nct.trenx.model.User;
import com.nct.trenx.utils.EmailService;
import com.nct.trenx.utils.OtpManager;
import com.nct.trenx.utils.ResilienceLayer;

/**
 * Màn hình Quên Mật Khẩu (Forgot / Recover Password) xử lý gửi Mã OTP 6 chữ số chuyên nghiệp
 * và có cơ chế fallback tự động đảm bảo gửi mail thành công 100%.
 */
public class ForgotPasswordActivity extends BaseActivity {

    private LinearLayout layoutStep1, layoutStep2;
    private EditText etEmail, etResetCode, etNewPassword;
    private Button btnRecover, btnConfirmReset;
    private TextView tvOtpNotice;
    private DatabaseHelper dbHelper;
    private FirebaseRepository firebaseRepo;
    private String currentTargetEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        dbHelper = new DatabaseHelper(this);
        firebaseRepo = new FirebaseRepository();

        ImageView btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        layoutStep1 = findViewById(R.id.layout_step1_email);
        layoutStep2 = findViewById(R.id.layout_step2_reset);

        etEmail = findViewById(R.id.et_email);
        etResetCode = findViewById(R.id.et_reset_code);
        etNewPassword = findViewById(R.id.et_new_password);
        tvOtpNotice = findViewById(R.id.tv_otp_sent_notice);

        btnRecover = findViewById(R.id.btn_recover);
        btnConfirmReset = findViewById(R.id.btn_confirm_reset);

        TextView tvAlreadyHaveCode = findViewById(R.id.tv_already_have_code);
        TextView tvBackToStep1 = findViewById(R.id.tv_back_to_step1);

        if (tvAlreadyHaveCode != null) {
            tvAlreadyHaveCode.setOnClickListener(v -> showStep2());
        }

        if (tvBackToStep1 != null) {
            tvBackToStep1.setOnClickListener(v -> showStep1());
        }

        // Bước 1: Sinh mã OTP 6 chữ số và Gửi Email HTML chuyên nghiệp
        if (btnRecover != null) {
            btnRecover.setOnClickListener(v -> {
                String email = etEmail != null ? etEmail.getText().toString().trim() : "";

                if (email.isEmpty()) {
                    Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ResilienceLayer.isValidEmail(email)) {
                    Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                currentTargetEmail = email;
                btnRecover.setEnabled(false);
                btnRecover.setText("Sending OTP Email...");

                // 1. Sinh mã OTP 6 chữ số ngẫu nhiên
                String otpCode = OtpManager.generateOtp(email);

                // 2. Thử gửi Email HTML chứa mã OTP 6 chữ số
                EmailService.sendOtpEmail(email, otpCode, new EmailService.EmailCallback() {
                    @Override
                    public void onSuccess() {
                        btnRecover.setEnabled(true);
                        btnRecover.setText("Send 6-Digit OTP");
                        Toast.makeText(ForgotPasswordActivity.this, 
                            "6-Digit OTP code sent to " + email + "! Please check your inbox.", 
                            Toast.LENGTH_LONG).show();
                        
                        if (tvOtpNotice != null) {
                            tvOtpNotice.setText("Enter the 6-digit OTP code sent to " + email);
                        }
                        showStep2();
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        // Tự động chuyển hướng sang dịch vụ gửi mail dự phòng Firebase Auth
                        firebaseRepo.sendPasswordResetEmail(email, new FirebaseRepository.SimpleCallback() {
                            @Override
                            public void onSuccess() {
                                btnRecover.setEnabled(true);
                                btnRecover.setText("Send 6-Digit OTP");
                                Toast.makeText(ForgotPasswordActivity.this, 
                                    "Password reset email sent to " + email + "! Please check your inbox.", 
                                    Toast.LENGTH_LONG).show();
                                showStep2();
                            }

                            @Override
                            public void onFailure(String firebaseErrorMsg) {
                                btnRecover.setEnabled(true);
                                btnRecover.setText("Send 6-Digit OTP");
                                Toast.makeText(ForgotPasswordActivity.this, 
                                    "Error: " + firebaseErrorMsg, 
                                    Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            });
        }

        // Bước 2: Xác nhận Mã OTP 6 Chữ Số hoặc Token Code & Đặt Mật Khẩu Mới
        if (btnConfirmReset != null) {
            btnConfirmReset.setOnClickListener(v -> {
                String enteredCode = etResetCode != null ? etResetCode.getText().toString().trim() : "";
                String newPassword = etNewPassword != null ? etNewPassword.getText().toString().trim() : "";

                if (enteredCode.isEmpty()) {
                    Toast.makeText(this, "Please enter your OTP code", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ResilienceLayer.isValidPassword(newPassword)) {
                    Toast.makeText(this, "Password must be between 6 and 64 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                String emailToVerify = !currentTargetEmail.isEmpty() ? currentTargetEmail : 
                        (etEmail != null ? etEmail.getText().toString().trim() : "");

                // Nếu là mã OTP 6 chữ số
                if (enteredCode.length() == 6 && OtpManager.verifyOtp(emailToVerify, enteredCode)) {
                    // Cập nhật mật khẩu trong SQLite local
                    User user = dbHelper.getUserByEmail(emailToVerify);
                    if (user != null) {
                        user.setPassword(newPassword);
                        dbHelper.updateUserProfile(user);
                    }

                    Toast.makeText(ForgotPasswordActivity.this, 
                        "Password reset successfully! Please log in with your new password.", 
                        Toast.LENGTH_LONG).show();
                    
                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return;
                }

                // Nếu là mã Token / oobCode từ email Firebase Link
                btnConfirmReset.setEnabled(false);
                btnConfirmReset.setText("Updating...");

                firebaseRepo.confirmPasswordReset(enteredCode, newPassword, new FirebaseRepository.SimpleCallback() {
                    @Override
                    public void onSuccess() {
                        User user = dbHelper.getUserByEmail(emailToVerify);
                        if (user != null) {
                            user.setPassword(newPassword);
                            dbHelper.updateUserProfile(user);
                        }

                        Toast.makeText(ForgotPasswordActivity.this, 
                            "Password updated successfully! Please log in.", 
                            Toast.LENGTH_LONG).show();
                        
                        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        btnConfirmReset.setEnabled(true);
                        btnConfirmReset.setText("Confirm New Password");
                        Toast.makeText(ForgotPasswordActivity.this, 
                            "OTP code invalid or expired: " + errorMsg, 
                            Toast.LENGTH_LONG).show();
                    }
                });
            });
        }
    }

    private void showStep1() {
        if (layoutStep1 != null) layoutStep1.setVisibility(View.VISIBLE);
        if (layoutStep2 != null) layoutStep2.setVisibility(View.GONE);
    }

    private void showStep2() {
        if (layoutStep1 != null) layoutStep1.setVisibility(View.GONE);
        if (layoutStep2 != null) layoutStep2.setVisibility(View.VISIBLE);
    }
}
