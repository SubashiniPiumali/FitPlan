package com.example.fitplan.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitplan.APILink.RetrofitClient;
import com.example.fitplan.Adapter.ExerciseAdapter;
import com.example.fitplan.Interface.ExerciseApiService;
import com.example.fitplan.Model.Exercise;
import com.example.fitplan.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateWorkoutPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateWorkoutPlanFragment extends Fragment {

    private static final String API_HOST = "exercisedb.p.rapidapi.com";
    private static final String API_KEY = "ccd5887d03msh23f8ae122bdc395p1169eajsn81eb9e23195b";
    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_create_workout_plan, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        fetchExercises();
        return view;
    }

    private void fetchExercises() {
        ExerciseApiService apiService = RetrofitClient.getRetrofitInstance().create(ExerciseApiService.class);
        Call<List<Exercise>> call = apiService.getExercises(API_HOST, API_KEY, 100, 0);

        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Exercise> exercises = response.body();
                    List<Exercise> uniqueExercises = filterUniqueExercises(exercises);

                    Collections.sort(uniqueExercises, new Comparator<Exercise>() {
                        @Override
                        public int compare(Exercise e1, Exercise e2) {
                            return e1.getBodyPart().compareToIgnoreCase(e2.getBodyPart());
                        }
                    });
                    exerciseAdapter = new ExerciseAdapter(uniqueExercises);

                    recyclerView.setAdapter(exerciseAdapter);
                } else {
                    Log.e("CreateWorkoutPlanFragment", "Request failed. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                Log.e("CreateWorkoutPlanFragment", "API call failed", t);
            }
        });
    }

    private List<Exercise> filterUniqueExercises(List<Exercise> exercises) {
        HashSet<String> exerciseNames = new HashSet<>();
        List<Exercise> uniqueExercises = new ArrayList<>();

        for (Exercise exercise : exercises) {
            if (exerciseNames.add(exercise.getBodyPart())) {
                uniqueExercises.add(exercise);
            }
        }

        return uniqueExercises;
    }
}