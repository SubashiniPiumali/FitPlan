package com.example.fitplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder> {

private final List<Exercise> exerciseList;

    private OnItemClickListener listener;

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private FirebaseUser user;

    private String userID;


    private int selectedYear, selectedMonth;
    private  String weekNo;

    // Constructor and ViewHolder implementation...

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ExerciseListAdapter(List<Exercise> exerciseList, int selectedYear, int selectedMonth, String weekNo) {
        this.exerciseList = exerciseList;
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
    public ExerciseListAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.excersise_card_view, parent, false);
        return new ExerciseListAdapter.ExerciseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);

        boolean nextTitleCase = true;
        StringBuilder titleCaseName = new StringBuilder();
        for (char c : exercise.getName().toCharArray()) {
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
        for (char c : exercise.getEquipment().toCharArray()) {
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
        for (char c : exercise.getTarget().toCharArray()) {
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
                workoutPlan.setName(exercise.getName());
                workoutPlan.setBodyPart(exercise.getBodyPart());
                workoutPlan.setEquipment(exercise.getEquipment());
                workoutPlan.setGifUrl(exercise.getGifUrl());
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
        holder.bind(exercise);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(exercise);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView exerciseEquipment;
        Button addBtn;

        TextView exerciseTarget;
        ImageView exerciseimage;
        CardView cardView;
        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_title);
            exerciseimage = itemView.findViewById(R.id.exercise_image);
            cardView = itemView.findViewById(R.id.card_view);
            exerciseEquipment = itemView.findViewById(R.id.exercise_equipment);
            exerciseTarget = itemView.findViewById(R.id.exercise_traget);
            addBtn = itemView.findViewById(R.id.addbtn);

        }
        public void bind(Exercise exercise) {
            Glide.with(itemView.getContext())
                    .load(exercise.getGifUrl()) // Assuming getGifUrl() returns a String URL
                    .into(exerciseimage);
        }

    }
    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    private int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    private int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1; // Month is zero-based
    }
}