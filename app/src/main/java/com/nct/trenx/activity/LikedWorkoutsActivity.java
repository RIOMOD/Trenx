package com.nct.trenx.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.adapter.LikedWorkoutAdapter;
import com.nct.trenx.database.ExerciseRepository;
import com.nct.trenx.utils.PreferenceUtils;

public class LikedWorkoutsActivity extends BaseActivity {

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

        TextView tvTitle = findViewById(R.id.tv_toolbar_title);
        if (tvTitle != null) {
            tvTitle.setText(R.string.all_liked_workouts_title);
        }

        repository = new ExerciseRepository(this);
        rvLikedWorkouts = findViewById(R.id.rv_liked_workouts);
        rvLikedWorkouts.setLayoutManager(new LinearLayoutManager(this));

        int userId = PreferenceUtils.getUserId(this);
        adapter = new LikedWorkoutAdapter(repository.getLikedExercises(userId));
        rvLikedWorkouts.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (repository != null && adapter != null) {
            int userId = PreferenceUtils.getUserId(this);
            adapter.updateData(repository.getLikedExercises(userId));
        }
    }
}
