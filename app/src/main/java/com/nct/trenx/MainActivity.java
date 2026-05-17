package com.nct.trenx;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigation);

        // Bắt sự kiện bấm Tab
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                selectedFragment = new DashboardFragment();
            } else if (id == R.id.nav_community) {
                selectedFragment = new CommunityFragment();
            } else if (id == R.id.nav_explore) {
                selectedFragment = new ExploreFragment();
            } else if (id == R.id.nav_progress) {
                selectedFragment = new MyProgressFragment();
            } else if (id == R.id.nav_premium) {
                // Premium vẫn giữ là Activity
                startActivity(new Intent(MainActivity.this, PremiumActivity.class));
                return false;
            }

            // Thực hiện thao tác thay ruột Fragment
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Xử lý Intent khi được gọi từ Activity khác thông qua BottomNavHelper
        handleIntent(getIntent());

        // Set mặc định khi vừa mở app lên sẽ vào thẳng Dashboard
        if (savedInstanceState == null && getIntent().getExtras() == null) {
            bottomNav.setSelectedItemId(R.id.nav_dashboard);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra("TARGET_FRAGMENT")) {
            int targetId = intent.getIntExtra("TARGET_FRAGMENT", R.id.nav_dashboard);
            bottomNav.setSelectedItemId(targetId);
        }
    }
}