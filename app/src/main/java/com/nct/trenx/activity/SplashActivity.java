package com.nct.trenx.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nct.trenx.R;

public class SplashActivity extends AppCompatActivity {

    TextView tvLogo;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvLogo = findViewById(R.id.tv_logo);

        // Hiệu ứng Fade In
        ObjectAnimator fade = ObjectAnimator.ofFloat(tvLogo, View.ALPHA, 0f, 1f);
        fade.setDuration(1500);

        // Hiệu ứng Zoom
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvLogo, View.SCALE_X, 0.5f, 1f);
        scaleX.setDuration(1500);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvLogo, View.SCALE_Y, 0.5f, 1f);
        scaleY.setDuration(1500);

        // Hiệu ứng trượt lên
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(tvLogo, View.TRANSLATION_Y, 100f, 0f);
        moveUp.setDuration(1500);

        // Chạy cùng lúc
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fade, scaleX, scaleY, moveUp);
        animatorSet.start();

        // Chuyển sang MainActivity sau 2 giây
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
