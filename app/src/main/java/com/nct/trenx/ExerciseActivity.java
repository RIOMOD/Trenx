package com.nct.trenx;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    private RecyclerView rvExercises;
    private DatabaseHelper db;
    private TextView tvCategoryName;
    private Button btnBeginner, btnIntermediate, btnAdvanced;

    // Mặc định ban đầu
    private String currentDay = "Wednesday";
    private String currentDifficulty = "Intermediate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        db = new DatabaseHelper(this);
        rvExercises = findViewById(R.id.rvExercises);
        tvCategoryName = findViewById(R.id.tvCategoryName);

        btnBeginner = findViewById(R.id.btnBeginner);
        btnIntermediate = findViewById(R.id.btnIntermediate);
        btnAdvanced = findViewById(R.id.btnAdvanced);
        Button btnStartWorkout = findViewById(R.id.btnStartWorkout);

        // 1. Nhận dữ liệu ngày và tên buổi tập từ Dashboard (MainActivity)
        // Lưu ý: Key gửi từ MainActivity sang là "DAY_NAME"
        String dayFromIntent = getIntent().getStringExtra("DAY_NAME");
        String titleFromIntent = getIntent().getStringExtra("WORKOUT_TITLE");

        if (dayFromIntent != null) {
            currentDay = dayFromIntent;
        }
        if (titleFromIntent != null) {
            tvCategoryName.setText(titleFromIntent.toUpperCase());
        }

        // Load dữ liệu ban đầu
        updateButtonColors();
        loadExercises();

        // 2. Sự kiện khi chọn các mức độ
        btnBeginner.setOnClickListener(v -> {
            currentDifficulty = "Beginner";
            updateButtonColors();
            loadExercises();
        });

        btnIntermediate.setOnClickListener(v -> {
            currentDifficulty = "Intermediate";
            updateButtonColors();
            loadExercises();
        });

        btnAdvanced.setOnClickListener(v -> {
            currentDifficulty = "Advanced";
            updateButtonColors();
            loadExercises();
        });

        // 3. MỞ MÀN HÌNH TẬP LUYỆN (QUAN TRỌNG NHẤT)
        btnStartWorkout.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseActivity.this, TrainingActivity.class);

            // SỬA LỖI Ở ĐÂY: Phải gửi là "DAY_NAME" để TrainingActivity nhận được
            intent.putExtra("DAY_NAME", currentDay);
            intent.putExtra("DIFFICULTY", currentDifficulty);

            startActivity(intent);
        });

        // Nút quay lại
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void loadExercises() {
        // Lấy danh sách bài tập từ DB theo đúng Ngày và Độ khó
        List<Exercise> list = db.getExercisesByDayAndDifficulty(currentDay, currentDifficulty);
        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(new ExerciseAdapter(list));
    }

    private void updateButtonColors() {
        // Reset màu xám cho các nút không chọn
        btnBeginner.setTextColor(Color.parseColor("#888888"));
        btnIntermediate.setTextColor(Color.parseColor("#888888"));
        btnAdvanced.setTextColor(Color.parseColor("#888888"));

        // Bôi màu Xanh Neon cho nút được chọn
        switch (currentDifficulty) {
            case "Beginner":
                btnBeginner.setTextColor(Color.parseColor("#00FF00"));
                break;
            case "Intermediate":
                btnIntermediate.setTextColor(Color.parseColor("#00FF00"));
                break;
            case "Advanced":
                btnAdvanced.setTextColor(Color.parseColor("#00FF00"));
                break;
        }
    }
}