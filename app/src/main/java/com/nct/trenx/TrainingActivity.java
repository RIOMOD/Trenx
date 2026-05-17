package com.nct.trenx;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class TrainingActivity extends AppCompatActivity {

    private TextView tvTimer, tvTrainingTitle, tvCurrentExName, tvRepsCount, tvRoundInfo;
    private ImageView vvTrainingVideo, btnClose;
    private Button btnComplete;
    private LinearLayout layoutProgress;
    private ScrollView svMainContent; // Vùng cuộn của bài tập

    private ImageView ivNextExThumb;
    private TextView tvNextExDesc;

    private ConstraintLayout layoutRest;
    private TextView tvRestCount, tvRestNextName, tvRestNextInfo;
    private ImageView ivRestNextThumb;
    private Button btnMinus10, btnPlus10, btnSkipRest;

    private List<Exercise> exerciseList;
    private DatabaseHelper db;

    private int actualIndex = 0;
    private int viewingIndex = 0;

    private long startTimeMillis;
    private Handler totalTimeHandler = new Handler();
    private Runnable totalTimeRunnable;
    private CountDownTimer restDownTimer;
    private long restTimeLeftInMillis = 45000;

    // Bộ bắt vuốt (Tuyệt chiêu giúp vuốt mượt mà)
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        initViews();
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        String day = intent.getStringExtra("DAY_NAME");
        String difficulty = intent.getStringExtra("DIFFICULTY");
        if (difficulty == null) difficulty = "Beginner";

        actualIndex = intent.getIntExtra("POSITION", 0);
        viewingIndex = actualIndex;

        exerciseList = db.getExercisesByDayAndDifficulty(day, difficulty);

        if (exerciseList == null || exerciseList.isEmpty()) {
            Toast.makeText(this, "Chưa có bài tập!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        startTimeMillis = System.currentTimeMillis();
        startTotalTimer();
        setupSwipeGesture(); // Khởi tạo bộ bắt vuốt

        updateProgressBar();
        loadExerciseData(viewingIndex);

        btnSkipRest.setOnClickListener(v -> finishRestAndNext());
        btnPlus10.setOnClickListener(v -> adjustRestTime(10000));
        btnMinus10.setOnClickListener(v -> adjustRestTime(-10000));

        if (btnClose != null) btnClose.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvTrainingTitle = findViewById(R.id.tvTrainingTitle);
        tvTimer = findViewById(R.id.tvTimer);
        layoutProgress = findViewById(R.id.layoutProgress);
        tvRepsCount = findViewById(R.id.tvRepsCount);
        tvCurrentExName = findViewById(R.id.tvCurrentExName);
        vvTrainingVideo = findViewById(R.id.vvTrainingVideo);
        ivNextExThumb = findViewById(R.id.ivNextExThumb);
        tvNextExDesc = findViewById(R.id.tvNextExDesc);
        tvRoundInfo = findViewById(R.id.tvRoundInfo);
        btnComplete = findViewById(R.id.btnComplete);
        btnClose = findViewById(R.id.btnClose);
        svMainContent = findViewById(R.id.svMainContent); // Ánh xạ ScrollView chính

        layoutRest = findViewById(R.id.layoutRest);
        tvRestCount = findViewById(R.id.tvRestCount);
        tvRestNextName = findViewById(R.id.tvRestNextName);
        tvRestNextInfo = findViewById(R.id.tvRestNextInfo);
        ivRestNextThumb = findViewById(R.id.ivRestNextThumb);
        btnMinus10 = findViewById(R.id.btnMinus10);
        btnPlus10 = findViewById(R.id.btnPlus10);
        btnSkipRest = findViewById(R.id.btnSkipRest);
    }

    // --- BẮT SỰ KIỆN VUỐT CHUYÊN NGHIỆP ---
    private void setupSwipeGesture() {
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 80;
            private static final int SWIPE_VELOCITY_THRESHOLD = 80;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null || e2 == null) return false;

                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(e2.getY() - e1.getY())
                        && Math.abs(diffX) > SWIPE_THRESHOLD
                        && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {

                    if (diffX > 0) { // Vuốt sang PHẢI (Xem bài trước đó)
                        if (viewingIndex > 0) {
                            viewingIndex--;
                            loadExerciseData(viewingIndex);
                        }
                    } else { // Vuốt sang TRÁI (Xem bài tiếp theo)
                        if (viewingIndex < exerciseList.size() - 1) {
                            viewingIndex++;
                            loadExerciseData(viewingIndex);
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    // Ghi đè hàm này để ép Android ưu tiên nhận vuốt màn hình
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Nếu màn hình Rest đang hiện thì không cho vuốt
        if (layoutRest.getVisibility() == View.VISIBLE) {
            return super.dispatchTouchEvent(ev);
        }

        // Nhường sự kiện cho bộ bắt vuốt xử lý trước
        if (gestureDetector != null && gestureDetector.onTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    // --- HÀM LOAD DỮ LIỆU ---
    private void loadExerciseData(int index) {
        Exercise currentEx = exerciseList.get(index);
        tvCurrentExName.setText(currentEx.getName());
        tvRepsCount.setText(currentEx.getReps().replaceAll("[^0-9]", ""));
        tvRoundInfo.setText("Round 1  |  " + (index + 1) + "st exercise");
        Glide.with(this).load(currentEx.getImageName()).centerCrop().into(vvTrainingVideo);

        if (index < exerciseList.size() - 1) {
            Exercise nextEx = exerciseList.get(index + 1);
            tvNextExDesc.setText(nextEx.getName());
            Glide.with(this).load(nextEx.getImageName()).centerCrop().into(ivNextExThumb);
        } else {
            tvNextExDesc.setText("Workout Finished!");
            ivNextExThumb.setImageDrawable(null);
        }

        if (viewingIndex == actualIndex) {
            btnComplete.setText("Complete Exercise");
            btnComplete.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#262626")));
            btnComplete.setOnClickListener(v -> {
                actualIndex++;
                viewingIndex = actualIndex;
                updateProgressBar();

                if (actualIndex < exerciseList.size()) {
                    showRestScreen();
                } else {
                    finishWorkout();
                }
            });
        } else {
            btnComplete.setText("Back to Current");
            btnComplete.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#555555")));
            btnComplete.setOnClickListener(v -> {
                viewingIndex = actualIndex;
                loadExerciseData(viewingIndex);
            });
        }
    }

    private void updateProgressBar() {
        layoutProgress.removeAllViews();
        for (int i = 0; i < exerciseList.size(); i++) {
            View segment = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 8, 1f);
            params.setMargins(6, 0, 6, 0);
            segment.setLayoutParams(params);
            segment.setBackgroundColor(i < actualIndex ? Color.parseColor("#00C853") : Color.parseColor("#333333"));
            layoutProgress.addView(segment);
        }
    }

    // --- REST OVERLAY ---
    private void showRestScreen() {
        // Ẩn nội dung chính, chừa lại Header và Progress Bar
        svMainContent.setVisibility(View.GONE);
        btnComplete.setVisibility(View.GONE);
        tvRoundInfo.setVisibility(View.GONE);

        // Hiện màn hình đếm ngược
        layoutRest.setVisibility(View.VISIBLE);

        Exercise nextEx = exerciseList.get(actualIndex);
        tvRestNextName.setText(nextEx.getName());
        tvRestNextInfo.setText(nextEx.getReps() + " • Rest 45 seconds");
        Glide.with(this).load(nextEx.getImageName()).centerCrop().into(ivRestNextThumb);

        restTimeLeftInMillis = 45000;
        startRestTimer();
    }

    private void startRestTimer() {
        if (restDownTimer != null) restDownTimer.cancel();
        restDownTimer = new CountDownTimer(restTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millis) {
                restTimeLeftInMillis = millis;
                tvRestCount.setText("" + (millis / 1000));
            }
            @Override
            public void onFinish() { finishRestAndNext(); }
        }.start();
    }

    private void adjustRestTime(long millis) {
        restTimeLeftInMillis += millis;
        if (restTimeLeftInMillis < 0) restTimeLeftInMillis = 0;
        startRestTimer();
    }

    private void finishRestAndNext() {
        if (restDownTimer != null) restDownTimer.cancel();

        // Ẩn Rest, Hiện lại nội dung chính
        layoutRest.setVisibility(View.GONE);
        svMainContent.setVisibility(View.VISIBLE);
        btnComplete.setVisibility(View.VISIBLE);
        tvRoundInfo.setVisibility(View.VISIBLE);

        loadExerciseData(actualIndex);
    }

    private void startTotalTimer() {
        totalTimeRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - startTimeMillis;
                int mins = (int) (elapsed / 1000) / 60;
                int secs = (int) (elapsed / 1000) % 60;
                tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", mins, secs));
                totalTimeHandler.postDelayed(this, 1000);
            }
        };
        totalTimeHandler.post(totalTimeRunnable);
    }

    private void finishWorkout() {
        totalTimeHandler.removeCallbacks(totalTimeRunnable);
        Intent intent = new Intent(TrainingActivity.this, ReviewActivity.class);
        intent.putExtra("TOTAL_TIME", tvTimer.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (restDownTimer != null) restDownTimer.cancel();
        totalTimeHandler.removeCallbacks(totalTimeRunnable);
    }
}