package com.example.fitplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitplan.Model.Exercise;
import com.example.fitplan.R;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder> {

private final List<Exercise> exerciseList;

    private OnItemClickListener listener;

    // Constructor and ViewHolder implementation...

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ExerciseListAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
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
}