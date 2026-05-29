package com.nct.trenx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.model.Exercise;
import com.nct.trenx.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class LikedWorkoutAdapter extends RecyclerView.Adapter<LikedWorkoutAdapter.ViewHolder> {
    private final List<Exercise> likedList = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise, int position);
    }

    public LikedWorkoutAdapter(List<Exercise> likedList) {
        updateData(likedList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updateData(List<Exercise> newList) {
        likedList.clear();
        if (newList != null) {
            likedList.addAll(newList);
        }
        notifyDataSetChanged();
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

        ImageUtils.loadExerciseThumb(holder.itemView.getContext(), holder.ivThumb, ex.getImageName());
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(ex, position);
            }
        });
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
            ivThumb = itemView.findViewById(R.id.iv_liked_thumb);
            tvTitle = itemView.findViewById(R.id.tv_liked_title);
            tvCategory = itemView.findViewById(R.id.tv_liked_category);
        }
    }
}
