package com.nct.trenx;

import android.app.Activity;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavHelper {

    // Hàm này sẽ được gọi ở tất cả các trang
    public static void setupBottomNav(Activity activity, BottomNavigationView bottomNav, int currentTabId) {

        // 1. Đèn sáng ở tab hiện tại
        bottomNav.setSelectedItemId(currentTabId);

        // 2. Xử lý khi bấm vào các tab
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Nếu bấm lại vào chính tab đang đứng -> Không làm gì cả
            if (id == currentTabId) {
                return true;
            }

            Intent intent = null;

            // Các tab Dashboard, Community, Explore, và Progress là Fragment trong MainActivity
            if (id == R.id.nav_dashboard || id == R.id.nav_community || 
                id == R.id.nav_explore || id == R.id.nav_progress) {
                
                intent = new Intent(activity, MainActivity.class);
                intent.putExtra("TARGET_FRAGMENT", id);
                // Giữ lại trang cũ, không tạo trang mới để tránh chồng chéo Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            } else if (id == R.id.nav_premium) {
                // Riêng Premium là Activity riêng biệt
                activity.startActivity(new Intent(activity, PremiumActivity.class));
                return false;
            }

            // Thực thi chuyển trang
            if (intent != null) {
                activity.startActivity(intent);
                // Tắt hiệu ứng trượt màn hình để giống tab thật
                activity.overridePendingTransition(0, 0);
            }
            return true;
        });
    }
}
