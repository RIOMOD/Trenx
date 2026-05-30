package com.nct.trenx.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nct.trenx.R;

public class LikedProgramsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_programs);

        // Nút Back để quay lại Dashboard
        ImageView btnBack = findViewById(R.id.iv_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Set title
        TextView tvTitle = findViewById(R.id.tv_toolbar_title);
        if (tvTitle != null) {
            tvTitle.setText(R.string.liked_programs_title);
        }
    }
}
