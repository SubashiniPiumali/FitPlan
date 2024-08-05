package com.example.fitplan.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitplan.Adapter.WorkoutPlanAdapter;
import com.example.fitplan.Adapter.WorkoutPlanExerciseListAdapter;
import com.example.fitplan.Model.WorkoutPlans;
import com.example.fitplan.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutExerciseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutExerciseListFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String userID;

    private FirebaseUser currentUser;
    String     workoutPlansKey;
    private RecyclerView recyclerView;
    private List<WorkoutPlans> workoutList;
    private WorkoutPlanExerciseListAdapter workoutPlanExerciseListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_exercise_list, container, false);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = currentUser.getUid();
        Bundle bundle = getArguments();
        workoutList = new ArrayList<>();
        workoutPlansKey = bundle.getString("workoutPlans");
        Log.e("testiung", workoutPlansKey);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            reference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("workoutPlans").child(workoutPlansKey);;
            fetchWorkoutPlanData(view);
        }
        return view;
    }

    private void fetchWorkoutPlanData(View view) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    //workoutList.clear(); // Clear previous data (if any)

                    for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {
                        WorkoutPlans workout = exerciseSnapshot.getValue(WorkoutPlans.class);
                        workoutList.add(workout);
                    }

                    workoutPlanExerciseListAdapter = new WorkoutPlanExerciseListAdapter(workoutList);
                    recyclerView.setAdapter(workoutPlanExerciseListAdapter);
                    workoutPlanExerciseListAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(getView(), "No workout exercises found for this plan", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(getView(), "Failed to fetch workout plans: " + error.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}