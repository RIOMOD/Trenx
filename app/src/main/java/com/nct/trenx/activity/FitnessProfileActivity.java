package com.nct.trenx.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nct.trenx.R;
import com.nct.trenx.database.DatabaseHelper;
import com.nct.trenx.model.User;
import com.nct.trenx.utils.PreferenceUtils;

/**
 * Màn hình Hồ sơ thể lực (Fitness Profile).
 */
public class FitnessProfileActivity extends BaseActivity {

    private TextView tvGender, tvHeight, tvWeight, tvWeightGoal, tvFitnessLevel, tvGoals;
    private TextView tvMaxPullups, tvMaxPushups, tvMaxSquats, tvMaxDips;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_profile);

        dbHelper = new DatabaseHelper(this);

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        initViews();
        loadProfileData();
        setupListeners();
    }

    private void initViews() {
        tvGender = findViewById(R.id.tvGender);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvWeightGoal = findViewById(R.id.tvWeightGoal);
        tvFitnessLevel = findViewById(R.id.tvFitnessLevel);
        tvGoals = findViewById(R.id.tvGoals);

        tvMaxPullups = findViewById(R.id.tvMaxPullups);
        tvMaxPushups = findViewById(R.id.tvMaxPushups);
        tvMaxSquats = findViewById(R.id.tvMaxSquats);
        tvMaxDips = findViewById(R.id.tvMaxDips);
    }

    private void loadProfileData() {
        int userId = PreferenceUtils.getUserId(this);
        User user = dbHelper.getUserById(userId);

        if (user != null) {
            tvGender.setText(user.getGender() != null ? user.getGender() : "Male");
            tvHeight.setText(user.getHeight() > 0 ? user.getHeight() + " cm" : "172 cm");
            tvWeight.setText(user.getWeight() > 0 ? user.getWeight() + " kg" : "65 kg");
            tvWeightGoal.setText(user.getWeightGoal() > 0 ? user.getWeightGoal() + " kg" : "70 kg");
            tvFitnessLevel.setText(user.getFitnessLevel() != null ? user.getFitnessLevel() : "Intermediate");
            tvGoals.setText(user.getGoals() != null ? user.getGoals() : "Build Strength, Build Muscle, Learn Techniques");

            tvMaxPullups.setText(user.getMaxPullups() != null ? user.getMaxPullups() : ">15");
            tvMaxPushups.setText(user.getMaxPushups() != null ? user.getMaxPushups() : ">30");
            tvMaxSquats.setText(user.getMaxSquats() != null ? user.getMaxSquats() : ">40");
            tvMaxDips.setText(user.getMaxDips() != null ? user.getMaxDips() : "16 - 25");
        } else {
            tvGender.setText("Male");
            tvHeight.setText("172 cm");
            tvWeight.setText("65 kg");
            tvWeightGoal.setText("70 kg");
            tvFitnessLevel.setText("Intermediate");
            tvGoals.setText("Build Strength, Build Muscle, Learn Techniques");
            tvMaxPullups.setText(">15");
            tvMaxPushups.setText(">30");
            tvMaxSquats.setText(">40");
            tvMaxDips.setText("16 - 25");
        }
    }

    private void setupListeners() {
        setClickToast(R.id.btnGender, "Gender");
        setClickToast(R.id.btnHeight, "Height");
        setClickToast(R.id.btnWeight, "Weight");
        setClickToast(R.id.btnWeightGoal, "Weight Goal");
        setClickToast(R.id.btnFitnessLevel, "Fitness Level");
        setClickToast(R.id.btnGoals, "Goals");
        setClickToast(R.id.btnMaxPullups, "Max Pullups");
        setClickToast(R.id.btnMaxPushups, "Max Pushups");
        setClickToast(R.id.btnMaxSquats, "Max Squats");
        setClickToast(R.id.btnMaxDips, "Max Dips");
    }

    private void setClickToast(int id, String title) {
        findViewById(id).setOnClickListener(v -> 
            Toast.makeText(this, "Edit " + title, Toast.LENGTH_SHORT).show()
        );
    }
}
