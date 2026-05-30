package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nct.trenx.R;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.UiUtils;

public class LevelActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        UiUtils.applyEdgeToEdge(this, R.id.main);

        // 1. Ánh xạ
        androidx.cardview.widget.CardView cardEasy = findViewById(R.id.card_easy);
        androidx.cardview.widget.CardView cardNormal = findViewById(R.id.card_normal);
        androidx.cardview.widget.CardView cardHard = findViewById(R.id.card_hard);

        // 2. Bắt sự kiện Click
        cardEasy.setOnClickListener(v -> {
            Intent intent = new Intent(LevelActivity.this, ExrciselistActivity.class);
            intent.putExtra(IntentExtras.NHOM_CO, "Ngực & Tay sau");
            startActivity(intent);
        });

        cardNormal.setOnClickListener(v -> {
            Intent intent = new Intent(LevelActivity.this, ExrciselistActivity.class);
            intent.putExtra(IntentExtras.NHOM_CO, "Ngực & Tay sau");
            startActivity(intent);
        });

        cardHard.setOnClickListener(v -> {
            Intent intent = new Intent(LevelActivity.this, ExrciselistActivity.class);
            intent.putExtra(IntentExtras.NHOM_CO, "Lưng & Tay trước");
            startActivity(intent);
        });
    }
}
