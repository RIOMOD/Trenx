package com.nct.trenx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_LAYOUT_RES = "layout_res";

    public static PlaceholderFragment newInstance(@LayoutRes int layoutResId) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES, layoutResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResFromArgs(), container, false);
    }

    @LayoutRes
    private int getLayoutResFromArgs() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getInt(ARG_LAYOUT_RES);
        }
        return com.nct.trenx.R.layout.fragment_community;
    }
}
