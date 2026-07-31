package com.nct.trenx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.activity.FitnessTestActivity;
import com.nct.trenx.adapter.OnboardingSelectionAdapter;
import com.nct.trenx.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnboardingStepFragment extends Fragment {

    private int step;
    private User user;
    private OnboardingSelectionAdapter adapter;
    private List<Integer> selectedPositions = new ArrayList<>();

    private NumberPicker numberPicker;
    private TextView btnUnitLeft, btnUnitRight;
    private boolean isMetric = true;

    private EditText etUsername, etFullName, etEmail, etPassword;
    private ImageView ivTogglePassword;
    private boolean isPasswordVisible = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        step = getArguments().getInt("step");
        user = (User) getArguments().getSerializable("user");

        View view;
        switch (step) {
            case 0: // Goals
                view = inflater.inflate(R.layout.fragment_onboarding_selection, container, false);
                List<String> goalTitles = Arrays.asList("Build Strength", "Build Muscle", "Lose Fat", "Learn Techniques");
                setupSelectionStep(view, "Goals", "Choose as many as you like",
                        goalTitles,
                        Arrays.asList("Get stronger and perform exercises with greater ease",
                                "Increase volume and difficulty to ensure muscle growth",
                                "Optimized for high intensity fat burning workouts",
                                "Master basic to advanced skills"),
                        true);
                if (user.getGoals() != null && !user.getGoals().isEmpty()) {
                    List<String> savedGoals = Arrays.asList(user.getGoals().split(", "));
                    List<Integer> positions = new ArrayList<>();
                    for (int i = 0; i < goalTitles.size(); i++) {
                        if (savedGoals.contains(goalTitles.get(i))) positions.add(i);
                    }
                    adapter.setSelectedPositions(positions);
                    selectedPositions = positions;
                }
                break;
            case 1: // Gender
                view = inflater.inflate(R.layout.fragment_onboarding_selection, container, false);
                List<String> genders = Arrays.asList("Male", "Female");
                setupSelectionStep(view, "Gender", "Select your gender",
                        genders, null, false);
                if (user.getGender() != null) {
                    int pos = genders.indexOf(user.getGender());
                    if (pos != -1) {
                        adapter.setSelectedPositions(Arrays.asList(pos));
                        selectedPositions = Arrays.asList(pos);
                    }
                }
                break;
            case 2: // Fitness Level
                view = inflater.inflate(R.layout.fragment_onboarding_selection, container, false);
                List<String> levels = Arrays.asList("Newbie", "Beginner", "Intermediate", "Advanced");
                setupSelectionStep(view, "Fitness Level", "Select your current level",
                        levels,
                        Arrays.asList("I've never trained before", "Some experience", "Moderate experience with consistent training", "Very experienced with consistent training"),
                        false);
                if (user.getFitnessLevel() != null) {
                    int pos = levels.indexOf(user.getFitnessLevel());
                    if (pos != -1) {
                        adapter.setSelectedPositions(Arrays.asList(pos));
                        selectedPositions = Arrays.asList(pos);
                    }
                }
                break;
            case 3: // Height
                view = inflater.inflate(R.layout.fragment_onboarding_number_picker, container, false);
                setupNumberPickerStep(view, "Your Height", 100, 250, user.getHeight() > 0 ? user.getHeight() : 170, "cm", "in");
                break;
            case 4: // Weight
                view = inflater.inflate(R.layout.fragment_onboarding_number_picker, container, false);
                setupNumberPickerStep(view, "Your Weight", 30, 200, user.getWeight() > 0 ? user.getWeight() : 70, "kg", "lbs");
                break;
            case 5: // Weight Goal
                view = inflater.inflate(R.layout.fragment_onboarding_number_picker, container, false);
                setupNumberPickerStep(view, "Your Weight Goal", 30, 200, user.getWeightGoal() > 0 ? user.getWeightGoal() : 70, "kg", "lbs");
                break;
            case 6: // Max Pullups
            case 7: // Max Pushups
            case 8: // Max Dips
            case 9: // Max Squats
                view = inflater.inflate(R.layout.fragment_onboarding_selection, container, false);
                setupMaxRepsStep(view, step);
                break;
            case 10: // Account Details
                view = inflater.inflate(R.layout.fragment_onboarding_account, container, false);
                setupAccountStep(view);
                etUsername.setText(user.getUsername());
                etFullName.setText(user.getFullName());
                etEmail.setText(user.getEmail());
                etPassword.setText(user.getPassword());
                break;
            default:
                view = new View(getContext());
        }
        return view;
    }

    private void setupMaxRepsStep(View view, int step) {
        String title = "";
        String subtitle = "";
        List<String> items = new ArrayList<>();
        String savedVal = "";
        final String testType;

        switch (step) {
            case 6:
                title = "Max Pullups";
                subtitle = "How many pullups can you do without interruption?";
                items = Arrays.asList("<6", "6 - 10", "11 - 15", ">15");
                savedVal = user.getMaxPullups();
                testType = "pullups";
                break;
            case 7:
                title = "Max Pushups";
                subtitle = "How many pushups can you do without interruption?";
                items = Arrays.asList("<11", "11 - 20", "21 - 30", ">30");
                savedVal = user.getMaxPushups();
                testType = "pushups";
                break;
            case 8:
                title = "Max Dips";
                subtitle = "How many dips can you do without interruption?";
                items = Arrays.asList("<9", "9 - 15", "16 - 25", ">25");
                savedVal = user.getMaxDips();
                testType = "dips";
                break;
            case 9:
                title = "Max Squats";
                subtitle = "How many squats can you do without interruption?";
                items = Arrays.asList("<21", "21 - 30", "31 - 40", ">40");
                savedVal = user.getMaxSquats();
                testType = "squats";
                break;
            default:
                testType = "";
        }

        setupSelectionStep(view, title, subtitle, items, null, false);
        View btnTestIt = view.findViewById(R.id.btn_test_it);
        btnTestIt.setVisibility(View.VISIBLE);
        btnTestIt.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FitnessTestActivity.class);
            intent.putExtra("test_type", testType); // Truyền loại bài test
            startActivity(intent);
        });

        if (savedVal != null) {
            int pos = items.indexOf(savedVal);
            if (pos != -1) {
                adapter.setSelectedPositions(Arrays.asList(pos));
                selectedPositions = Arrays.asList(pos);
            }
        }
    }

    private void setupSelectionStep(View view, String title, String subtitle, List<String> items, List<String> subtitles, boolean multi) {
        TextView tvTitle = view.findViewById(R.id.tv_selection_title);
        TextView tvSubtitle = view.findViewById(R.id.tv_selection_subtitle);
        RecyclerView rv = view.findViewById(R.id.rv_selection);

        tvTitle.setText(title);
        tvSubtitle.setText(subtitle);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OnboardingSelectionAdapter(items, subtitles, multi);
        rv.setAdapter(adapter);
        adapter.setOnItemSelectedListener(positions -> selectedPositions = positions);
    }

    private void setupNumberPickerStep(View view, String title, int min, int max, int val, String unitL, String unitR) {
        TextView tvTitle = view.findViewById(R.id.tv_picker_title);
        numberPicker = view.findViewById(R.id.number_picker);
        btnUnitLeft = view.findViewById(R.id.btn_unit_left);
        btnUnitRight = view.findViewById(R.id.btn_unit_right);

        tvTitle.setText(title);
        numberPicker.setMinValue(min);
        numberPicker.setMaxValue(max);
        numberPicker.setValue(val);
        btnUnitLeft.setText(unitL);
        btnUnitRight.setText(unitR);

        btnUnitLeft.setOnClickListener(v -> {
            isMetric = true;
            btnUnitLeft.setBackgroundResource(R.drawable.bg_selected_day);
            btnUnitLeft.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
            btnUnitLeft.setTextColor(getResources().getColor(android.R.color.white));
            btnUnitRight.setBackground(null);
            btnUnitRight.setTextColor(getResources().getColor(android.R.color.black));
        });

        btnUnitRight.setOnClickListener(v -> {
            isMetric = false;
            btnUnitRight.setBackgroundResource(R.drawable.bg_selected_day);
            btnUnitRight.setBackgroundTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(android.R.color.black)));
            btnUnitRight.setTextColor(getResources().getColor(android.R.color.white));
            btnUnitLeft.setBackground(null);
            btnUnitLeft.setTextColor(getResources().getColor(android.R.color.black));
        });
    }

    private void setupAccountStep(View view) {
        etUsername = view.findViewById(R.id.et_username);
        etFullName = view.findViewById(R.id.et_fullname);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        ivTogglePassword = view.findViewById(R.id.iv_toggle_password);

        ivTogglePassword.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                etPassword.setTransformationMethod(null);
                ivTogglePassword.setAlpha(1.0f);
            } else {
                etPassword.setTransformationMethod(new PasswordTransformationMethod());
                ivTogglePassword.setAlpha(0.6f);
            }
            etPassword.setSelection(etPassword.getText().length());
        });
    }

    public boolean saveData(User user) {
        switch (step) {
            case 0: // Goals
                if (selectedPositions.isEmpty()) {
                    Toast.makeText(getContext(), "Please select at least one goal", Toast.LENGTH_SHORT).show();
                    return false;
                }
                StringBuilder goals = new StringBuilder();
                List<String> goalItems = Arrays.asList("Build Strength", "Build Muscle", "Lose Fat", "Learn Techniques");
                for (int pos : selectedPositions) {
                    if (goals.length() > 0) goals.append(", ");
                    goals.append(goalItems.get(pos));
                }
                user.setGoals(goals.toString());
                return true;
            case 1: // Gender
                if (selectedPositions.isEmpty()) {
                    Toast.makeText(getContext(), "Please select your gender", Toast.LENGTH_SHORT).show();
                    return false;
                }
                user.setGender(selectedPositions.get(0) == 0 ? "Male" : "Female");
                return true;
            case 2: // Fitness Level
                if (selectedPositions.isEmpty()) {
                    Toast.makeText(getContext(), "Please select your fitness level", Toast.LENGTH_SHORT).show();
                    return false;
                }
                user.setFitnessLevel(Arrays.asList("Newbie", "Beginner", "Intermediate", "Advanced").get(selectedPositions.get(0)));
                return true;
            case 3: // Height
                user.setHeight(numberPicker.getValue());
                return true;
            case 4: // Weight
                user.setWeight(numberPicker.getValue());
                return true;
            case 5: // Weight Goal
                user.setWeightGoal(numberPicker.getValue());
                return true;
            case 6: // Max Pullups
                if (selectedPositions.isEmpty()) {
                    Toast.makeText(getContext(), "Please select your max pullups", Toast.LENGTH_SHORT).show();
                    return false;
                }
                user.setMaxPullups(Arrays.asList("<6", "6 - 10", "11 - 15", ">15").get(selectedPositions.get(0)));
                return true;
            case 7: // Max Pushups
                if (selectedPositions.isEmpty()) {
                    Toast.makeText(getContext(), "Please select your max pushups", Toast.LENGTH_SHORT).show();
                    return false;
                }
                user.setMaxPushups(Arrays.asList("<11", "11 - 20", "21 - 30", ">30").get(selectedPositions.get(0)));
                return true;
            case 8: // Max Dips
                if (selectedPositions.isEmpty()) {
                    Toast.makeText(getContext(), "Please select your max dips", Toast.LENGTH_SHORT).show();
                    return false;
                }
                user.setMaxDips(Arrays.asList("<9", "9 - 15", "16 - 25", ">25").get(selectedPositions.get(0)));
                return true;
            case 9: // Max Squats
                if (selectedPositions.isEmpty()) {
                    Toast.makeText(getContext(), "Please select your max squats", Toast.LENGTH_SHORT).show();
                    return false;
                }
                user.setMaxSquats(Arrays.asList("<21", "21 - 30", "31 - 40", ">40").get(selectedPositions.get(0)));
                return true;
            case 10: // Account Details
                String username = etUsername.getText().toString().trim();
                String fullName = etFullName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Áp dụng lớp kiểm tra của ResilienceLayer
                if (username.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (!com.nct.trenx.utils.ResilienceLayer.isValidEmail(email)) {
                    Toast.makeText(getContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (!com.nct.trenx.utils.ResilienceLayer.isValidPassword(password)) {
                    Toast.makeText(getContext(), "Password must be 6-64 characters", Toast.LENGTH_SHORT).show();
                    return false;
                }

                // Làm sạch các chuỗi trước khi lưu vào thực thể User
                user.setUsername(com.nct.trenx.utils.ResilienceLayer.sanitizeString(username, 50));
                user.setFullName(com.nct.trenx.utils.ResilienceLayer.sanitizeString(fullName, 100));
                user.setEmail(com.nct.trenx.utils.ResilienceLayer.sanitizeString(email, 100));
                user.setPassword(password);
                return true;
        }
        return false;
    }
}
