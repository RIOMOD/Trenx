package com.nct.trenx.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nct.trenx.model.User;
import com.nct.trenx.model.WorkoutHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository xử lý các kết nối và đồng bộ dữ liệu Online thông qua Google Firebase.
 * Bao gồm Firebase Authentication và Cloud Firestore Database.
 */
public class FirebaseRepository {

    private final FirebaseAuth mAuth;
    private final FirebaseFirestore mDb;
    private final com.google.firebase.storage.FirebaseStorage mStorage;

    public FirebaseRepository() {
        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();
        mStorage = com.google.firebase.storage.FirebaseStorage.getInstance();
    }

    public interface AuthCallback {
        void onSuccess(User user);
        void onFailure(String errorMsg);
    }

    public interface SimpleCallback {
        void onSuccess();
        void onFailure(String errorMsg);
    }

    public interface UploadCallback {
        void onSuccess(String downloadUrl);
        void onFailure(String errorMsg);
    }

    public interface HistoryCallback {
        void onSuccess(List<WorkoutHistory> historyList);
        void onFailure(String errorMsg);
    }

    public interface LikesCallback {
        void onSuccess(List<String> likedExercises);
        void onFailure(String errorMsg);
    }

    public interface CheckLikeCallback {
        void onResult(boolean isLiked);
    }

