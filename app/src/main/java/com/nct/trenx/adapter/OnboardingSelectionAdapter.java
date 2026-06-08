package com.nct.trenx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nct.trenx.R;
import java.util.ArrayList;
import java.util.List;

public class OnboardingSelectionAdapter extends RecyclerView.Adapter<OnboardingSelectionAdapter.ViewHolder> {

    private final List<String> items;
    private final List<String> subtitles;
    private final List<Integer> selectedPositions = new ArrayList<>();
    private final boolean multiSelect;
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        void onItemSelected(List<Integer> positions);
    }

    public OnboardingSelectionAdapter(List<String> items, List<String> subtitles, boolean multiSelect) {
        this.items = items;
        this.subtitles = subtitles;
        this.multiSelect = multiSelect;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public void setSelectedPositions(List<Integer> positions) {
        this.selectedPositions.clear();
        this.selectedPositions.addAll(positions);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onboarding_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = items.get(position);
        String subtitle = (subtitles != null && position < subtitles.size()) ? subtitles.get(position) : null;

        holder.tvTitle.setText(title);
        if (subtitle != null && !subtitle.isEmpty()) {
            holder.tvSubtitle.setText(subtitle);
            holder.tvSubtitle.setVisibility(View.VISIBLE);
        } else {
            holder.tvSubtitle.setVisibility(View.GONE);
        }

        boolean isSelected = selectedPositions.contains(position);
        holder.itemView.setSelected(isSelected);
        holder.viewIndicator.setVisibility(isSelected ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (multiSelect) {
                if (selectedPositions.contains(position)) {
                    selectedPositions.remove(Integer.valueOf(position));
                } else {
                    selectedPositions.add(position);
                }
            } else {
                selectedPositions.clear();
                selectedPositions.add(position);
            }
            notifyDataSetChanged();
            if (listener != null) {
                listener.onItemSelected(selectedPositions);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubtitle;
        View viewIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvSubtitle = itemView.findViewById(R.id.tv_item_subtitle);
            viewIndicator = itemView.findViewById(R.id.view_selected_indicator);
        }
    }
}
