package com.nct.trenx.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.nct.trenx.R;

/**
 * Màn hình Thông báo (Notifications) cập nhật giao diện mới.
 */
public class NotificationsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ImageView ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }
    }
}
