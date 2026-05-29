package com.nct.trenx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.nct.trenx.R;
import com.nct.trenx.utils.IntentExtras;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Ánh xạ các View từ activity_review.xml
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button btnFinish = findViewById(R.id.btnFinishReview);

        // Nhận tổng thời gian từ TrainingActivity gửi sang
        String totalTime = getIntent().getStringExtra(IntentExtras.TOTAL_TIME);

        // Bạn có thể thêm một TextView trong activity_review.xml để hiện thời gian này
        // Ví dụ: TextView tvTimeResult = findViewById(R.id.tvTotalTimeResult);
        // if (totalTime != null) tvTimeResult.setText("Thời gian tập: " + totalTime);

        btnFinish.setOnClickListener(v -> {
            float rating = ratingBar.getRating();

            if (rating > 0) {
                // Hiển thị cảm ơn dựa trên số sao đã chấm
                Toast.makeText(this, "Cảm ơn bạn đã đánh giá " + rating + " sao cho buổi tập!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Chúc mừng bạn đã hoàn thành buổi tập!", Toast.LENGTH_SHORT).show();
            }

            // Chuyển về trang chủ (MainActivity)
            Intent intent = new Intent(ReviewActivity.this, MainActivity.class);

            // FLAG_ACTIVITY_CLEAR_TOP: Xóa các Activity trung gian (như TrainingActivity) khỏi Stack
            // FLAG_ACTIVITY_SINGLE_TOP: Nếu MainActivity đang chạy thì không tạo mới mà dùng lại bản cũ
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(intent);

            // Kết thúc ReviewActivity để dọn dẹp bộ nhớ
            finish();
        });
    }
}
