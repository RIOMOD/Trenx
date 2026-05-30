package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.nct.trenx.R;
import com.nct.trenx.utils.IntentExtras;

public class ReviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnFinish = findViewById(R.id.btnFinishReview);

        btnFinish.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            if (rating > 0) {
                String msg = getString(R.string.rating_thanks, rating);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
