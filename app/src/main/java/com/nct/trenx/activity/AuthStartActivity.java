package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.nct.trenx.R;

public class AuthStartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_start);

        Button btnCreateAccount = findViewById(R.id.btn_create_account);
        TextView tvSignIn = findViewById(R.id.tv_sign_in);

        btnCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(AuthStartActivity.this, OnboardingActivity.class);
            startActivity(intent);
        });

        tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(AuthStartActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
