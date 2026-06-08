package com.nct.trenx.model;

public class NotificationItem {
    private String title;
    private String message;
    private String time;
    private boolean isRead;

    public NotificationItem(String title, String message, String time, boolean isRead) {
        this.title = title;
        this.message = message;
        this.time = time;
        this.isRead = isRead;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getTime() { return time; }
    public boolean isRead() { return isRead; }
}
