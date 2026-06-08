package com.nct.trenx.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.adapter.NotificationAdapter;
import com.nct.trenx.model.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends BaseActivity {

    private RecyclerView rvNotifications;
    private LinearLayout layoutEmptyNotifications;
    private List<NotificationItem> notificationList;
    private NotificationAdapter adapter;
    private View btnMarkAllRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ImageView ivBack = findViewById(R.id.iv_back);
        TextView tvTitle = findViewById(R.id.tv_toolbar_title);
        rvNotifications = findViewById(R.id.rv_notifications);
        layoutEmptyNotifications = findViewById(R.id.layout_empty_notifications);
        btnMarkAllRead = findViewById(R.id.btn_mark_all_read);

        if (tvTitle != null) {
            tvTitle.setText(R.string.notifications_title);
        }

        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        setupNotificationsList();

        if (btnMarkAllRead != null) {
            btnMarkAllRead.setOnClickListener(v -> markAllAsRead());
        }
    }

    private void setupNotificationsList() {
        notificationList = new ArrayList<>();
        // Dữ liệu mẫu (Dummy data)
        notificationList.add(new NotificationItem(getString(R.string.daily_workout), "Đã đến lúc tập bụng rồi, cố lên!", "2 giờ trước", false));
        notificationList.add(new NotificationItem("Cập nhật mới", "Phiên bản 1.0.3 đã sẵn sàng với nhiều bài tập mới.", "1 ngày trước", true));
        notificationList.add(new NotificationItem("Thành tích mới", "Chúc mừng! Bạn đã hoàn thành chuỗi 7 ngày tập luyện.", "3 ngày trước", true));
        notificationList.add(new NotificationItem("Nhắc nhở", "Đừng quên uống đủ nước sau buổi tập nhé.", "4 ngày trước", true));

        if (notificationList.isEmpty()) {
            showEmptyState();
        } else {
            rvNotifications.setVisibility(View.VISIBLE);
            layoutEmptyNotifications.setVisibility(View.GONE);
            if (btnMarkAllRead != null) btnMarkAllRead.setVisibility(View.VISIBLE);

            rvNotifications.setLayoutManager(new LinearLayoutManager(this));
            adapter = new NotificationAdapter(notificationList);
            rvNotifications.setAdapter(adapter);
        }
    }

    private void markAllAsRead() {
        if (notificationList == null || notificationList.isEmpty()) return;

        boolean updated = false;
        for (int i = 0; i < notificationList.size(); i++) {
            NotificationItem item = notificationList.get(i);
            if (!item.isRead()) {
                // Trong thực tế sẽ cập nhật DB. Ở đây ta tạo object mới để demo.
                notificationList.set(i, new NotificationItem(item.getTitle(), item.getMessage(), item.getTime(), true));
                updated = true;
            }
        }

        if (updated) {
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Tất cả đã được đánh dấu là đã đọc", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEmptyState() {
        rvNotifications.setVisibility(View.GONE);
        layoutEmptyNotifications.setVisibility(View.VISIBLE);
        if (btnMarkAllRead != null) btnMarkAllRead.setVisibility(View.GONE);
    }
}
