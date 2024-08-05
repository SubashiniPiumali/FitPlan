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
import com.example.fitplan.Model.WorkoutPlans;
import com.example.fitplan.R;

import java.util.List;

public class WorkoutPlanExerciseListAdapter extends RecyclerView.Adapter<WorkoutPlanExerciseListAdapter.WorkoutPlanExerciseListViewHolder> {

    private List<WorkoutPlans> workoutPlansList;

    private WorkoutPlanExerciseListAdapter.OnItemClickListener listener;

    // Constructor and ViewHolder implementation...
    public void setOnItemClickListener(WorkoutPlanExerciseListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public WorkoutPlanExerciseListAdapter(List<WorkoutPlans> workoutPlansList) {
        this.workoutPlansList = workoutPlansList;
    }

    @NonNull
    @Override
    public WorkoutPlanExerciseListAdapter.WorkoutPlanExerciseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workout_plans_exercise_list, parent, false);
        return new WorkoutPlanExerciseListAdapter.WorkoutPlanExerciseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutPlanExerciseListAdapter.WorkoutPlanExerciseListViewHolder holder, int position) {
        WorkoutPlans workoutPlans = workoutPlansList.get(position);

        boolean nextTitleCase = true;
        holder.exerciseName.setText(workoutPlans.getName());
        holder.exerciseEquipment.setText(workoutPlans.getEquipment());
        holder.exerciseTarget.setText(workoutPlans.getTarget());

        holder.bind(workoutPlans);

        // holder.exerciseName.setText(titleCase.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(workoutPlans);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return workoutPlansList.size();
    }


    public static class WorkoutPlanExerciseListViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView exerciseEquipment;
        Button addBtn;

        TextView exerciseTarget;
        ImageView exerciseimage;
        CardView cardView;

        public WorkoutPlanExerciseListViewHolder(@NonNull View itemView) {
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
        void onItemClick(WorkoutPlans workoutPlans);
    }
}