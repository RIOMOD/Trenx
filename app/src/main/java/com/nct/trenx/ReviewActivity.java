package com.nct.trenx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnFinish = findViewById(R.id.btnFinishReview);

        btnFinish.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            if (rating > 0) {
                String message = getString(R.string.rating_thanks, rating);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }

            // Chuyển về trang chủ (MainActivity)
            Intent intent = new Intent(ReviewActivity.this, MainActivity.class);

            // Xóa sạch lịch sử để không quay lại màn hình Timer được
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(intent);
            finish();
        });
    }
}