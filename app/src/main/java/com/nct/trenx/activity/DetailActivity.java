package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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

        // Nhận dữ liệu truyền từ Dashboard/Search/LevelActivity/ExerciseActivity
        String titleFromIntent = getIntent().getStringExtra(IntentExtras.WORKOUT_TITLE);
        String nameExtra = getIntent().getStringExtra(IntentExtras.EXERCISE_NAME);
        String dayNameExtra = getIntent().getStringExtra(IntentExtras.DAY_NAME);
        String nhomCoExtra = getIntent().getStringExtra(IntentExtras.NHOM_CO);
        String muscleExtra = getIntent().getStringExtra("MUSCLE_GROUP");
        String eqExtra = getIntent().getStringExtra("EQUIPMENT");
        String diffExtra = getIntent().getStringExtra(IntentExtras.DIFFICULTY);

        if (titleFromIntent != null && !titleFromIntent.isEmpty()) {
            currentDayName = titleFromIntent;
        } else if (nameExtra != null && !nameExtra.isEmpty()) {
            currentDayName = nameExtra;
        } else if (dayNameExtra != null && !dayNameExtra.isEmpty()) {
            currentDayName = dayNameExtra;
        } else if (nhomCoExtra != null && !nhomCoExtra.isEmpty()) {
            currentDayName = nhomCoExtra;
        } else if (muscleExtra != null && !muscleExtra.isEmpty()) {
            currentDayName = muscleExtra;
        } else if (eqExtra != null && !eqExtra.isEmpty()) {
            currentDayName = eqExtra + " Workout";
        }

        if (tvName != null) {
            tvName.setText(currentDayName);
        }

        ImageView ivHero = findViewById(R.id.iv_hero_media);
        if (ivHero != null) {
            com.bumptech.glide.Glide.with(this)
                    .load("https://chrisheria.com/cdn/shop/articles/main-qimg-84869695d7b5d92823071857945d812d.jpg?v=1555624187")
                    .placeholder(R.drawable.feed_workout_1)
                    .error(R.drawable.feed_workout_1)
                    .into(ivHero);
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

        // Tải danh sách bài tập động tương ứng bài tập & cấp độ được chọn
        loadDynamicExercises();

        // Nút Start Workout floating ở đáy màn hình
        if (btnStart != null) {
            btnStart.setOnClickListener(v ->
                NavigationUtils.startTraining(DetailActivity.this, currentDayName, currentDifficulty));
        }
    }

    private static final int[] EXERCISE_DRAWABLES = new int[]{
            R.drawable.feed_workout_1,
            R.drawable.feed_workout_2,
            R.drawable.age18_29,
            R.drawable.age30_39,
            R.drawable.age40_49,
            R.drawable.age50,
            R.drawable.avatar_dan,
            R.drawable.avatar_steffchen
    };

    private void loadDynamicExercises() {
        LinearLayout container = findViewById(R.id.layout_dynamic_exercise_list);
        if (container == null) return;

        com.nct.trenx.database.ExerciseRepository repository = new com.nct.trenx.database.ExerciseRepository(this);
        List<com.nct.trenx.model.Exercise> exercises = repository.getExercisesByDayAndDifficulty(currentDayName, currentDifficulty);

        if (exercises != null && !exercises.isEmpty()) {
            container.removeAllViews();
            android.view.LayoutInflater inflater = android.view.LayoutInflater.from(this);

            for (int i = 0; i < exercises.size(); i++) {
                com.nct.trenx.model.Exercise ex = exercises.get(i);
                View itemView = inflater.inflate(R.layout.item_exercise_detail_row, container, false);

                ImageView imgEx = itemView.findViewById(R.id.iv_exercise_thumb);
                TextView tvTitle = itemView.findViewById(R.id.tv_exercise_title);
                TextView tvSub = itemView.findViewById(R.id.tv_exercise_sub);

                if (tvTitle != null) tvTitle.setText(ex.getName());
                if (tvSub != null) tvSub.setText(ex.getReps() + " • Rest 45 seconds");

                if (imgEx != null) {
                    String imgUrl = ex.getImageName();
                    if (imgUrl != null && imgUrl.startsWith("http")) {
                        com.bumptech.glide.Glide.with(this)
                                .load(imgUrl)
                                .placeholder(EXERCISE_DRAWABLES[i % EXERCISE_DRAWABLES.length])
                                .error(EXERCISE_DRAWABLES[i % EXERCISE_DRAWABLES.length])
                                .into(imgEx);
                    } else {
                        imgEx.setImageResource(EXERCISE_DRAWABLES[i % EXERCISE_DRAWABLES.length]);
                    }
                }

                int pos = i;
                itemView.setOnClickListener(v ->
                        NavigationUtils.startTraining(DetailActivity.this, currentDayName, currentDifficulty, pos));

                container.addView(itemView);

                if (i < exercises.size() - 1) {
                    View divider = new View(this);
                    divider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    divider.setBackgroundColor(0xFFE0E0E0);
                    container.addView(divider);
                }
            }
        }
    }

    private void selectDifficultyTab(String level) {
        currentDifficulty = level;
        loadDynamicExercises();

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
