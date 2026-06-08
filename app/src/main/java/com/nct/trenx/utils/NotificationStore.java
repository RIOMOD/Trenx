package com.nct.trenx.utils;

import com.nct.trenx.model.NotificationItem;
import java.util.ArrayList;
import java.util.List;

public class NotificationStore {
    private static List<NotificationItem> notifications = new ArrayList<>();

    static {
        // Khởi tạo dữ liệu mẫu nếu danh sách trống
        notifications.add(new NotificationItem("Luyện tập hôm nay", "Đã đến lúc tập bụng rồi, cố lên!", "2 giờ trước", false));
        notifications.add(new NotificationItem("Cập nhật mới", "Phiên bản 1.0.3 đã sẵn sàng với nhiều bài tập mới.", "1 ngày trước", false));
        notifications.add(new NotificationItem("Thành tích mới", "Chúc mừng! Bạn đã hoàn thành chuỗi 7 ngày tập luyện.", "3 ngày trước", true));
    }

    public static List<NotificationItem> getNotifications() {
        return notifications;
    }

    public static int getUnreadCount() {
        int count = 0;
        for (NotificationItem item : notifications) {
            if (!item.isRead()) count++;
        }
        return count;
    }

    public static void markAllAsRead() {
        for (int i = 0; i < notifications.size(); i++) {
            NotificationItem old = notifications.get(i);
            notifications.set(i, new NotificationItem(old.getTitle(), old.getMessage(), old.getTime(), true));
        }
    }

    public static void markAsRead(int position) {
        if (position >= 0 && position < notifications.size()) {
            NotificationItem old = notifications.get(position);
            notifications.set(position, new NotificationItem(old.getTitle(), old.getMessage(), old.getTime(), true));
        }
    }
}
