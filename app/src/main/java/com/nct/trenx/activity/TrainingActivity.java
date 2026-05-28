package com.nct.trenx.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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

import com.nct.trenx.R;
import com.nct.trenx.database.ExerciseRepository;
import com.nct.trenx.model.Exercise;
import com.nct.trenx.utils.ImageUtils;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.TrainingTimerHelper;

import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    private TextView tvTimer, tvCurrentExName, tvRepsCount, tvRoundInfo;
    private ImageView vvTrainingVideo, btnClose;
    private Button btnComplete;
    private LinearLayout layoutProgress;
    private ScrollView svMainContent;

    private ImageView ivNextExThumb;
    private TextView tvNextExDesc;

    private ConstraintLayout layoutRest;
    private TextView tvRestCount, tvRestNextName, tvRestNextInfo;
    private ImageView ivRestNextThumb;
    private Button btnMinus10, btnPlus10, btnSkipRest;

    private List<Exercise> exerciseList;
    private ExerciseRepository repository;
    private TrainingTimerHelper timerHelper;

    private int actualIndex = 0;
    private int viewingIndex = 0;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        initViews();
        repository = new ExerciseRepository(this);
        timerHelper = new TrainingTimerHelper();
        timerHelper.setListener(new TrainingTimerHelper.Listener() {
            @Override
            public void onElapsedTick(String formattedTime) {
                tvTimer.setText(formattedTime);
            }

            @Override
            public void onRestTick(int secondsLeft) {
                tvRestCount.setText(String.valueOf(secondsLeft));
            }

            @Override
            public void onRestFinished() {
                finishRestAndNext();
            }
        });

        Intent intent = getIntent();
        String day = IntentExtras.getDayName(intent);
        String difficulty = IntentExtras.getDifficulty(intent);

        actualIndex = intent.getIntExtra(IntentExtras.POSITION, 0);
        viewingIndex = actualIndex;

        exerciseList = repository.getExercisesByDayAndDifficulty(day, difficulty);

        if (exerciseList == null || exerciseList.isEmpty()) {
            Toast.makeText(this, "Chưa có bài tập!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        timerHelper.startElapsedTimer();
        setupSwipeGesture();

        updateProgressBar();
        loadExerciseData(viewingIndex);

        btnSkipRest.setOnClickListener(v -> finishRestAndNext());
        btnPlus10.setOnClickListener(v -> timerHelper.adjustRestTime(10_000));
        btnMinus10.setOnClickListener(v -> timerHelper.adjustRestTime(-10_000));

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> finish());
        }
    }

    private void initViews() {
        tvTimer = findViewById(R.id.tv_timer);
        layoutProgress = findViewById(R.id.layout_progress);
        tvRepsCount = findViewById(R.id.tv_reps_count);
        tvCurrentExName = findViewById(R.id.tv_current_ex_name);
        vvTrainingVideo = findViewById(R.id.vv_training_video);
        ivNextExThumb = findViewById(R.id.iv_next_ex_thumb);
        tvNextExDesc = findViewById(R.id.tv_next_ex_desc);
        tvRoundInfo = findViewById(R.id.tv_round_info);
        btnComplete = findViewById(R.id.btn_complete);
        btnClose = findViewById(R.id.btn_close);
        svMainContent = findViewById(R.id.sv_main_content);

        layoutRest = findViewById(R.id.layout_rest);
        tvRestCount = findViewById(R.id.tv_rest_count);
        tvRestNextName = findViewById(R.id.tv_rest_next_name);
        tvRestNextInfo = findViewById(R.id.tv_rest_next_info);
        ivRestNextThumb = findViewById(R.id.iv_rest_next_thumb);
        btnMinus10 = findViewById(R.id.btn_minus_10);
        btnPlus10 = findViewById(R.id.btn_plus_10);
        btnSkipRest = findViewById(R.id.btn_skip_rest);
    }

    private void setupSwipeGesture() {
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 80;
            private static final int SWIPE_VELOCITY_THRESHOLD = 80;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null || e2 == null) {
                    return false;
                }

                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(e2.getY() - e1.getY())
                        && Math.abs(diffX) > SWIPE_THRESHOLD
                        && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {

                    if (diffX > 0) {
                        if (viewingIndex > 0) {
                            viewingIndex--;
                            loadExerciseData(viewingIndex);
                        }
                    } else {
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (layoutRest.getVisibility() == View.VISIBLE) {
            return super.dispatchTouchEvent(ev);
        }

        if (gestureDetector != null && gestureDetector.onTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void loadExerciseData(int index) {
        Exercise currentEx = exerciseList.get(index);
        tvCurrentExName.setText(currentEx.getName());
        tvRepsCount.setText(currentEx.getReps().replaceAll("[^0-9]", ""));
        tvRoundInfo.setText("Round 1  |  " + (index + 1) + "st exercise");
        ImageUtils.loadExerciseThumb(this, vvTrainingVideo, currentEx.getImageName());

        if (index < exerciseList.size() - 1) {
            Exercise nextEx = exerciseList.get(index + 1);
            tvNextExDesc.setText(nextEx.getName());
            ImageUtils.loadExerciseThumb(this, ivNextExThumb, nextEx.getImageName());
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

    private void showRestScreen() {
        svMainContent.setVisibility(View.GONE);
        btnComplete.setVisibility(View.GONE);
        tvRoundInfo.setVisibility(View.GONE);
        layoutRest.setVisibility(View.VISIBLE);

        Exercise nextEx = exerciseList.get(actualIndex);
        tvRestNextName.setText(nextEx.getName());
        tvRestNextInfo.setText(nextEx.getReps() + " • Rest 45 seconds");
        ImageUtils.loadExerciseThumb(this, ivRestNextThumb, nextEx.getImageName());

        timerHelper.resetRestDuration();
        timerHelper.startRestTimer();
    }

    private void finishRestAndNext() {
        timerHelper.cancelRestTimer();

        layoutRest.setVisibility(View.GONE);
        svMainContent.setVisibility(View.VISIBLE);
        btnComplete.setVisibility(View.VISIBLE);
        tvRoundInfo.setVisibility(View.VISIBLE);

        loadExerciseData(actualIndex);
    }

    private void finishWorkout() {
        timerHelper.stopElapsedTimer();
        Intent intent = new Intent(TrainingActivity.this, ReviewActivity.class);
        intent.putExtra(IntentExtras.TOTAL_TIME, tvTimer.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        timerHelper.release();
        super.onDestroy();
    }
}
