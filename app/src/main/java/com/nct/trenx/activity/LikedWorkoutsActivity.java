package com.nct.trenx.activity;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.adapter.LikedWorkoutAdapter;
import com.nct.trenx.database.ExerciseRepository;

public class LikedWorkoutsActivity extends AppCompatActivity {

    private RecyclerView rvLikedWorkouts;
    private LikedWorkoutAdapter adapter;
    private ExerciseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_workouts);

        ImageView btnBack = findViewById(R.id.iv_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        android.widget.TextView tvTitle = findViewById(R.id.tv_toolbar_title);
        if (tvTitle != null) {
            tvTitle.setText(R.string.all_liked_workouts_title);
        }

        repository = new ExerciseRepository(this);
        rvLikedWorkouts = findViewById(R.id.rv_liked_workouts);
        rvLikedWorkouts.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LikedWorkoutAdapter(repository.getLikedExercises());
        rvLikedWorkouts.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (repository != null && adapter != null) {
            adapter.updateData(repository.getLikedExercises());
        }
    }
}
