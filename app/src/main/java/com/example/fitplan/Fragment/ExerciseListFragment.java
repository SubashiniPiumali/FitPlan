package com.example.fitplan.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitplan.APILink.RetrofitClient;
import com.example.fitplan.Adapter.ExerciseAdapter;
import com.example.fitplan.Adapter.ExerciseListAdapter;
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
 * Use the {@link ExerciseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ExerciseListAdapter exerciseListAdapter;

    private static final String API_HOST = "exercisedb.p.rapidapi.com";
    private static final String API_KEY = "ccd5887d03msh23f8ae122bdc395p1169eajsn81eb9e23195b";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        Bundle bundle = getArguments();
        String  bodyPart= bundle.getString("bodyPart");
       // String  image= bundle.getString("image");

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchExercises(bodyPart);
        // Display instruction
        return view;
    }

    private void fetchExercises(String bodyPart) {
        ExerciseApiService apiService = RetrofitClient.getRetrofitInstance().create(ExerciseApiService.class);
        Call<List<Exercise>> call = apiService.getExercises(API_HOST, API_KEY, 100, 0);

        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Exercise> exercises = response.body();
                    List<Exercise> uniqueExercises = filterExercises(exercises, bodyPart);

                    exerciseListAdapter = new ExerciseListAdapter(uniqueExercises);

                    recyclerView.setAdapter(exerciseListAdapter);

                    exerciseListAdapter.setOnItemClickListener(new ExerciseListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Exercise exercise) {
                            Bundle result = new Bundle();
                            result.putString("title", exercise.getName());
                            result.putString("image", exercise.getGifUrl());
                            result.putString("target", exercise.getTarget());
                            result.putString("equipment", exercise.getEquipment());
                            result.putStringArrayList("instructions", new ArrayList<>(exercise.getInstructions()));
                            result.putString("bodyPart", exercise.getBodyPart());
                            // Handle item click here, e.g., navigate to another fragment
                            Fragment exerciseDetailsFragment = new ExerciseDetailsFragment();
                            exerciseDetailsFragment.setArguments(result);
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager() ;
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.frame_layout, exerciseDetailsFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });




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

    private List<Exercise> filterExercises(List<Exercise> exercises, String bodyPart) {
        HashSet<String> exerciseNames = new HashSet<>();
        List<Exercise> filteredExercises = new ArrayList<>();

        for (Exercise exercise : exercises) {
            if (exercise.getBodyPart().equals(bodyPart)) {
                filteredExercises.add(exercise);
            }
        }

        return filteredExercises;
    }
}