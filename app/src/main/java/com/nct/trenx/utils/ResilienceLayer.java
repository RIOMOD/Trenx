package com.nct.trenx.utils;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.regex.Pattern;

/**
 * Resilience Layer (Tầng kiên cường/phục hồi) cho ứng dụng Trenx.
 * Cung cấp các cơ chế phòng vệ chống lại dữ liệu Fuzzing, SQL Injection, XSS,
 * tránh tràn số và đảm bảo ứng dụng không bao giờ bị crash khi gặp ngoại lệ.
 */
public final class ResilienceLayer {

    private static final String TAG = "ResilienceLayer";

    // Hạn chế độ dài tối đa cho input đầu vào tránh tràn bộ nhớ (Memory Overflow)
    private static final int MAX_INPUT_LENGTH = 1000;
    private static final int MAX_EMAIL_LENGTH = 100;
    private static final int MAX_PASSWORD_LENGTH = 64;

    // Regex kiểm tra định dạng email tiêu chuẩn
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    // Regex lọc các ký tự nguy hiểm cho SQL Injection / HTML Injection
    private static final Pattern DANGEROUS_SQL_PATTERN = Pattern.compile(
            "(?i)(UNION|SELECT|INSERT|UPDATE|DELETE|DROP|ALTER|WHERE|OR\\s+1=1|--|#|/\\*|\\*/)"
    );

    private ResilienceLayer() {
        // Private constructor để tránh khởi tạo instance
    }

    /**
     * Helper log an toàn khi chạy Unit Test trên local JVM (tránh lỗi Class Not Mocked của Android Log)
     */
    private static void logError(String tag, String msg, Throwable tr) {
        try {
            Log.e(tag, msg, tr);
        } catch (RuntimeException e) {
            System.err.println("[" + tag + "] " + msg + ": " + (tr != null ? tr.getMessage() : ""));
        }
    }

    private static void logError(String tag, String msg) {
        try {
            Log.e(tag, msg);
        } catch (RuntimeException e) {
            System.err.println("[" + tag + "] " + msg);
        }
    }

    /**
     * Làm sạch chuỗi đầu vào (Sanitize) tránh các ký tự đặc biệt nguy hiểm hoặc chuỗi quá dài.
     */
    public static String sanitizeString(String input, int maxLength) {
        if (input == null) {
            return "";
        }
        // Loại bỏ các thẻ HTML để chống XSS trước khi thực hiện cắt chuỗi
        String sanitized = input.replaceAll("<[^>]*>", "").trim();
        if (sanitized.length() > maxLength) {
            sanitized = sanitized.substring(0, maxLength);
        }
        return sanitized;
    }

    public static String sanitizeString(String input) {
        return sanitizeString(input, MAX_INPUT_LENGTH);
    }

    /**
     * Làm sạch câu truy vấn tìm kiếm, loại bỏ ký tự đại diện SQL wildcard (%) và các chuỗi SQL độc hại.
     */
    public static String sanitizeSearchQuery(String query) {
        if (query == null) {
            return "";
        }
        String sanitized = sanitizeString(query, 100);
        // Loại bỏ các ký tự đại diện wildcard của SQL để tránh làm chậm database
        sanitized = sanitized.replace("%", "").replace("_", "").replace("[", "").replace("]", "");
        // Loại bỏ các từ khóa SQL Injection phổ biến
        sanitized = DANGEROUS_SQL_PATTERN.matcher(sanitized).replaceAll("");
        return sanitized.trim();
    }

    /**
     * Kiểm tra tính hợp lệ của Email.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.length() > MAX_EMAIL_LENGTH) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Kiểm tra tính hợp lệ của Password.
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6 || password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }
        return true;
    }

    /**
     * Ép kiểu Integer an toàn, tránh lỗi NumberFormatException và kiểm tra biên (Boundary Checks)
     */
    public static int safeParseInt(String value, int min, int max, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < min) return min;
            if (parsed > max) return max;
            return parsed;
        } catch (NumberFormatException e) {
            logError(TAG, "Lỗi định dạng số khi parse: " + value, e);
            return defaultValue;
        }
    }

    /**
     * Trích xuất Intent String Extra an toàn, chống Fuzzing dữ liệu Intent.
     */
    public static String getSafeStringExtra(Intent intent, String key, String defaultValue, int maxLength) {
        if (intent == null || key == null) {
            return defaultValue;
        }
        try {
            String value = intent.getStringExtra(key);
            if (value == null) {
                return defaultValue;
            }
            return sanitizeString(value, maxLength);
        } catch (Exception e) {
            logError(TAG, "Lỗi khi đọc string extra từ intent cho key: " + key, e);
            return defaultValue;
        }
    }

    public static String getSafeStringExtra(Intent intent, String key, String defaultValue) {
        return getSafeStringExtra(intent, key, defaultValue, MAX_INPUT_LENGTH);
    }

    /**
     * Trích xuất Intent Int Extra an toàn.
     */
    public static int getSafeIntExtra(Intent intent, String key, int defaultValue) {
        if (intent == null || key == null) {
            return defaultValue;
        }
        try {
            return intent.getIntExtra(key, defaultValue);
        } catch (Exception e) {
            logError(TAG, "Lỗi khi đọc int extra từ intent cho key: " + key, e);
            return defaultValue;
        }
    }

    /**
     * Giao diện chức năng phục vụ chạy câu truy vấn SQL an toàn
     */
    public interface DatabaseAction<T> {
        T execute(SQLiteDatabase db) throws Exception;
    }

    /**
     * Thực thi truy vấn SQLite an toàn, tự động bắt tất cả các ngoại lệ và ghi log, không làm crash app.
     */
    public static <T> T executeSafeDbCall(SQLiteDatabase db, T defaultValue, DatabaseAction<T> action, String errorMsg) {
        if (db == null || action == null) {
            return defaultValue;
        }
        try {
            return action.execute(db);
        } catch (Exception e) {
            logError(TAG, errorMsg != null ? errorMsg : "Lỗi xảy ra khi truy vấn cơ sở dữ liệu", e);
            return defaultValue;
        }
    }

    /**
     * Đóng con trỏ cursor an toàn
     */
    public static void safeCloseCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            try {
                cursor.close();
            } catch (Exception e) {
                logError(TAG, "Lỗi khi đóng cursor", e);
            }
        }
    }
}
