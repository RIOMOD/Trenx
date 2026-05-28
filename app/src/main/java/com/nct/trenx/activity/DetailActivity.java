package com.nct.trenx.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.nct.trenx.R;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.NavigationUtils;

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
        String name = getIntent().getStringExtra(IntentExtras.EXERCISE_NAME);
        String reps = getIntent().getStringExtra(IntentExtras.EXERCISE_REPS);
        String desc = getIntent().getStringExtra(IntentExtras.EXERCISE_DESC);
        String dayName = getIntent().getStringExtra(IntentExtras.DAY_NAME);
        String difficulty = getIntent().getStringExtra(IntentExtras.DIFFICULTY);

        tvName.setText(name);
        tvReps.setText(reps);
        tvDesc.setText(desc);

        btnStart.setOnClickListener(v ->
                NavigationUtils.startTraining(DetailActivity.this, dayName, difficulty));
    }
}
