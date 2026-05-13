package com.nct.trenx;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private List<Exercise> exerciseList;

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
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

        // --- LOGIC HIỂN THỊ HÌNH ẢNH ---
        // Tìm ID của ảnh trong thư mục drawable dựa trên tên imageName lưu ở SQLite
        int imageId = holder.itemView.getContext().getResources().getIdentifier(
                ex.getImageName(), "drawable", holder.itemView.getContext().getPackageName());

        if (imageId != 0) {
            holder.ivThumb.setImageResource(imageId);
        } else {
            // Nếu tên file trong database không khớp với ảnh nào, hiện logo mặc định
            holder.ivThumb.setImageResource(R.mipmap.ic_launcher);
        }

        // Bấm vào bài tập để mở màn hình tập luyện (Timer)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TimerActivity.class);
            intent.putExtra("POSITION", position);
            intent.putExtra("CATEGORY", ex.getCategory());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return exerciseList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvReps;
        ImageView ivThumb; // Thêm ImageView vào ViewHolder

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvReps = itemView.findViewById(R.id.tvItemReps);
            ivThumb = itemView.findViewById(R.id.ivExerciseThumb); // Ánh xạ ImageView từ XML
        }
    }
}