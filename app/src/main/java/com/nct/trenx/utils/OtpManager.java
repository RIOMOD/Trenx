package com.nct.trenx.utils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Quản lý sinh mã OTP 6 chữ số và xác thực thời hạn 10 phút.
 */
public class OtpManager {

    private static final long OTP_VALID_DURATION_MS = 10 * 60 * 1000; // 10 phút
    private static final Map<String, OtpData> otpStore = new HashMap<>();

    private static class OtpData {
        String code;
        long timestamp;

        OtpData(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
    }

    /**
     * Sinh ngẫu nhiên mã OTP 6 chữ số (ví dụ: 849201).
     */
    public static synchronized String generateOtp(String email) {
        if (email == null) return null;
        String cleanEmail = email.trim().toLowerCase();

        SecureRandom random = new SecureRandom();
        int number = 100000 + random.nextInt(900000);
        String code = String.valueOf(number);

        otpStore.put(cleanEmail, new OtpData(code, System.currentTimeMillis()));
        return code;
    }

    /**
     * Kiểm tra mã OTP 6 chữ số người dùng nhập vào.
     */
    public static synchronized boolean verifyOtp(String email, String enteredCode) {
        if (email == null || enteredCode == null) return false;
        String cleanEmail = email.trim().toLowerCase();
        String cleanCode = enteredCode.trim();

        OtpData data = otpStore.get(cleanEmail);
        if (data == null) {
            return false;
        }

        // Kiểm tra thời hạn 10 phút
        if (System.currentTimeMillis() - data.timestamp > OTP_VALID_DURATION_MS) {
            otpStore.remove(cleanEmail); // Hết hạn
            return false;
        }

        boolean isValid = data.code.equals(cleanCode);
        if (isValid) {
            otpStore.remove(cleanEmail); // Dùng xong xóa ngay để bảo mật
        }
        return isValid;
    }
}
