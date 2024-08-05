package com.example.fitplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitplan.Model.Exercise;
import com.example.fitplan.Model.WorkoutPlans;
import com.example.fitplan.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class WorkoutExerciseAdapter  extends RecyclerView.Adapter<WorkoutExerciseAdapter.WorkoutExerciseViewHolder> {

    private final List<WorkoutPlans> workoutPlansList;

    private WorkoutExerciseAdapter.OnItemClickListener listener;

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private FirebaseUser user;

    private String userID;


    private int selectedYear, selectedMonth;
    private  String weekNo;

    // Constructor and ViewHolder implementation...

    public void setOnItemClickListener(WorkoutExerciseAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public WorkoutExerciseAdapter(List<WorkoutPlans> exerciseList, int selectedYear, int selectedMonth, String weekNo) {
        this.workoutPlansList = exerciseList;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        userID = user.getUid();
        this.weekNo = weekNo;
        this.selectedMonth = selectedMonth;
        this.selectedYear = selectedYear;
    }

    @NonNull
    @Override
    public WorkoutExerciseAdapter.WorkoutExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workout_plans_exercise_list, parent, false);
        return new WorkoutExerciseAdapter.WorkoutExerciseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WorkoutExerciseAdapter.WorkoutExerciseViewHolder holder, int position) {
        WorkoutPlans workoutPlan = workoutPlansList.get(position);

        boolean nextTitleCase = true;
        StringBuilder titleCaseName = new StringBuilder();
        for (char c : workoutPlan.getName().toCharArray()) {
            if (Character.isSpaceChar(c) || c == '\t' || c == '\n' || c == '\r') {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCaseName.append(c);
        }

        StringBuilder titleCaseEquipment = new StringBuilder();
        for (char c : workoutPlan.getEquipment().toCharArray()) {
            if (Character.isSpaceChar(c) || c == '\t' || c == '\n' || c == '\r') {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCaseEquipment.append(c);
        }

        StringBuilder titleCaseTarget = new StringBuilder();
        for (char c : workoutPlan.getTarget().toCharArray()) {
            if (Character.isSpaceChar(c) || c == '\t' || c == '\n' || c == '\r') {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCaseTarget.append(c);
        }

        holder.exerciseName.setText(titleCaseName.toString());
        holder.exerciseEquipment.setText(titleCaseEquipment.toString());
        holder.exerciseTarget.setText(titleCaseTarget.toString());
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = selectedYear + "-" +  selectedMonth + "-" + weekNo;
                WorkoutPlans workoutPlan = new WorkoutPlans();
                workoutPlan.setName(workoutPlan.getName());
                workoutPlan.setBodyPart(workoutPlan.getBodyPart());
                workoutPlan.setEquipment(workoutPlan.getEquipment());
                workoutPlan.setGifUrl(workoutPlan.getGifUrl());
                workoutPlan.setPlanNo(date);
                int PlanNumber = 1;

                // Get reference to user's workout plans node in Firebase
                DatabaseReference userWorkoutPlansRef = mDatabase.child(userID).child("workoutPlans").child(date);

                // Push new workout plan under user's node
                String workoutPlanKey = userWorkoutPlansRef.push().getKey();
                workoutPlan.setId(workoutPlanKey);
                userWorkoutPlansRef.child(workoutPlanKey).setValue(workoutPlan);

                //mDatabase.push().setValue(exercise);
                Snackbar.make(v, "Exercise added!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
        holder.bind(workoutPlan);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(workoutPlan);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return workoutPlansList.size();
    }

    public static class WorkoutExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView exerciseEquipment;
        Button addBtn;

        TextView exerciseTarget;
        ImageView exerciseimage;
        CardView cardView;
        public WorkoutExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_title);
            exerciseimage = itemView.findViewById(R.id.exercise_image);
            cardView = itemView.findViewById(R.id.card_view);
            exerciseEquipment = itemView.findViewById(R.id.exercise_equipment);
            exerciseTarget = itemView.findViewById(R.id.exercise_traget);
            addBtn = itemView.findViewById(R.id.addbtn);

        }
        public void bind(WorkoutPlans workoutPlan) {
            Glide.with(itemView.getContext())
                    .load(workoutPlan.getGifUrl()) // Assuming getGifUrl() returns a String URL
                    .into(exerciseimage);
        }

    }
    public interface OnItemClickListener {
        void onItemClick(WorkoutPlans workoutPlan);
    }

}