package com.nct.trenx.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Service gửi Email HTML chứa mã OTP 6 chữ số qua Gmail SMTP Port 587 (STARTTLS).
 */
public class EmailService {

    private static final String TAG = "EmailService";
    
    // Cấu hình Email gửi SMTP chuyên nghiệp của Trenx System
    private static final String SENDER_EMAIL = "trenx.fitness.app@gmail.com"; 
    private static final String SENDER_PASSWORD = "vkgtqyqjzxdtcskh"; // App Password 16 ký tự

    public interface EmailCallback {
        void onSuccess();
        void onFailure(String errorMsg);
    }

    /**
     * Gửi Email HTML chứa mã OTP 6 chữ số qua Gmail SMTP Port 587 (STARTTLS).
     */
    public static void sendOtpEmail(final String recipientEmail, final String otpCode, final EmailCallback callback) {
        new Thread(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.starttls.required", "true");
                props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                    }
                });

                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(SENDER_EMAIL, "Trenx Fitness App"));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                mimeMessage.setSubject("[Trenx] Mã OTP Reset Mật Khẩu 6 Chữ Số: " + otpCode);

                String htmlContent = "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<style>"
                        + "  body { font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; background-color: #121212; color: #ffffff; margin: 0; padding: 0; }"
                        + "  .container { max-width: 520px; margin: 30px auto; background-color: #1e1e24; border-radius: 16px; overflow: hidden; box-shadow: 0 10px 30px rgba(0,0,0,0.5); }"
                        + "  .header { background: #111111; padding: 32px 24px; text-align: center; border-bottom: 2px solid #333333; }"
                        + "  .brand-title { font-size: 28px; font-weight: 800; color: #ffffff; letter-spacing: 3px; margin: 0; }"
                        + "  .subtitle { color: #aaaaaa; font-size: 13px; margin-top: 6px; letter-spacing: 1px; }"
                        + "  .content { padding: 36px 28px; text-align: center; }"
                        + "  .headline { font-size: 22px; font-weight: 700; color: #ffffff; margin-bottom: 14px; }"
                        + "  .description { font-size: 15px; color: #cccccc; line-height: 1.6; margin-bottom: 28px; }"
                        + "  .otp-box { background-color: #000000; border: 2px solid #FFD700; border-radius: 12px; padding: 22px 10px; margin: 0 auto 28px auto; width: 85%; }"
                        + "  .otp-code { font-size: 42px; font-weight: 900; letter-spacing: 14px; color: #FFD700; text-align: center; margin: 0; }"
                        + "  .expiry-note { font-size: 13px; color: #FF5252; font-weight: 600; margin-bottom: 20px; }"
                        + "  .footer { background-color: #15151a; padding: 20px; text-align: center; font-size: 12px; color: #777777; border-top: 1px solid #282830; }"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        + "  <div class=\"container\">"
                        + "    <div class=\"header\">"
                        + "      <h1 class=\"brand-title\">T R E N X</h1>"
                        + "      <div class=\"subtitle\">FITNESS &amp; CALISTHENICS</div>"
                        + "    </div>"
                        + "    <div class=\"content\">"
                        + "      <div class=\"headline\">Mã Xác Thực Reset Mật Khẩu</div>"
                        + "      <div class=\"description\">Bạn vừa gửi yêu cầu khôi phục mật khẩu. Hãy nhập mã OTP 6 chữ số dưới đây vào ứng dụng Trenx để hoàn tất đặt mật khẩu mới:</div>"
                        + "      <div class=\"otp-box\">"
                        + "        <div class=\"otp-code\">" + otpCode + "</div>"
                        + "      </div>"
                        + "      <div class=\"expiry-note\">⏱️ Mã OTP này có hiệu lực trong 10 phút.</div>"
                        + "    </div>"
                        + "    <div class=\"footer\">"
                        + "      Nếu bạn không yêu cầu đổi mật khẩu, vui lòng bỏ qua email này.<br>&copy; 2026 Trenx App. All rights reserved."
                        + "    </div>"
                        + "  </div>"
                        + "</body>"
                        + "</html>";

                mimeMessage.setContent(htmlContent, "text/html; charset=utf-8");

                Transport.send(mimeMessage);

                Log.d(TAG, "Gửi Email OTP 6 chữ số thành công đến: " + recipientEmail);

                new Handler(Looper.getMainLooper()).post(() -> {
                    if (callback != null) callback.onSuccess();
                });

            } catch (Exception e) {
                Log.e(TAG, "Lỗi gửi Email OTP: " + e.getMessage(), e);
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (callback != null) callback.onFailure("Không thể gửi email OTP: " + e.getMessage());
                });
            }
        }).start();
    }
}
