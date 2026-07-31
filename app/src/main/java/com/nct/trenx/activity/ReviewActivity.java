package com.nct.trenx.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.nct.trenx.R;
import com.nct.trenx.database.DatabaseHelper;
import com.nct.trenx.database.FirebaseRepository;
import com.nct.trenx.model.WorkoutHistory;
import com.nct.trenx.utils.DateUtils;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.PreferenceUtils;

import java.util.Calendar;

/**
 * Màn hình Hoàn Thành Bài Tập (Finish Workout) theo thiết kế mới.
 */
public class ReviewActivity extends BaseActivity {

    private TextView tvDuration;
    private ProgressBar pbCompletion;
    private TextView tvCompletionPct;
    private EditText etComment;
    private Switch switchPublicActivity;
    private Button btnSave;
    private ImageView ivClose;
    private View layoutAddPhoto;

    private TextView[] feelingChips;
    private String selectedFeeling = "";
    private DatabaseHelper dbHelper;
    private FirebaseRepository firebaseRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        dbHelper = new DatabaseHelper(this);
        firebaseRepo = new FirebaseRepository();

        initViews();
        setupData();
        setupFeelingChips();
        setupListeners();
    }

    private void initViews() {
        ivClose = findViewById(R.id.iv_close);
        tvDuration = findViewById(R.id.tv_workout_duration);
        pbCompletion = findViewById(R.id.pb_completion);
        tvCompletionPct = findViewById(R.id.tv_completion_percentage);
        etComment = findViewById(R.id.et_comment);
        switchPublicActivity = findViewById(R.id.switch_public_activity);
        btnSave = findViewById(R.id.btnFinishReview);
        layoutAddPhoto = findViewById(R.id.layout_add_photo);

        feelingChips = new TextView[]{
                findViewById(R.id.chip_amazing),
                findViewById(R.id.chip_strong),
                findViewById(R.id.chip_fresh),
                findViewById(R.id.chip_motivated),
                findViewById(R.id.chip_pumped)
        };
    }

    private void setupData() {
        String durationStr = getIntent().getStringExtra(IntentExtras.TOTAL_TIME);
        if (durationStr != null && !durationStr.isEmpty()) {
            tvDuration.setText(durationStr);
        } else {
            tvDuration.setText("00:00");
        }

        pbCompletion.setProgress(100);
        tvCompletionPct.setText("100% COMPLETED");
    }

    private void setupFeelingChips() {
        for (TextView chip : feelingChips) {
            chip.setOnClickListener(v -> {
                String text = chip.getText().toString();
                if (text.equalsIgnoreCase(selectedFeeling)) {
                    selectedFeeling = "";
                    deselectChip(chip);
                } else {
                    selectedFeeling = text;
                    for (TextView c : feelingChips) {
                        if (c == chip) selectChip(c);
                        else deselectChip(c);
                    }
                }
            });
        }
    }

    private void selectChip(TextView chip) {
        chip.setBackgroundResource(R.drawable.bg_feeling_chip_selected);
        chip.setTextColor(Color.WHITE);
    }

    private void deselectChip(TextView chip) {
        chip.setBackgroundResource(R.drawable.bg_feeling_chip);
        chip.setTextColor(getThemeColor(android.R.attr.textColorPrimary));
    }

    private void setupListeners() {
        if (ivClose != null) {
            ivClose.setOnClickListener(v -> finishAndReturnHome());
        }

        if (layoutAddPhoto != null) {
            layoutAddPhoto.setOnClickListener(v -> 
                Toast.makeText(this, "Photo upload coming soon!", Toast.LENGTH_SHORT).show()
            );
        }

        btnSave.setOnClickListener(v -> saveAndFinish());
    }

    private void saveAndFinish() {
        int userId = PreferenceUtils.getUserId(this);
        String todayStr = com.nct.trenx.utils.ProgressDataProvider.formatDateKey(Calendar.getInstance());
        String durationText = tvDuration.getText().toString();

        // TÍnh số giây từ chuỗi duration mm:ss hoặc hh:mm:ss
        int totalSeconds = parseDurationToSeconds(durationText);

        WorkoutHistory history = new WorkoutHistory(
                todayStr,
                "Workout Completed",
                selectedFeeling.isEmpty() ? "Intermediate" : selectedFeeling,
                100,
                5,
                totalSeconds,
                "Full Body"
        );

        // 1. Lưu Local SQLite
        dbHelper.addWorkoutHistory(userId, history);

        // 2. Đồng bộ Online Firebase Cloud Firestore
        firebaseRepo.addWorkoutHistory(history, new FirebaseRepository.SimpleCallback() {
            @Override
            public void onSuccess() {
                // Online sync success
            }

            @Override
            public void onFailure(String errorMsg) {
                // Fallback logged silently
            }
        });

        Toast.makeText(this, "Workout saved to history!", Toast.LENGTH_SHORT).show();
        finishAndReturnHome();
    }

    private void finishAndReturnHome() {
        Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private int parseDurationToSeconds(String durationStr) {
        if (durationStr == null) return 0;
        String[] parts = durationStr.split(":");
        try {
            if (parts.length == 2) {
                return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            } else if (parts.length == 3) {
                return Integer.parseInt(parts[0]) * 3600 + Integer.parseInt(parts[1]) * 60 + Integer.parseInt(parts[2]);
            }
        } catch (Exception ignored) {}
        return 0;
    }

    private int getThemeColor(int attrRes) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }
}
