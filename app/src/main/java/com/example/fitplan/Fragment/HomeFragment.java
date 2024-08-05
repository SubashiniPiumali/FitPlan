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

import com.example.fitplan.APILink.RetrofitClient;
import com.example.fitplan.Adapter.ExerciseAdapter;
import com.example.fitplan.Adapter.WorkoutPlanAdapter;
import com.example.fitplan.Interface.ExerciseApiService;
import com.example.fitplan.Model.Exercise;
import com.example.fitplan.Model.WorkoutPlans;
import com.example.fitplan.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private static final String API_HOST = "exercisedb.p.rapidapi.com";
    private static final String API_KEY = "ccd5887d03msh23f8ae122bdc395p1169eajsn81eb9e23195b";
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser currentUser;
    private String userID;

    private WorkoutPlanAdapter workoutPlanAdapter;

    private List<String> workoutList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_home, container, false);

        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = currentUser.getUid();
        workoutList = new ArrayList<>();
        workoutPlanAdapter = new WorkoutPlanAdapter(workoutList);
        recyclerView.setAdapter(workoutPlanAdapter);
        // Initialize Firebase components
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            reference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("workoutPlans");
            fetchWorkoutPlans();
        }
        return  view;
    }
    private void fetchWorkoutPlans() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    workoutList.clear(); // Clear previous data (if any)

                    for (DataSnapshot planSnapshot : dataSnapshot.getChildren()) {
                        String planKey = planSnapshot.getKey();
                        workoutList.add(planKey);
//                        for (DataSnapshot exerciseSnapshot : planSnapshot.getChildren()) {
//                            String exerciseKey = exerciseSnapshot.getKey();
//                            WorkoutPlans workout = exerciseSnapshot.getValue(WorkoutPlans.class);
//
//                        }


                        workoutPlanAdapter.setOnItemClickListener(new WorkoutPlanAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(String workoutPlans) {
                                Bundle result = new Bundle();
                                result.putString("workoutPlans", workoutPlans);
                                Fragment workoutExerciseListFragment = new WorkoutExerciseListFragment();
                                workoutExerciseListFragment.setArguments(result);
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager() ;
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.frame_layout, workoutExerciseListFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }

                        });
                    }

                    workoutPlanAdapter.notifyDataSetChanged(); // Notify adapter of data change
                } else {
                    Snackbar.make(getView(), "No workout plans found", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(getView(), "Failed to fetch workout plans: " + databaseError.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

}