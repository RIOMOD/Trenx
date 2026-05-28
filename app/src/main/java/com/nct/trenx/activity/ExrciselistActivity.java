package com.nct.trenx.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nct.trenx.R;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.UiUtils;

public class ExrciselistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exlist);
        UiUtils.applyEdgeToEdge(this, R.id.main);

        // 1. Ánh xạ (Tìm các CardView trên giao diện thông qua ID)
        androidx.cardview.widget.CardView cardChest = findViewById(R.id.card_chest);
        androidx.cardview.widget.CardView cardBack = findViewById(R.id.card_back);
        androidx.cardview.widget.CardView cardCore = findViewById(R.id.card_core);

        // 2. Bắt sự kiện Click cho thẻ "Ngực & Tay sau"
        cardChest.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Sử dụng Intent để chuyển từ MainActivity sang ExerciseActivity
                android.content.Intent intent = new android.content.Intent(ExrciselistActivity.this, ExerciseActivity.class);
                // Gửi theo một thông điệp để màn hình kia biết bạn chọn nhóm cơ nào
                intent.putExtra(IntentExtras.NHOM_CO, "Ngực & Tay sau");
                // Thực hiện chuyển màn hình
                startActivity(intent);
            }
        });

        // 3. Bắt sự kiện Click cho thẻ "Lưng & Tay trước"
        cardBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(ExrciselistActivity.this, ExerciseActivity.class);
                intent.putExtra(IntentExtras.NHOM_CO, "Lưng & Tay trước");
                startActivity(intent);
            }
        });

        // 4. Bắt sự kiện Click cho thẻ "Cơ bụng (Core)"
        cardCore.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(ExrciselistActivity.this, ExerciseActivity.class);
                intent.putExtra(IntentExtras.NHOM_CO, "Cơ bụng (Core)");
                startActivity(intent);
            }
        });

    }
}
