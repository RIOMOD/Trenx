package com.nct.trenx.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.nct.trenx.R;
import com.nct.trenx.utils.PreferenceUtils;

public class SplashActivity extends BaseActivity {

    TextView tvLogo;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize theme before super.onCreate
        PreferenceUtils.applyTheme(PreferenceUtils.isDarkMode(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvLogo = findViewById(R.id.tv_logo);

        // Fade In effect
        ObjectAnimator fade = ObjectAnimator.ofFloat(tvLogo, View.ALPHA, 0f, 1f);
        fade.setDuration(1500);

        // Zoom effect
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvLogo, View.SCALE_X, 0.5f, 1f);
        scaleX.setDuration(1500);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvLogo, View.SCALE_Y, 0.5f, 1f);
        scaleY.setDuration(1500);

        // Slide up effect
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(tvLogo, View.TRANSLATION_Y, 100f, 0f);
        moveUp.setDuration(1500);

        // Run together
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fade, scaleX, scaleY, moveUp);
        animatorSet.start();

        // Transition to MainActivity after 2 seconds
        handler = new Handler(android.os.Looper.getMainLooper());
        runnable = () -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        };
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
