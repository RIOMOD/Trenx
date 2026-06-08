package com.nct.trenx.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nct.trenx.R;

public class ForgotPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ImageView btnBack = findViewById(R.id.btn_back);
        EditText etEmail = findViewById(R.id.et_email);
        Button btnRecover = findViewById(R.id.btn_recover);

        btnBack.setOnClickListener(v -> finish());

        btnRecover.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else {
                // In a real app, you'd call an API here.
                Toast.makeText(this, "Recovery link sent to " + email, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
