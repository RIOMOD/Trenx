package com.nct.trenx;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class MyProgressFragment extends Fragment {

    public MyProgressFragment() {
        // Constructor rỗng
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        FrameLayout btnDay13 = view.findViewById(R.id.btnDay13);
        LinearLayout layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        LinearLayout layoutHistoryCard = view.findViewById(R.id.layoutHistoryCard);
        CardView cardWorkoutHistory = view.findViewById(R.id.cardWorkoutHistory);
        TextView tvDateTitle = view.findViewById(R.id.tvDateTitle);

        if (btnDay13 != null) {
            btnDay13.setOnClickListener(v -> {
                layoutEmptyState.setVisibility(View.GONE);
                layoutHistoryCard.setVisibility(View.VISIBLE);
                if (tvDateTitle != null) {
                    tvDateTitle.setText("Wednesday, 13 May");
                }
            });
        }

        // ================= TRẠM KIỂM SOÁT SỐ 2 =================
        if (cardWorkoutHistory != null) {
            cardWorkoutHistory.setOnClickListener(v -> {
                // Sếp check kỹ xem có phải file Java tên là TrainingActivity.class không nha,
                // hay là ExerciseActivity.class hoặc DetailActivity.class?
                // Thấy tên nào đúng thì sếp sửa chữ "TrainingActivity" dưới đây thành tên đó là hết văng liền!
                Intent intent = new Intent(requireActivity(), TrainingActivity.class);
                startActivity(intent);
            });
        }

        return view;
    }
}