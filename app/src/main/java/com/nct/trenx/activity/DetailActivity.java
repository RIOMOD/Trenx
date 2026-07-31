package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.nct.trenx.R;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.NavigationUtils;

/**
 * Màn hình Chi tiết Bài tập (Training / Workout Detail Activity) khớp 100% hình tham chiếu.
 */
public class DetailActivity extends BaseActivity {

    private TextView tvName, tabBeginner, tabIntermediate, tabAdvanced;
    private Button btnStart;
    private String currentDifficulty = "Intermediate";
    private String currentDayName = "Chest Crusher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView btnBack = findViewById(R.id.btn_back);
        ImageView btnShare = findViewById(R.id.btn_share);
        tvName = findViewById(R.id.tvDetailName);
        btnStart = findViewById(R.id.btnStartWorkout);

        tabBeginner = findViewById(R.id.tab_beginner);
        tabIntermediate = findViewById(R.id.tab_intermediate);
        tabAdvanced = findViewById(R.id.tab_advanced);

        CardView cardPersonalHeatmap = findViewById(R.id.card_personal_heatmap);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        
        if (btnShare != null) {
            btnShare.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this workout: " + currentDayName + " on Trenx App!");
                startActivity(Intent.createChooser(shareIntent, "Share Workout"));
            });
        }

        // Nhận dữ liệu truyền từ Dashboard/Search
        String nameExtra = getIntent().getStringExtra(IntentExtras.EXERCISE_NAME);
        String dayNameExtra = getIntent().getStringExtra(IntentExtras.DAY_NAME);
        String diffExtra = getIntent().getStringExtra(IntentExtras.DIFFICULTY);

        if (nameExtra != null && !nameExtra.isEmpty()) {
            currentDayName = nameExtra;
            if (tvName != null) tvName.setText(nameExtra);
        } else if (dayNameExtra != null && !dayNameExtra.isEmpty()) {
            currentDayName = dayNameExtra;
            if (tvName != null) tvName.setText(dayNameExtra);
        }

        if (diffExtra != null && !diffExtra.isEmpty()) {
            currentDifficulty = diffExtra;
            selectDifficultyTab(currentDifficulty);
        }

        // Sự kiện chuyển Tab Cấp độ (Beginner | Intermediate | Advanced)
        if (tabBeginner != null) {
            tabBeginner.setOnClickListener(v -> selectDifficultyTab("Beginner"));
        }
        if (tabIntermediate != null) {
            tabIntermediate.setOnClickListener(v -> selectDifficultyTab("Intermediate"));
        }
        if (tabAdvanced != null) {
            tabAdvanced.setOnClickListener(v -> selectDifficultyTab("Advanced"));
        }

        if (cardPersonalHeatmap != null) {
            cardPersonalHeatmap.setOnClickListener(v -> {
                Toast.makeText(this, "Opening Personal Heatmap...", Toast.LENGTH_SHORT).show();
            });
        }

        // Nút Start Workout floating ở đáy màn hình
        if (btnStart != null) {
            btnStart.setOnClickListener(v ->
                NavigationUtils.startTraining(DetailActivity.this, currentDayName, currentDifficulty));
        }
    }

    private void selectDifficultyTab(String level) {
        currentDifficulty = level;

        if (tabBeginner != null) {
            tabBeginner.setBackground(null);
            tabBeginner.setTextColor(0xFF555555);
        }
        if (tabIntermediate != null) {
            tabIntermediate.setBackground(null);
            tabIntermediate.setTextColor(0xFF555555);
        }
        if (tabAdvanced != null) {
            tabAdvanced.setBackground(null);
            tabAdvanced.setTextColor(0xFF555555);
        }

        TextView selectedTab = tabIntermediate;
        if ("Beginner".equalsIgnoreCase(level)) {
            selectedTab = tabBeginner;
        } else if ("Advanced".equalsIgnoreCase(level)) {
            selectedTab = tabAdvanced;
        }

        if (selectedTab != null) {
            selectedTab.setBackgroundResource(R.drawable.bg_pill_badge);
            selectedTab.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFFFFFFF));
            selectedTab.setTextColor(0xFF000000);
            selectedTab.setElevation(2f);
        }
    }
}
