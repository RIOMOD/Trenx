package com.nct.trenx;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PremiumActivity extends AppCompatActivity {

    private View btnClose;
    private Button btnUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        // 1. Ánh xạ các thành phần từ XML
        btnClose = findViewById(R.id.btnClose);
        btnUpgrade = findViewById(R.id.btnUpgrade);

        // 2. Xử lý khi ấn nút X: Quay lại màn hình đang hiển thị trước đó
        if (btnClose != null) {
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Hàm finish() sẽ đóng Activity này và tự động quay về màn hình cũ
                    finish();
                }
            });
        }

        // 3. Xử lý nút Upgrade Now (nếu sếp cần viết logic thanh toán sau này)
        if (btnUpgrade != null) {
            btnUpgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Viết logic xử lý mua premium ở đây
                }
            });
        }
    }
}