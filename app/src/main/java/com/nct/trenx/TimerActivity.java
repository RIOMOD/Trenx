package com.nct.trenx;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private TextView tvTimer, tvExerciseName;
    private Button btnStartPause, btnReset;
    private View btnNext, btnPrevious;
    private ImageView ivExerciseVideo;

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 45000;
    private final long START_TIME_IN_MILLIS = 45000;

    private List<Exercise> exerciseList;
    private int currentPosition;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // Ánh xạ View
        tvTimer = findViewById(R.id.tvTimer);
        tvExerciseName = findViewById(R.id.tvExerciseNameTimer);
        btnStartPause = findViewById(R.id.btnStartPause);
        btnReset = findViewById(R.id.btnReset);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        ivExerciseVideo = findViewById(R.id.ivExerciseVideo);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("POSITION", 0);
        String category = intent.getStringExtra("CATEGORY");

        exerciseList = db.getExercisesByCategory(category);

        loadExerciseData();

        btnStartPause.setOnClickListener(v -> {
            if (btnStartPause.getText().toString().equals("XONG!")) {
                handleNextExercise();
            } else {
                if (isTimerRunning) pauseTimer();
                else startTimer();
            }
        });

        btnReset.setOnClickListener(v -> resetTimer());

        btnNext.setOnClickListener(v -> {
            if (currentPosition < exerciseList.size() - 1) {
                currentPosition++;
                loadExerciseData();
            } else {
                Toast.makeText(this, "Đây đã là bài cuối cùng!", Toast.LENGTH_SHORT).show();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
                loadExerciseData();
            } else {
                Toast.makeText(this, "Đây là bài đầu tiên!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadExerciseData() {
        if (exerciseList != null && currentPosition < exerciseList.size()) {
            Exercise currentEx = exerciseList.get(currentPosition);
            tvExerciseName.setText(currentEx.getName().toUpperCase());

            int imageId = getResources().getIdentifier(currentEx.getImageName(), "drawable", getPackageName());
            if (imageId != 0) {
                ivExerciseVideo.setImageResource(imageId);
            } else {
                ivExerciseVideo.setImageResource(R.color.card_dark);
            }

            resetTimer();
        }
    }

    // --- ĐÂY LÀ ĐOẠN UPDATE QUAN TRỌNG NHẤT ---
    private void handleNextExercise() {
        if (currentPosition < exerciseList.size() - 1) {
            // Nếu chưa hết bài, chuyển sang bài tiếp theo
            currentPosition++;
            loadExerciseData();
        } else {
            // NẾU ĐÃ TẬP XONG BÀI CUỐI CÙNG: Mở màn hình Review
            Intent intent = new Intent(TimerActivity.this, ReviewActivity.class);
            startActivity(intent);

            // Kết thúc TimerActivity để người dùng không bấm Back quay lại được
            finish();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                btnStartPause.setText("XONG!");

                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) { e.printStackTrace(); }
            }
        }.start();

        isTimerRunning = true;
        btnStartPause.setText("TẠM DỪNG");
    }

    private void pauseTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        isTimerRunning = false;
        btnStartPause.setText("TIẾP TỤC");
    }

    private void resetTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        isTimerRunning = false;
        btnStartPause.setText("BẮT ĐẦU");
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}