package com.nct.trenx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class CommunityFragment extends Fragment {

    public CommunityFragment() {
        // Constructor rỗng
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // CHÚ Ý DÒNG NÀY: Phải gọi đúng chữ R.layout.fragment_explore
        return inflater.inflate(R.layout.fragment_community, container, false);
    }
}