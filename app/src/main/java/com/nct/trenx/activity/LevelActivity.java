package com.nct.trenx.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nct.trenx.R;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.UiUtils;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        UiUtils.applyEdgeToEdge(this, R.id.main);

        // 1. Ánh xạ (Tìm các CardView trên giao diện thông qua ID)
        androidx.cardview.widget.CardView cardEasy = findViewById(R.id.card_easy);
        androidx.cardview.widget.CardView cardNormal = findViewById(R.id.card_normal);
        androidx.cardview.widget.CardView cardHard = findViewById(R.id.card_hard);

        // 2. Bắt sự kiện Click cho thẻ "Ngực & Tay sau"
        cardEasy.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Sử dụng Intent để chuyển từ MainActivity sang ExerciseActivity
                android.content.Intent intent = new android.content.Intent(LevelActivity.this, ExrciselistActivity.class);
                // Gửi theo một thông điệp để màn hình kia biết bạn chọn nhóm cơ nào
                intent.putExtra(IntentExtras.NHOM_CO, "Ngực & Tay sau");
                // Thực hiện chuyển màn hình
                startActivity(intent);
            }
        });

        cardNormal.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Sử dụng Intent để chuyển từ MainActivity sang ExerciseActivity
                android.content.Intent intent = new android.content.Intent(LevelActivity.this, ExrciselistActivity.class);
                // Gửi theo một thông điệp để màn hình kia biết bạn chọn nhóm cơ nào
                intent.putExtra(IntentExtras.NHOM_CO, "Ngực & Tay sau");
                // Thực hiện chuyển màn hình
                startActivity(intent);
            }
        });

        // 3. Bắt sự kiện Click cho thẻ "Lưng & Tay trước"
        cardHard.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(LevelActivity.this, ExrciselistActivity.class);
                intent.putExtra(IntentExtras.NHOM_CO, "Lưng & Tay trước");
                startActivity(intent);
            }
        });

    }
}
