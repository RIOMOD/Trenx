package com.nct.trenx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvReps = findViewById(R.id.tvDetailReps);
        TextView tvDesc = findViewById(R.id.tvDetailDesc);
        Button btnStart = findViewById(R.id.btnStartWorkout);

        // Nhận dữ liệu
        String name = getIntent().getStringExtra("NAME");
        String reps = getIntent().getStringExtra("REPS");
        String desc = getIntent().getStringExtra("DESC");

        tvName.setText(name);
        tvReps.setText(reps);
        tvDesc.setText(desc);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, TrainingActivity.class);
            startActivity(intent);
        });
    }
}