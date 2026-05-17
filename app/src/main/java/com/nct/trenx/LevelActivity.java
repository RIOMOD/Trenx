package com.nct.trenx;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_level);

        // 1. Ánh xạ (Tìm các CardView trên giao diện thông qua ID)
        androidx.cardview.widget.CardView cardEasy = findViewById(R.id.cardEasy);
        androidx.cardview.widget.CardView cardNormal = findViewById(R.id.cardNormal);
        androidx.cardview.widget.CardView cardHard = findViewById(R.id.cardHard);

        // 2. Bắt sự kiện Click cho thẻ "Ngực & Tay sau"
        cardEasy.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Sử dụng Intent để chuyển từ MainActivity sang ExerciseActivity
                android.content.Intent intent = new android.content.Intent(LevelActivity.this, ExrciselistActivity.class);
                // Gửi theo một thông điệp để màn hình kia biết bạn chọn nhóm cơ nào
                intent.putExtra("NHOM_CO", "Ngực & Tay sau");
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
                intent.putExtra("NHOM_CO", "Ngực & Tay sau");
                // Thực hiện chuyển màn hình
                startActivity(intent);
            }
        });

        // 3. Bắt sự kiện Click cho thẻ "Lưng & Tay trước"
        cardHard.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(LevelActivity.this, ExrciselistActivity.class);
                intent.putExtra("NHOM_CO", "Lưng & Tay trước");
                startActivity(intent);
            }
        });

        // Xử lý giao diện tràn viền (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}