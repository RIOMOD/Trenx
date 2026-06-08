package com.nct.trenx.activity;

import android.net.Uri;
import android.os.Bundle;
import androidx.viewpager2.widget.ViewPager2;
import com.nct.trenx.R;
import com.nct.trenx.adapter.FitnessTestAdapter;
import java.util.ArrayList;
import java.util.List;

public class FitnessTestActivity extends BaseActivity {

    private ViewPager2 viewPager;
    private List<FitnessTestAdapter.FitnessTestItem> testItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_test);

        String testType = getIntent().getStringExtra("test_type");

        viewPager = findViewById(R.id.view_pager_fitness_test);
        findViewById(R.id.btn_close_test).setOnClickListener(v -> finish());

        setupTestItems(testType);

        FitnessTestAdapter adapter = new FitnessTestAdapter(testItems, position -> {
            // When complete, return to onboarding to select parameters
            finish();
        });

        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
    }

    private void setupTestItems(String type) {
        testItems = new ArrayList<>();
        
        if ("squats".equals(type)) {
            testItems.add(new FitnessTestAdapter.FitnessTestItem(
                    "Max Squats Without Interruption Test",
                    "Let's see how many squats you can perform! On this screen, you'll see a demonstration of the correct squat form. Watch closely, then try it yourself and see how many you can do while maintaining proper technique.",
                    null // To be added later: Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_squat)
            ));
        } else if ("pullups".equals(type)) {
            testItems.add(new FitnessTestAdapter.FitnessTestItem(
                    "Max Pull-ups Without Interruption Test",
                    "Let's see how many pull-ups you can perform! On this screen, you'll see a demonstration of the correct pull-up form. Watch closely, then try it yourself and see how many you can do while maintaining proper technique.",
                    null // To be added later
            ));
        } else if ("pushups".equals(type)) {
            testItems.add(new FitnessTestAdapter.FitnessTestItem(
                    "Max Push-ups Without Interruption Test",
                    "Let's see how many push-ups you can perform! On this screen, you'll see a demonstration of the correct push-up form. Watch closely, then try it yourself and see how many you can do while maintaining proper technique.",
                    null // To be added later
            ));
        } else if ("dips".equals(type)) {
            testItems.add(new FitnessTestAdapter.FitnessTestItem(
                    "Max Dips Without Interruption Test",
                    "Let's see how many dips you can perform! On this screen, you'll see a demonstration of the correct dip form. Watch closely, then try it yourself and see how many you can do while maintaining proper technique.",
                    null // To be added later
            ));
        }
    }
}
