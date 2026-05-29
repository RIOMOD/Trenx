package com.nct.trenx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.model.FeedPost;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private List<FeedPost> postList;

    public FeedAdapter(List<FeedPost> postList) {
        this.postList = postList;
    }

    public void updateData(List<FeedPost> newPostList) {
        this.postList = newPostList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_post, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        FeedPost post = postList.get(position);

        holder.ivAvatar.setImageResource(post.getUserAvatarResId());
        holder.tvUserName.setText(post.getUserName());
        holder.tvStatusFeeling.setText(post.getStatusFeeling());
        holder.tvTimeAgo.setText(post.getTimeAgo());

        holder.ivMainImage.setImageResource(post.getMainImageResId());
        holder.tvActivityTitle.setText(post.getActivityTitle());
        holder.tvProgress.setText(post.getProgress());
        holder.tvTimeStr.setText(post.getTimeStr());
        holder.tvLevel.setText(post.getLevel());

        holder.tvLikesCount.setText(post.getLikesCount() + " Likes");
        holder.tvCommentsCount.setText(post.getCommentsCount() + " Comments");
        holder.tvCaption.setText(post.getCaption());

        // Update like icon state
        if (post.isLiked()) {
            holder.ivLikeIcon.setImageResource(R.drawable.ic_heart_filled);
        } else {
            holder.ivLikeIcon.setImageResource(R.drawable.ic_heart);
        }

        // Like click event
        holder.btnLike.setOnClickListener(v -> {
            boolean isCurrentlyLiked = post.isLiked();
            post.setLiked(!isCurrentlyLiked);
            int newCount = isCurrentlyLiked ? post.getLikesCount() - 1 : post.getLikesCount() + 1;
            post.setLikesCount(newCount);
            
            // Rebind just this item
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar, ivMore, ivMainImage, ivLikeIcon;
        TextView tvUserName, tvStatusFeeling, tvTimeAgo, tvActivityTitle, tvProgress, tvTimeStr, tvLevel, tvLikesCount, tvCommentsCount, tvCaption;
        LinearLayout btnLike, btnComment;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvStatusFeeling = itemView.findViewById(R.id.tvStatusFeeling);
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            ivMore = itemView.findViewById(R.id.ivMore);
            
            ivMainImage = itemView.findViewById(R.id.ivMainImage);
            tvActivityTitle = itemView.findViewById(R.id.tvActivityTitle);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            tvTimeStr = itemView.findViewById(R.id.tvTimeStr);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            
            btnLike = itemView.findViewById(R.id.btnLike);
            ivLikeIcon = itemView.findViewById(R.id.ivLikeIcon);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            
            btnComment = itemView.findViewById(R.id.btnComment);
            tvCommentsCount = itemView.findViewById(R.id.tvCommentsCount);
            
            tvCaption = itemView.findViewById(R.id.tvCaption);
        }
    }
}
