package com.nct.trenx;

import android.app.Application;
import com.nct.trenx.utils.PreferenceUtils;

public class TrenxApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Khởi tạo cấu hình (Theme) ngay khi App bắt đầu
        PreferenceUtils.init(this);
    }
}
