package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nct.trenx.R;

/**
 * Màn hình Nâng cấp Premium (Unlimited Access To Thenx) cập nhật giao diện mới.
 */
public class PremiumActivity extends BaseActivity {

    private ImageView btnClose;
    private Button btnUpgrade;
    private View cardThenxWebsite, cardAppStore;
    private View cardYearlyPlan, cardMonthlyPlan;

    private boolean isYearlySelected = true;
    private boolean isThenxWebsiteSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        initViews();
        setupListeners();
    }

    private void initViews() {
        btnClose = findViewById(R.id.btnClose);
        btnUpgrade = findViewById(R.id.btnUpgrade);

        cardThenxWebsite = findViewById(R.id.cardThenxWebsite);
        cardAppStore = findViewById(R.id.cardAppStore);

        cardYearlyPlan = findViewById(R.id.cardYearlyPlan);
        cardMonthlyPlan = findViewById(R.id.cardMonthlyPlan);
    }

    private void setupListeners() {
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> finish());
        }

        if (cardThenxWebsite != null && cardAppStore != null) {
            cardThenxWebsite.setOnClickListener(v -> {
                isThenxWebsiteSelected = true;
                cardThenxWebsite.setBackgroundResource(R.drawable.bg_payment_card_selected);
                cardAppStore.setBackgroundResource(R.drawable.bg_payment_card);
            });

            cardAppStore.setOnClickListener(v -> {
                isThenxWebsiteSelected = false;
                cardAppStore.setBackgroundResource(R.drawable.bg_payment_card_selected);
                cardThenxWebsite.setBackgroundResource(R.drawable.bg_payment_card);
            });
        }

        if (cardYearlyPlan != null && cardMonthlyPlan != null) {
            cardYearlyPlan.setOnClickListener(v -> {
                isYearlySelected = true;
                cardYearlyPlan.setBackgroundResource(R.drawable.bg_payment_card_selected);
                cardMonthlyPlan.setBackgroundResource(R.drawable.bg_payment_card);
            });

            cardMonthlyPlan.setOnClickListener(v -> {
                isYearlySelected = false;
                cardMonthlyPlan.setBackgroundResource(R.drawable.bg_payment_card_selected);
                cardYearlyPlan.setBackgroundResource(R.drawable.bg_payment_card);
            });
        }

        if (btnUpgrade != null) {
            btnUpgrade.setOnClickListener(v -> {
                String plan = isYearlySelected ? "Yearly (711.550 VND)" : "Monthly (122.550 VND)";
                Toast.makeText(this, "Redirecting to checkout for " + plan, Toast.LENGTH_SHORT).show();
            });
        }
    }
}
