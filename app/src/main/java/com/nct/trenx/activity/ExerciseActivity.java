package com.nct.trenx.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.adapter.ExerciseAdapter;
import com.nct.trenx.database.ExerciseRepository;
import com.nct.trenx.model.Exercise;
import com.nct.trenx.utils.DifficultyUiHelper;
import com.nct.trenx.utils.IntentExtras;
import com.nct.trenx.utils.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    private RecyclerView rvExercises;
    private ExerciseRepository repository;
    private ExerciseAdapter exerciseAdapter;
    private TextView tvCategoryName;
    private Button btnBeginner, btnIntermediate, btnAdvanced;

    private String currentDay = IntentExtras.DEFAULT_DAY;
    private String currentDifficulty = IntentExtras.DEFAULT_DIFFICULTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        repository = new ExerciseRepository(this);
        rvExercises = findViewById(R.id.rv_exercises);
        tvCategoryName = findViewById(R.id.tv_category_name);

        btnBeginner = findViewById(R.id.btn_beginner);
        btnIntermediate = findViewById(R.id.btn_intermediate);
        btnAdvanced = findViewById(R.id.btn_advanced);
        Button btnStartWorkout = findViewById(R.id.btn_start_workout);

        String dayFromIntent = getIntent().getStringExtra(IntentExtras.DAY_NAME);
        String titleFromIntent = getIntent().getStringExtra(IntentExtras.WORKOUT_TITLE);

        if (dayFromIntent != null) {
            currentDay = dayFromIntent;
        }
        if (titleFromIntent != null) {
            tvCategoryName.setText(titleFromIntent.toUpperCase());
        }

        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseAdapter(new ArrayList<>());
        exerciseAdapter.setOnExerciseClickListener((exercise, position) ->
                NavigationUtils.startTraining(ExerciseActivity.this,
                        exercise.getDay(), exercise.getDifficulty(), position));
        rvExercises.setAdapter(exerciseAdapter);

        updateButtonColors();
        loadExercises();

        btnBeginner.setOnClickListener(v -> selectDifficulty(IntentExtras.DIFFICULTY_BEGINNER));
        btnIntermediate.setOnClickListener(v -> selectDifficulty(IntentExtras.DIFFICULTY_INTERMEDIATE));
        btnAdvanced.setOnClickListener(v -> selectDifficulty(IntentExtras.DIFFICULTY_ADVANCED));

        btnStartWorkout.setOnClickListener(v ->
                NavigationUtils.startTraining(ExerciseActivity.this, currentDay, currentDifficulty));

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void selectDifficulty(String difficulty) {
        currentDifficulty = difficulty;
        updateButtonColors();
        loadExercises();
    }

    private void loadExercises() {
        List<Exercise> list = repository.getExercisesByDayAndDifficulty(currentDay, currentDifficulty);
        exerciseAdapter.updateData(list);
    }

    private void updateButtonColors() {
        DifficultyUiHelper.applySelection(btnBeginner, btnIntermediate, btnAdvanced, currentDifficulty);
    }
}
