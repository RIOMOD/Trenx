package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nct.trenx.R;
import com.nct.trenx.database.DatabaseHelper;
import com.nct.trenx.fragment.OnboardingStepFragment;
import com.nct.trenx.model.User;
import com.nct.trenx.utils.PreferenceUtils;

public class OnboardingActivity extends BaseActivity {

    private ProgressBar progressBar;
    private Button btnContinue;
    private User tempUser;
    private int currentStep = 0;
    private final int TOTAL_STEPS = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        progressBar = findViewById(R.id.pb_onboarding);
        btnContinue = findViewById(R.id.btn_continue);
        tempUser = new User();

        findViewById(R.id.btn_back).setOnClickListener(v -> {
            if (currentStep > 0) {
                currentStep--;
                showStep();
            } else {
                finish();
            }
        });

        btnContinue.setOnClickListener(v -> {
            if (validateAndSaveCurrentStep()) {
                if (currentStep < TOTAL_STEPS - 1) {
                    currentStep++;
                    showStep();
                } else {
                    registerUser();
                }
            }
        });

        showStep();
    }

    private void showStep() {
        progressBar.setProgress(currentStep + 1);
        Fragment fragment = createFragmentForStep(currentStep);
        
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, "step_" + currentStep)
                .commit();
    }

    private Fragment createFragmentForStep(int step) {
        Bundle args = new Bundle();
        args.putInt("step", step);
        args.putSerializable("user", tempUser);
        
        OnboardingStepFragment fragment = new OnboardingStepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean validateAndSaveCurrentStep() {
        OnboardingStepFragment fragment = (OnboardingStepFragment) getSupportFragmentManager().findFragmentByTag("step_" + currentStep);
        if (fragment != null) {
            return fragment.saveData(tempUser);
        }
        return false;
    }

    private void registerUser() {
        DatabaseHelper db = new DatabaseHelper(this);
        long id = db.registerUser(tempUser);
        if (id > 0) {
            PreferenceUtils.saveUserSession(this, (int) id, tempUser.getEmail());
            // Sau khi đăng ký xong -> Vào thẳng Premium như yêu cầu
            Intent intent = new Intent(this, PremiumActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Registration failed. Email might already exist.", Toast.LENGTH_SHORT).show();
        }
    }
}
