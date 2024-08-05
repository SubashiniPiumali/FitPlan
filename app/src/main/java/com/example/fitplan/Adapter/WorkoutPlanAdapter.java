package com.example.fitplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitplan.Model.Exercise;
import com.example.fitplan.Model.WorkoutPlans;
import com.example.fitplan.R;

import java.util.List;

public class WorkoutPlanAdapter  extends RecyclerView.Adapter<WorkoutPlanAdapter.WorkoutViewHolder> {

    private List<String> workoutPlansList;

    private WorkoutPlanAdapter.OnItemClickListener listener;

    // Constructor and ViewHolder implementation...
    public void setOnItemClickListener(WorkoutPlanAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public WorkoutPlanAdapter(List<String> workoutPlansList) {
        this.workoutPlansList = workoutPlansList;
    }

    @NonNull
    @Override
    public WorkoutPlanAdapter.WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_workout_plans, parent, false);
        return new WorkoutPlanAdapter.WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutPlanAdapter.WorkoutViewHolder holder, int position) {
        String workoutPlans = workoutPlansList.get(position);

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


    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView planNumber;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            planNumber = itemView.findViewById(R.id.planNumber);
        }
        public void bind(String workoutPlans) {

            planNumber.setText(workoutPlans);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String workoutPlans);
    }
}