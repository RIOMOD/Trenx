package com.nct.trenx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class LikedWorkoutAdapter extends RecyclerView.Adapter<LikedWorkoutAdapter.ViewHolder> {
    private List<Exercise> likedList;

    public LikedWorkoutAdapter(List<Exercise> likedList) {
        this.likedList = likedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liked_workout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise ex = likedList.get(position);
        holder.tvTitle.setText(ex.getName());
        holder.tvCategory.setText(ex.getCategory());

        Glide.with(holder.itemView.getContext())
                .load(ex.getImageName())
                .centerCrop()
                .into(holder.ivThumb);
    }

    @Override
    public int getItemCount() {
        return likedList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvTitle, tvCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivLikedThumb);
            tvTitle = itemView.findViewById(R.id.tvLikedTitle);
            tvCategory = itemView.findViewById(R.id.tvLikedCategory);
        }
    }
}