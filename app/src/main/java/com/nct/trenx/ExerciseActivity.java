package com.nct.trenx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        TextView tvCategoryName = findViewById(R.id.tvCategoryName);
        TextView btnBack = findViewById(R.id.btnBack);
        RecyclerView rvExercises = findViewById(R.id.rvExercises);

        String category = getIntent().getStringExtra("NHOM_CO");
        tvCategoryName.setText(category != null ? category.toUpperCase() : "BÀI TẬP");

        // Lấy dữ liệu từ SQLite theo nhóm cơ
        DatabaseHelper db = new DatabaseHelper(this);
        List<Exercise> list = db.getExercisesByCategory(category);

        // Hiển thị lên RecyclerView
        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(new ExerciseAdapter(list));

        btnBack.setOnClickListener(v -> finish());
    }
}