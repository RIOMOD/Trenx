package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nct.trenx.R;

public class PremiumActivity extends BaseActivity {

    private View btnClose;
    private Button btnUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        btnClose = findViewById(R.id.btnClose);
        btnUpgrade = findViewById(R.id.btnUpgrade);

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                // Khi ấn nút X -> Chuyển sang trang Dashboard (MainActivity)
                Intent intent = new Intent(PremiumActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }

        if (btnUpgrade != null) {
            btnUpgrade.setOnClickListener(v -> {
                // Logic thanh toán...
            });
        }
    }
}
