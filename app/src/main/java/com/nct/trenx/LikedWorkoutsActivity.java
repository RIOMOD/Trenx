package com.nct.trenx;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LikedWorkoutsActivity extends AppCompatActivity {

    private RecyclerView rvLikedWorkouts;
    private LikedWorkoutAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<Exercise> likedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_workouts);

        // 1. Nút Back để quay về Dashboard
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. Khởi tạo Database và lấy danh sách bài tập đã Like
        dbHelper = new DatabaseHelper(this);
        likedList = dbHelper.getLikedExercises(); // Hàm này mình đã thêm vào DatabaseHelper ver 11

        // 3. Thiết lập RecyclerView
        rvLikedWorkouts = findViewById(R.id.rvLikedWorkouts);

        // Dùng LinearLayoutManager để các thẻ xếp chồng lên nhau theo chiều dọc
        rvLikedWorkouts.setLayoutManager(new LinearLayoutManager(this));

        // 4. Khởi tạo Adapter và gán vào RecyclerView
        // Lưu ý: Đảm bảo bạn đã tạo file LikedWorkoutAdapter.java trước đó nhé
        adapter = new LikedWorkoutAdapter(likedList);
        rvLikedWorkouts.setAdapter(adapter);
    }

    // Mẹo: Nếu bạn muốn danh sách tự cập nhật mỗi khi quay lại trang này
    @Override
    protected void onResume() {
        super.onResume();
        if (dbHelper != null && adapter != null) {
            likedList.clear();
            likedList.addAll(dbHelper.getLikedExercises());
            adapter.notifyDataSetChanged();
        }
    }
}