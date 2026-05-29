package com.nct.trenx.model;

public class FeedPost {
    private String id;
    private String userName;
    private String statusFeeling;
    private String timeAgo;
    
    // Using string for image resources or URLs. In this demo, we can use drawable resource names 
    // or just pass an int for simplicity, but let's use an int for local demo.
    private int userAvatarResId;
    private int mainImageResId;
    
    private String activityTitle;
    private String progress;
    private String timeStr;
    private String level;
    
    private int likesCount;
    private int commentsCount;
    private String caption;
    private boolean isLiked;

    public FeedPost(String id, String userName, int userAvatarResId, String statusFeeling, String timeAgo, 
                    int mainImageResId, String activityTitle, String progress, String timeStr, 
                    String level, int likesCount, int commentsCount, String caption) {
        this.id = id;
        this.userName = userName;
        this.userAvatarResId = userAvatarResId;
        this.statusFeeling = statusFeeling;
        this.timeAgo = timeAgo;
        this.mainImageResId = mainImageResId;
        this.activityTitle = activityTitle;
        this.progress = progress;
        this.timeStr = timeStr;
        this.level = level;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.caption = caption;
        this.isLiked = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getUserName() { return userName; }
    public int getUserAvatarResId() { return userAvatarResId; }
    public String getStatusFeeling() { return statusFeeling; }
    public String getTimeAgo() { return timeAgo; }
    public int getMainImageResId() { return mainImageResId; }
    public String getActivityTitle() { return activityTitle; }
    public String getProgress() { return progress; }
    public String getTimeStr() { return timeStr; }
    public String getLevel() { return level; }
    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }
    public int getCommentsCount() { return commentsCount; }
    public String getCaption() { return caption; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
}
