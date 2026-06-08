package com.nct.trenx.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nct.trenx.R;
import com.nct.trenx.adapter.ExerciseAdapter;
import com.nct.trenx.database.ExerciseRepository;
import com.nct.trenx.model.Exercise;
import com.nct.trenx.utils.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private EditText etSearch;
    private ImageView ivClear;
    private RecyclerView rvSearchResults;
    private LinearLayout layoutEmptySearch;
    private TextView tvEmptyTitle, tvEmptyDesc;
    private ImageView ivEmptyIcon;
    private ExerciseAdapter adapter;
    private ExerciseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        repository = new ExerciseRepository(this);

        ImageView ivBack = findViewById(R.id.iv_back);
        etSearch = findViewById(R.id.et_search);
        ivClear = findViewById(R.id.iv_clear);
        rvSearchResults = findViewById(R.id.rv_search_results);
        layoutEmptySearch = findViewById(R.id.layout_empty_search);
        tvEmptyTitle = findViewById(R.id.tv_search_empty_title);
        tvEmptyDesc = findViewById(R.id.tv_search_empty_desc);
        ivEmptyIcon = findViewById(R.id.iv_search_empty_icon);

        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        setupRecyclerView();
        setupSearchLogic();

        if (ivClear != null) {
            ivClear.setOnClickListener(v -> {
                if (etSearch != null) {
                    etSearch.setText("");
                }
            });
        }
    }

    private void setupRecyclerView() {
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseAdapter(new ArrayList<>());
        adapter.setOnExerciseClickListener((exercise, position) -> {
            List<Exercise> group = repository.getExercisesByDayAndDifficulty(exercise.getDay(), exercise.getDifficulty());
            int actualPos = 0;
            for (int i = 0; i < group.size(); i++) {
                if (group.get(i).getName().equalsIgnoreCase(exercise.getName())) {
                    actualPos = i;
                    break;
                }
            }
            NavigationUtils.startTraining(SearchActivity.this, 
                    exercise.getDay(), exercise.getDifficulty(), actualPos);
        });
        rvSearchResults.setAdapter(adapter);
    }

    private void setupSearchLogic() {
        if (etSearch != null) {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String query = s.toString().trim();
                    if (ivClear != null) {
                        ivClear.setVisibility(query.length() > 0 ? View.VISIBLE : View.GONE);
                    }
                    performSearch(query);
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void performSearch(String query) {
        if (query.isEmpty()) {
            adapter.updateData(new ArrayList<>());
            rvSearchResults.setVisibility(View.GONE);
            layoutEmptySearch.setVisibility(View.VISIBLE);
            
            ivEmptyIcon.setImageResource(R.drawable.ic_search);
            tvEmptyTitle.setText(R.string.start_searching_title);
            tvEmptyDesc.setText(R.string.start_searching_desc);
            return;
        }

        List<Exercise> results = repository.searchExercises(query);
        adapter.updateData(results);

        if (results.isEmpty()) {
            rvSearchResults.setVisibility(View.GONE);
            layoutEmptySearch.setVisibility(View.VISIBLE);
            
            ivEmptyIcon.setImageResource(R.drawable.ic_search);
            tvEmptyTitle.setText(R.string.no_results_title);
            tvEmptyDesc.setText(R.string.no_results_desc);
        } else {
            rvSearchResults.setVisibility(View.VISIBLE);
            layoutEmptySearch.setVisibility(View.GONE);
        }
    }
}
