package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nct.trenx.R;
import com.nct.trenx.fragment.DashboardFragment;
import com.nct.trenx.fragment.MyProgressFragment;
import com.nct.trenx.fragment.PlaceholderFragment;
import com.nct.trenx.utils.IntentExtras;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_DASHBOARD = "tag_dashboard";
    private static final String TAG_COMMUNITY = "tag_community";
    private static final String TAG_EXPLORE = "tag_explore";
    private static final String TAG_PROGRESS = "tag_progress";

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bnv_main);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_premium) {
                startActivity(new Intent(MainActivity.this, PremiumActivity.class));
                return false;
            }
            showTab(id);
            return true;
        });

        handleIntent(getIntent());

        if (savedInstanceState == null) {
            if (getIntent().getExtras() == null) {
                bottomNav.setSelectedItemId(R.id.nav_dashboard);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(IntentExtras.TARGET_FRAGMENT)) {
            int targetId = intent.getIntExtra(IntentExtras.TARGET_FRAGMENT, R.id.nav_dashboard);
            bottomNav.setSelectedItemId(targetId);
        }
    }

    private void showTab(@IdRes int navItemId) {
        String tag = tagForNavItem(navItemId);
        Fragment target = getSupportFragmentManager().findFragmentByTag(tag);
        if (target == null) {
            target = createFragment(navItemId);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllTabs(transaction);
        if (target.isAdded()) {
            transaction.show(target);
        } else {
            transaction.add(R.id.fragment_container, target, tag);
        }
        transaction.commit();
    }

    private void hideAllTabs(@NonNull FragmentTransaction transaction) {
        for (String tag : new String[]{TAG_DASHBOARD, TAG_COMMUNITY, TAG_EXPLORE, TAG_PROGRESS}) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null && fragment.isAdded()) {
                transaction.hide(fragment);
            }
        }
    }

    @NonNull
    private String tagForNavItem(@IdRes int navItemId) {
        if (navItemId == R.id.nav_community) {
            return TAG_COMMUNITY;
        }
        if (navItemId == R.id.nav_explore) {
            return TAG_EXPLORE;
        }
        if (navItemId == R.id.nav_progress) {
            return TAG_PROGRESS;
        }
        return TAG_DASHBOARD;
    }

    @NonNull
    private Fragment createFragment(@IdRes int navItemId) {
        if (navItemId == R.id.nav_community) {
            return new com.nct.trenx.fragment.CommunityFragment();
        }
        if (navItemId == R.id.nav_explore) {
            return PlaceholderFragment.newInstance(R.layout.fragment_explore);
        }
        if (navItemId == R.id.nav_progress) {
            return new MyProgressFragment();
        }
        return new DashboardFragment();
    }
}
