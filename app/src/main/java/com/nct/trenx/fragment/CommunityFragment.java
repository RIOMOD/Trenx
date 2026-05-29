package com.nct.trenx.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.adapter.FeedAdapter;
import com.nct.trenx.utils.CommunityDataProvider;

public class CommunityFragment extends Fragment {

    private TextView tvTabCommunity, tvTabPersonal;
    private RecyclerView rvCommunityFeed;
    private FeedAdapter feedAdapter;

    private boolean isCommunityTabActive = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        tvTabCommunity = view.findViewById(R.id.tvTabCommunity);
        tvTabPersonal = view.findViewById(R.id.tvTabPersonal);
        rvCommunityFeed = view.findViewById(R.id.rvCommunityFeed);

        rvCommunityFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        feedAdapter = new FeedAdapter(CommunityDataProvider.getCommunityFeed());
        rvCommunityFeed.setAdapter(feedAdapter);

        tvTabCommunity.setOnClickListener(v -> {
            if (!isCommunityTabActive) {
                isCommunityTabActive = true;
                updateTabUI();
                feedAdapter.updateData(CommunityDataProvider.getCommunityFeed());
                rvCommunityFeed.scrollToPosition(0);
            }
        });

        tvTabPersonal.setOnClickListener(v -> {
            if (isCommunityTabActive) {
                isCommunityTabActive = false;
                updateTabUI();
                feedAdapter.updateData(CommunityDataProvider.getPersonalFeed());
                rvCommunityFeed.scrollToPosition(0);
            }
        });

        return view;
    }

    private void updateTabUI() {
        if (isCommunityTabActive) {
            tvTabCommunity.setBackgroundResource(R.drawable.bg_tab_active);
            tvTabCommunity.setTextColor(Color.parseColor("#000000"));
            
            tvTabPersonal.setBackgroundResource(android.R.color.transparent);
            tvTabPersonal.setTextColor(Color.parseColor("#757575"));
        } else {
            tvTabPersonal.setBackgroundResource(R.drawable.bg_tab_active);
            tvTabPersonal.setTextColor(Color.parseColor("#000000"));
            
            tvTabCommunity.setBackgroundResource(android.R.color.transparent);
            tvTabCommunity.setTextColor(Color.parseColor("#757575"));
        }
    }
}
