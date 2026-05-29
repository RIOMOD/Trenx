package com.nct.trenx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.model.Exercise;
import com.nct.trenx.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private final List<Exercise> exerciseList = new ArrayList<>();
    @Nullable
    private OnExerciseClickListener listener;

    public interface OnExerciseClickListener {
        void onExerciseClick(Exercise exercise, int position);
    }

    public ExerciseAdapter(List<Exercise> exerciseList) {
        updateData(exerciseList);
    }

    public void updateData(List<Exercise> newList) {
        exerciseList.clear();
        if (newList != null) {
            exerciseList.addAll(newList);
        }
        notifyDataSetChanged();
    }

    public void setOnExerciseClickListener(@Nullable OnExerciseClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.ViewHolder holder, int position) {
        Exercise ex = exerciseList.get(position);
        holder.tvName.setText(ex.getName());
        holder.tvReps.setText(ex.getReps());

        ImageUtils.loadExerciseThumb(holder.itemView.getContext(), holder.ivThumb, ex.getImageName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onExerciseClick(ex, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvReps;
        ImageView ivThumb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_exercise_name);
            tvReps = itemView.findViewById(R.id.tv_exercise_reps);
            ivThumb = itemView.findViewById(R.id.iv_thumb);
        }
    }
}
