package com.nct.trenx;

import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    TextView txtLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txtLogo = findViewById(R.id.txtLogo);

        // Hiệu ứng Fade In
        ObjectAnimator fade = ObjectAnimator.ofFloat(txtLogo, View.ALPHA, 0f, 1f);
        fade.setDuration(1500);

        // Hiệu ứng Zoom
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(txtLogo, View.SCALE_X, 0.5f, 1f);
        scaleX.setDuration(1500);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(txtLogo, View.SCALE_Y, 0.5f, 1f);
        scaleY.setDuration(1500);

        // Hiệu ứng trượt lên
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(txtLogo, View.TRANSLATION_Y, 100f, 0f);
        moveUp.setDuration(1500);

        // Chạy cùng lúc
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fade, scaleX, scaleY, moveUp);
        animatorSet.start();

        // Chuyển sang MainActivity sau 2 giây
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}