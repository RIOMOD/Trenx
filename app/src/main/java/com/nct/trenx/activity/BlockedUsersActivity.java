package com.nct.trenx.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.nct.trenx.R;

/**
 * Màn hình Danh sách người dùng bị chặn (Blocked Users).
 */
public class BlockedUsersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_users);

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}
