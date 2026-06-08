package com.nct.trenx.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;

import java.util.List;

public class FitnessTestAdapter extends RecyclerView.Adapter<FitnessTestAdapter.ViewHolder> {

    private final List<FitnessTestItem> items;
    private final OnTestCompleteListener listener;

    public interface OnTestCompleteListener {
        void onComplete(int position);
    }

    public FitnessTestAdapter(List<FitnessTestItem> items, OnTestCompleteListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitness_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FitnessTestItem item = items.get(position);
        holder.tvTitle.setText(item.title);
        holder.tvDescription.setText(item.description);

        if (item.videoUri != null) {
            holder.videoView.setVideoURI(item.videoUri);
            holder.videoView.setOnPreparedListener(mp -> {
                mp.setLooping(true);
                // Adjust video scaling to fill screen
                float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                float screenRatio = holder.videoView.getWidth() / (float) holder.videoView.getHeight();
                float scale = videoRatio / screenRatio;
                if (scale >= 1f) {
                    holder.videoView.setScaleX(scale);
                } else {
                    holder.videoView.setScaleY(1f / scale);
                }
                holder.videoView.start();
            });
        }

        holder.btnComplete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onComplete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView tvTitle, tvDescription;
        View btnComplete;

        ViewHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view_test);
            tvTitle = itemView.findViewById(R.id.tv_test_title);
            tvDescription = itemView.findViewById(R.id.tv_test_description);
            btnComplete = itemView.findViewById(R.id.btn_complete_exercise);
        }
    }

    public static class FitnessTestItem {
        String title;
        String description;
        Uri videoUri;

        public FitnessTestItem(String title, String description, Uri videoUri) {
            this.title = title;
            this.description = description;
            this.videoUri = videoUri;
        }
    }
}
