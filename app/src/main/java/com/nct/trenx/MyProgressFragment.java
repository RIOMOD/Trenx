package com.nct.trenx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class MyProgressFragment extends Fragment {

    public MyProgressFragment() {
        // Constructor rỗng
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // CHÚ Ý DÒNG NÀY: Phải gọi đúng chữ R.layout.fragment_explore
// Sửa dòng 18 thành thế này:
        return inflater.inflate(R.layout.myprogress_community, container, false);
    }
}