    /**
     * Đăng ký tài khoản User mới lên hệ thống Firebase Auth và lưu Profile vào Cloud Firestore.
     */
    public void registerUser(User user, AuthCallback callback) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            callback.onFailure("Dữ liệu đăng ký không đầy đủ.");
            return;
        }

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();
                        // Lưu thông tin cá nhân vào Firestore
                        Map<String, Object> userProfile = new HashMap<>();
                        userProfile.put("uid", uid);
                        userProfile.put("username", user.getUsername());
                        userProfile.put("fullName", user.getFullName());
                        userProfile.put("email", user.getEmail());
                        userProfile.put("goals", user.getGoals());
                        userProfile.put("gender", user.getGender());
                        userProfile.put("fitnessLevel", user.getFitnessLevel());
                        userProfile.put("height", user.getHeight());
                        userProfile.put("weight", user.getWeight());
                        userProfile.put("weightGoal", user.getWeightGoal());
                        userProfile.put("maxPushups", user.getMaxPushups());
                        userProfile.put("maxPullups", user.getMaxPullups());
                        userProfile.put("maxDips", user.getMaxDips());
                        userProfile.put("maxSquats", user.getMaxSquats());

                        mDb.collection("users").document(uid).set(userProfile)
                                .addOnSuccessListener(aVoid -> {
                                    user.setId(uid.hashCode()); // Gán tạm hash code làm ID local
                                    callback.onSuccess(user);
                                })
                                .addOnFailureListener(e -> callback.onFailure("Không thể lưu profile: " + e.getMessage()));
                    } else {
                        callback.onFailure("Không lấy được người dùng từ Firebase.");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure("Lỗi đăng ký Firebase: " + e.getMessage()));
    }

    /**
     * Đăng nhập thông qua Firebase Auth và lấy profile từ Cloud Firestore.
     */
    public void login(String email, String password, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();
                        mDb.collection("users").document(uid).get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        User user = mapFirestoreToUser(documentSnapshot);
                                        callback.onSuccess(user);
                                    } else {
                                        callback.onFailure("Không tìm thấy dữ liệu Profile.");
                                    }
                                })
                                .addOnFailureListener(e -> callback.onFailure("Không tìm thấy profile: " + e.getMessage()));
                    } else {
                        callback.onFailure("Lỗi thông tin đăng nhập.");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure("Email hoặc mật khẩu không chính xác: " + e.getMessage()));
    }

    /**
     * Thêm lịch sử tập luyện online.
     */
    public void addWorkoutHistory(WorkoutHistory history, SimpleCallback callback) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            callback.onFailure("Vui lòng đăng nhập trước.");
            return;
        }

        String uid = firebaseUser.getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("date", history.getDate());
        map.put("dayName", history.getDayName());
        map.put("difficulty", history.getDifficulty());
        map.put("progressPercent", history.getProgressPercent());
        map.put("durationSeconds", history.getDurationSeconds());
        map.put("muscleGroups", history.getMuscleGroups());

        mDb.collection("users").document(uid).collection("workout_history")
                .add(map)
                .addOnSuccessListener(documentReference -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    /**
     * Lấy toàn bộ lịch sử tập luyện online của User hiện tại.
     */
    public void getWorkoutHistory(HistoryCallback callback) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            callback.onFailure("Vui lòng đăng nhập.");
            return;
        }

        String uid = firebaseUser.getUid();
        mDb.collection("users").document(uid).collection("workout_history")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<WorkoutHistory> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Long progress = doc.getLong("progressPercent");
                        Long duration = doc.getLong("durationSeconds");
                        list.add(new WorkoutHistory(
                                doc.getString("date"),
                                doc.getString("dayName"),
                                doc.getString("difficulty"),
                                progress != null ? progress.intValue() : 0,
                                5,
                                duration != null ? duration.intValue() : 0,
                                doc.getString("muscleGroups")
                        ));
                    }
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    /**
     * Yêu thích / Bỏ thích một bài tập online.
     */
    public void setExerciseLiked(String exerciseName, boolean liked, SimpleCallback callback) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            if (callback != null) callback.onFailure("Vui lòng đăng nhập.");
            return;
        }

        String uid = firebaseUser.getUid();
        if (liked) {
            Map<String, Object> map = new HashMap<>();
            map.put("liked", true);
            mDb.collection("users").document(uid).collection("likes").document(exerciseName).set(map)
                    .addOnSuccessListener(aVoid -> {
                        if (callback != null) callback.onSuccess();
                    })
                    .addOnFailureListener(e -> {
                        if (callback != null) callback.onFailure(e.getMessage());
                    });
        } else {
            mDb.collection("users").document(uid).collection("likes").document(exerciseName).delete()
                    .addOnSuccessListener(aVoid -> {
                        if (callback != null) callback.onSuccess();
                    })
                    .addOnFailureListener(e -> {
                        if (callback != null) callback.onFailure(e.getMessage());
                    });
        }
    }

    /**
     * Lấy danh sách các bài tập đã yêu thích online.
     */
    public void getLikedExercises(LikesCallback callback) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            callback.onFailure("Vui lòng đăng nhập.");
            return;
        }

        String uid = firebaseUser.getUid();
        mDb.collection("users").document(uid).collection("likes").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        list.add(doc.getId());
                    }
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    /**
     * Kiểm tra trạng thái thích bài tập online.
     */
    public void isExerciseLiked(String exerciseName, CheckLikeCallback callback) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            callback.onResult(false);
            return;
        }

        String uid = firebaseUser.getUid();
        mDb.collection("users").document(uid).collection("likes").document(exerciseName).get()
                .addOnSuccessListener(doc -> callback.onResult(doc.exists()))
                .addOnFailureListener(e -> callback.onResult(false));
    }

    /**
     * Gửi email khôi phục / reset mật khẩu tới Email người dùng qua Firebase Auth.
     */
    public void sendPasswordResetEmail(String email, SimpleCallback callback) {
        if (email == null || email.trim().isEmpty()) {
            if (callback != null) callback.onFailure("Vui lòng nhập Email.");
            return;
        }

        mAuth.sendPasswordResetEmail(email.trim())
                .addOnSuccessListener(aVoid -> {
                    if (callback != null) callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure("Lỗi gửi mail: " + e.getMessage());
                });
    }

    /**
     * Xác thực mã Reset Code (oobCode) từ email và đặt lại mật khẩu mới qua Firebase.
     */
    public void confirmPasswordReset(String codeOrLink, String newPassword, SimpleCallback callback) {
        if (codeOrLink == null || codeOrLink.trim().isEmpty()) {
            if (callback != null) callback.onFailure("Vui lòng nhập Mã xác thực (Reset Code).");
            return;
        }
        if (newPassword == null || newPassword.trim().length() < 6) {
            if (callback != null) callback.onFailure("Mật khẩu mới phải từ 6 ký tự.");
            return;
        }

        String code = codeOrLink.trim();
        // Nếu người dùng dán cả link từ email, tự động tách lấy chuỗi mã oobCode
        if (code.contains("oobCode=")) {
            try {
                int start = code.indexOf("oobCode=") + 8;
                int end = code.indexOf("&", start);
                if (end != -1) {
                    code = code.substring(start, end);
                } else {
                    code = code.substring(start);
                }
            } catch (Exception ignored) {}
        }

        mAuth.confirmPasswordReset(code, newPassword.trim())
                .addOnSuccessListener(aVoid -> {
                    if (callback != null) callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure("Lỗi đặt lại mật khẩu: " + e.getMessage());
                });
    }

    /**
     * Đăng xuất tài khoản Firebase.
     */
    public void logout() {
        mAuth.signOut();
    }

    /**
     * Tải ảnh đại diện (Avatar) lên Firebase Storage và cập nhật URL vào Firestore.
     */
    public void uploadAvatar(android.net.Uri imageUri, UploadCallback callback) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null || imageUri == null) {
            if (callback != null) callback.onFailure("Vui lòng đăng nhập trước.");
            return;
        }

        String uid = firebaseUser.getUid();
        com.google.firebase.storage.StorageReference ref = mStorage.getReference().child("avatars/" + uid + ".jpg");

        ref.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    String url = uri.toString();
                    Map<String, Object> map = new HashMap<>();
                    map.put("avatarUrl", url);
                    mDb.collection("users").document(uid).update(map);
                    if (callback != null) callback.onSuccess(url);
                }))
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e.getMessage());
                });
    }

    /**
     * Tải ảnh hoàn thành buổi tập lên Firebase Storage.
     */
    public void uploadWorkoutPhoto(android.net.Uri imageUri, UploadCallback callback) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null || imageUri == null) {
            if (callback != null) callback.onFailure("Vui lòng đăng nhập trước.");
            return;
        }

        String uid = firebaseUser.getUid();
        String filename = System.currentTimeMillis() + ".jpg";
        com.google.firebase.storage.StorageReference ref = mStorage.getReference().child("workout_photos/" + uid + "/" + filename);

        ref.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    if (callback != null) callback.onSuccess(uri.toString());
                }))
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e.getMessage());
                });
    }

    private User mapFirestoreToUser(DocumentSnapshot doc) {
        User user = new User();
        String email = doc.getString("email");
        user.setEmail(email);
        user.setUsername(doc.getString("username"));
        user.setFullName(doc.getString("fullName"));
        user.setGoals(doc.getString("goals"));
        user.setGender(doc.getString("gender"));
        user.setFitnessLevel(doc.getString("fitnessLevel"));
        
        Long height = doc.getLong("height");
        Long weight = doc.getLong("weight");
        Long weightGoal = doc.getLong("weightGoal");
        
        user.setHeight(height != null ? height.intValue() : 0);
        user.setWeight(weight != null ? weight.intValue() : 0);
        user.setWeightGoal(weightGoal != null ? weightGoal.intValue() : 0);
        
        user.setMaxPushups(doc.getString("maxPushups"));
        user.setMaxPullups(doc.getString("maxPullups"));
        user.setMaxDips(doc.getString("maxDips"));
        user.setMaxSquats(doc.getString("maxSquats"));
        return user;
    }
}
