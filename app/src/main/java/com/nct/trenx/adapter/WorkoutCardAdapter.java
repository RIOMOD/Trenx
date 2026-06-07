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

import java.util.List;

public class WorkoutCardAdapter extends RecyclerView.Adapter<WorkoutCardAdapter.ViewHolder> {

    private List<Exercise> workoutList;

    public WorkoutCardAdapter(List<Exercise> workoutList) {
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise workout = workoutList.get(position);
        holder.tvTitle.setText(workout.getName());

        // Sử dụng ImageUtils để load ảnh từ URL hoặc tên drawable
        String imgPath = workout.getImageName();
        if (imgPath != null && imgPath.startsWith("http")) {
            ImageUtils.loadExerciseThumb(holder.itemView.getContext(), holder.ivThumb, imgPath);
        } else {
            // Nếu là tên drawable (ví dụ: "feed_workout_1")
            int resId = holder.itemView.getContext().getResources().getIdentifier(
                    imgPath, "drawable", holder.itemView.getContext().getPackageName());
            if (resId != 0) {
                holder.ivThumb.setImageResource(resId);
            } else {
                holder.ivThumb.setImageResource(R.drawable.feed_workout_1); // Mặc định
            }
        }
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb, ivLock;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivThumb);
            ivLock = itemView.findViewById(R.id.ivLock);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
