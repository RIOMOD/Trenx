package com.nct.trenx;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Nhớ import thư viện Glide
import com.bumptech.glide.Glide;
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

        // --- LOGIC HIỂN THỊ HÌNH ẢNH MỚI BẰNG GLIDE ---
        // Sử dụng Glide để load ảnh từ Link URL (hiện đang lưu trong ex.getImageName())
        Glide.with(holder.itemView.getContext())
                .load(ex.getImageName())
                .centerCrop()
                .placeholder(R.color.card_dark) // Màu nền hiển thị tạm trong lúc chờ tải ảnh Internet
                .into(holder.ivThumb);

        // Bấm vào bài tập để mở màn hình tập luyện (Timer)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TrainingActivity.class);
            // Cập nhật lại Intent để truyền đúng chuẩn DAY và DIFFICULTY cho luồng mới
            intent.putExtra("DAY", ex.getDay());
            intent.putExtra("DIFFICULTY", ex.getDifficulty());
            intent.putExtra("POSITION", position); // Truyền vị trí để TrainingActivity biết bắt đầu từ bài nào
            v.getContext().startActivity(intent);
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
            tvName = itemView.findViewById(R.id.tvItemName);
            tvReps = itemView.findViewById(R.id.tvItemReps);
            ivThumb = itemView.findViewById(R.id.ivExerciseThumb);
        }
    }
}