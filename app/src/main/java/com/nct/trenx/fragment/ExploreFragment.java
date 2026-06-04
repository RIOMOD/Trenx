package com.nct.trenx.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.adapter.WorkoutCardAdapter;
import com.nct.trenx.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private AppCompatButton btnWorkouts;
    private AppCompatButton btnPrograms;
    private AppCompatButton btnTechniques;
    private AppCompatButton btnExercises;

    private TextView txtCategory;
    private TextView tvFollowTitle;
    private TextView tvYoutubeTitle;

    private RecyclerView rvFollowAlong;
    private RecyclerView rvYoutubeWorkouts;

    public ExploreFragment() {
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_explore,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        btnWorkouts = view.findViewById(R.id.btnWorkouts);
        btnPrograms = view.findViewById(R.id.btnPrograms);
        btnTechniques = view.findViewById(R.id.btnTechniques);
        btnExercises = view.findViewById(R.id.btnExercises);

        txtCategory = view.findViewById(R.id.txtCategory);
        tvFollowTitle = view.findViewById(R.id.tvFollowTitle);
        tvYoutubeTitle = view.findViewById(R.id.tvYoutubeTitle);

        rvFollowAlong = view.findViewById(R.id.rvFollowAlong);
        rvYoutubeWorkouts = view.findViewById(R.id.rvYoutubeWorkouts);

        setupRecyclerViews();

        btnWorkouts.setOnClickListener(v ->
                selectTab(btnWorkouts, "Workouts"));

        btnPrograms.setOnClickListener(v ->
                selectTab(btnPrograms, "Programs"));

        btnTechniques.setOnClickListener(v ->
                selectTab(btnTechniques, "Techniques"));

        btnExercises.setOnClickListener(v ->
                selectTab(btnExercises, "Exercises"));

        // Set initial selected tab
        selectTab(btnWorkouts, "Workouts");
    }

    private void setupRecyclerViews() {
        List<Exercise> dummyList = new ArrayList<>();
        dummyList.add(new Exercise("BIGGER ARMS", "15 mins", "Workouts", "", "https://i.ytimg.com/vi/pd3-q2U7JXk/maxresdefault.jpg", "", ""));
        dummyList.add(new Exercise("DUMBBELLS ONLY", "20 mins", "Workouts", "", "https://i.ytimg.com/vi/srj94JCeuWw/maxresdefault.jpg", "", ""));
        dummyList.add(new Exercise("CHEST PUMP", "12 mins", "Workouts", "", "https://thenx.com/cdn/shop/articles/legsandglutes.jpg", "", ""));

        WorkoutCardAdapter adapter1 = new WorkoutCardAdapter(dummyList);
        rvFollowAlong.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFollowAlong.setAdapter(adapter1);

        WorkoutCardAdapter adapter2 = new WorkoutCardAdapter(dummyList);
        rvYoutubeWorkouts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvYoutubeWorkouts.setAdapter(adapter2);
    }

    private void resetTabs() {
        btnWorkouts.setBackgroundResource(R.drawable.category_bg);
        btnPrograms.setBackgroundResource(R.drawable.category_bg);
        btnTechniques.setBackgroundResource(R.drawable.category_bg);
        btnExercises.setBackgroundResource(R.drawable.category_bg);

        btnWorkouts.setTextColor(Color.BLACK);
        btnPrograms.setTextColor(Color.BLACK);
        btnTechniques.setTextColor(Color.BLACK);
        btnExercises.setTextColor(Color.BLACK);
    }

    private void selectTab(AppCompatButton selectedButton,
                           String categoryName) {
        resetTabs();

        selectedButton.setBackgroundResource(R.drawable.category_selected);
        selectedButton.setTextColor(Color.WHITE);

        if (txtCategory != null) txtCategory.setText(categoryName);
        
        // Update section titles based on tab
        if (tvFollowTitle != null) tvFollowTitle.setText("Follow Along " + categoryName);
        if (tvYoutubeTitle != null) tvYoutubeTitle.setText("YouTube " + categoryName);
    }
}
