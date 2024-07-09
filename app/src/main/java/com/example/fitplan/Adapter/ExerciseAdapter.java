
package com.example.fitplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitplan.Model.Exercise;
import com.example.fitplan.R;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exerciseList;

    private OnItemClickListener listener;

    // Constructor and ViewHolder implementation...
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_body_parts, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);

        holder.bind(exercise);

       // holder.exerciseName.setText(titleCase.toString());
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

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.body_part_title);
        }
        public void bind(Exercise exercise) {
            StringBuilder titleCase = new StringBuilder();
            boolean nextTitleCase = true;

            for (char c : exercise.getBodyPart().toCharArray()) {
                if (Character.isSpaceChar(c) || c == '\t' || c == '\n' || c == '\r') {
                    nextTitleCase = true;
                } else if (nextTitleCase) {
                    c = Character.toTitleCase(c);
                    nextTitleCase = false;
                } else {
                    c = Character.toLowerCase(c);
                }

                titleCase.append(c);
            }
            exerciseName.setText(exercise.getBodyPart());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }
}
