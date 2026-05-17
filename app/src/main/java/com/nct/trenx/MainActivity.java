package com.nct.trenx;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. Ánh xạ (Tìm các CardView trên giao diện thông qua ID)
        androidx.cardview.widget.CardView cardChest = findViewById(R.id.cardChest);
        androidx.cardview.widget.CardView cardBack = findViewById(R.id.cardBack);
        androidx.cardview.widget.CardView cardCore = findViewById(R.id.cardCore);

        // 2. Bắt sự kiện Click cho thẻ "Ngực & Tay sau"
        cardChest.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Sử dụng Intent để chuyển từ MainActivity sang ExerciseActivity
                android.content.Intent intent = new android.content.Intent(MainActivity.this, ExerciseActivity.class);
                // Gửi theo một thông điệp để màn hình kia biết bạn chọn nhóm cơ nào
                intent.putExtra("NHOM_CO", "Ngực & Tay sau");
                // Thực hiện chuyển màn hình
                startActivity(intent);
            }
        });

        // 3. Bắt sự kiện Click cho thẻ "Lưng & Tay trước"
        cardBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this, ExerciseActivity.class);
                intent.putExtra("NHOM_CO", "Lưng & Tay trước");
                startActivity(intent);
            }
        });

        // 4. Bắt sự kiện Click cho thẻ "Cơ bụng (Core)"
        cardCore.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this, ExerciseActivity.class);
                intent.putExtra("NHOM_CO", "Cơ bụng (Core)");
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