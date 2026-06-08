package com.nct.trenx.fragment;

import android.content.Intent;
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
import com.nct.trenx.activity.LikedWorkoutsActivity;
import com.nct.trenx.activity.MainActivity;
import com.nct.trenx.adapter.WorkoutCardAdapter;
import com.nct.trenx.database.DatabaseHelper;
import com.nct.trenx.model.Exercise;
import com.nct.trenx.utils.IntentExtras;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private AppCompatButton btnCatAll, btnCatAbs, btnCatChest, btnCatLegs, btnCatBack;
    private RecyclerView rvFollowAlong, rvYoutubeWorkouts;
    private DatabaseHelper dbHelper;
    private List<AppCompatButton> tabButtons = new ArrayList<>();

    public ExploreFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());

        btnCatAll = view.findViewById(R.id.btnCatAll);
        btnCatAbs = view.findViewById(R.id.btnCatAbs);
        btnCatChest = view.findViewById(R.id.btnCatChest);
        btnCatLegs = view.findViewById(R.id.btnCatLegs);
        btnCatBack = view.findViewById(R.id.btnCatBack);

        tabButtons.add(btnCatAll);
        tabButtons.add(btnCatAbs);
        tabButtons.add(btnCatChest);
        tabButtons.add(btnCatLegs);
        tabButtons.add(btnCatBack);

        rvFollowAlong = view.findViewById(R.id.rvFollowAlong);
        rvYoutubeWorkouts = view.findViewById(R.id.rvYoutubeWorkouts);

        btnCatAll.setOnClickListener(v -> selectTab(btnCatAll, null));
        btnCatAbs.setOnClickListener(v -> selectTab(btnCatAbs, "Bụng"));
        btnCatChest.setOnClickListener(v -> selectTab(btnCatChest, "Ngực"));
        btnCatLegs.setOnClickListener(v -> selectTab(btnCatLegs, "Chân"));
        btnCatBack.setOnClickListener(v -> selectTab(btnCatBack, "Lưng"));

        view.findViewById(R.id.cardPrevious).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(IntentExtras.TARGET_FRAGMENT, R.id.nav_progress);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.cardLiked).setOnClickListener(v -> 
                startActivity(new Intent(getContext(), LikedWorkoutsActivity.class)));

        // Initial load
        selectTab(btnCatAll, null);
    }

    private void selectTab(AppCompatButton selected, String category) {
        for (AppCompatButton btn : tabButtons) {
            btn.setBackgroundResource(R.drawable.category_bg);
            btn.setTextColor(Color.BLACK);
        }
        selected.setBackgroundResource(R.drawable.category_selected);
        selected.setTextColor(Color.WHITE);

        loadData(category);
    }

    private void loadData(String category) {
        List<Exercise> exercises;
        if (category == null) {
            exercises = dbHelper.getAllExercises();
        } else {
            exercises = dbHelper.getExercisesByCategory(category);
        }

        if (exercises.isEmpty()) {
            exercises = dbHelper.getAllExercises();
        }

        // Shuffle or split for variety
        int size = exercises.size();
        List<Exercise> list1 = new ArrayList<>(exercises.subList(0, size / 2));
        List<Exercise> list2 = new ArrayList<>(exercises.subList(size / 2, size));

        rvFollowAlong.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFollowAlong.setAdapter(new WorkoutCardAdapter(list1));

        rvYoutubeWorkouts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvYoutubeWorkouts.setAdapter(new WorkoutCardAdapter(list2));
    }
}
