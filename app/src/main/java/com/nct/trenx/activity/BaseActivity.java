package com.nct.trenx.activity;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import com.nct.trenx.utils.PreferenceUtils;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        // Áp dụng ngôn ngữ đã lưu vào Context của Activity
        super.attachBaseContext(PreferenceUtils.updateResources(newBase));
    }
}
