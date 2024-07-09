package com.example.fitplan.Fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitplan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_details, container, false);

        Bundle bundle = this.getArguments();
        String image = bundle.getString("image");
        String title = bundle.getString("title");
        String bodyPart = bundle.getString("bodyPart");
        String equipment = bundle.getString("equipment");
        String target = bundle.getString("target");
        ArrayList<String> instructions = bundle.getStringArrayList("instructions");

        ImageView posterImage = view.findViewById(R.id.poster_image);
        TextView exerciseTitle = view.findViewById(R.id.exerciseTitle);
        TextView bodyPartText = view.findViewById(R.id.bodyPart);
        TextView equipmentTextView = view.findViewById(R.id.equipment);
        TextView targetText = view.findViewById(R.id.target);
        LinearLayout instructionsContainer = view.findViewById(R.id.instructions_container);

        // Load the image using Glide
  Glide.with(this)
            .load(image)
            .into(posterImage);

        boolean nextTitleCaseOne = true;
        StringBuilder titleCaseTitle = new StringBuilder();
        for (char c : title.toCharArray()) {
            if (Character.isSpaceChar(c) || c == '\t' || c == '\n' || c == '\r') {
                nextTitleCaseOne = true;
            } else if (nextTitleCaseOne) {
                c = Character.toTitleCase(c);
                nextTitleCaseOne = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCaseTitle.append(c);
        }
        boolean nextTitleCaseTwo = true;

        StringBuilder titleCaseBodyPart = new StringBuilder();
        for (char c : bodyPart.toCharArray()) {
            if (Character.isSpaceChar(c) || c == '\t' || c == '\n' || c == '\r') {
                nextTitleCaseTwo = true;
            } else if (nextTitleCaseTwo) {
                c = Character.toTitleCase(c);
                nextTitleCaseTwo = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCaseBodyPart.append(c);
        }

        boolean nextTitleCaseThree = true;
        StringBuilder titleCaseEquipment = new StringBuilder();
        for (char c : equipment.toCharArray()) {
            if (Character.isSpaceChar(c) || c == '\t' || c == '\n' || c == '\r') {
                nextTitleCaseThree = true;
            } else if (nextTitleCaseThree) {
                c = Character.toTitleCase(c);
                nextTitleCaseThree = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCaseEquipment.append(c);
        }
        boolean nextTitleCaseFour = true;

        StringBuilder titleCaseTarget = new StringBuilder();
        for (char c : target.toCharArray()) {
            if (Character.isSpaceChar(c) || c == '\t' || c == '\n' || c == '\r') {
                nextTitleCaseFour = true;
            } else if (nextTitleCaseFour) {
                c = Character.toTitleCase(c);
                nextTitleCaseFour = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCaseTarget.append(c);
        }

        exerciseTitle.setText(titleCaseTitle.toString());
        bodyPartText.setText(titleCaseBodyPart.toString());
        equipmentTextView.setText(titleCaseEquipment.toString());
        targetText.setText(titleCaseTarget.toString());

        if (instructions != null) {
            for (String instruction : instructions) {
                TextView instructionView = new TextView(getContext());
                instructionView.setText(instruction);
                instructionView.setPadding(0, 8, 0, 8);
                instructionsContainer.addView(instructionView);
            }
        }

        return view;

    }
}