package com.example.fitplan.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.fitplan.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileFragment extends Fragment {
    private EditText name, age, height, weight,fitnessGoal, bmiValue;
    private Button btnUpdate;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize TextInputLayouts and get EditTexts
        TextInputLayout nameLayout = view.findViewById(R.id.name);
        TextInputLayout ageLayout = view.findViewById(R.id.age);
        TextInputLayout heightLayout = view.findViewById(R.id.height);
        TextInputLayout weightLayout = view.findViewById(R.id.weight);
        TextInputLayout fitnessGoalLayout = view.findViewById(R.id.fitness_goal);
        TextInputLayout bmiLayout = view.findViewById(R.id.bmiValue);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        name = nameLayout.getEditText();
        age = ageLayout.getEditText();
        height = heightLayout.getEditText();
        weight = weightLayout.getEditText();
        fitnessGoal = fitnessGoalLayout.getEditText();
        bmiValue = bmiLayout.getEditText();

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();
        // Fetch user data from Firebase
        fetchUserData(view);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData(view);
            }
        });


        return view;
    }

    private void fetchUserData(View view) {
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get user details
                    String userName = snapshot.child("name").getValue(String.class);
                    int userAge = snapshot.child("age").getValue(Integer.class);
                    Double userHeight = snapshot.child("height").getValue(Double.class);
                    Double userWeight = snapshot.child("weight").getValue(Double.class);
                    String userfitnessGoal = snapshot.child("fitnessGoal").getValue(String.class);
                    Double userBMI = snapshot.child("bmiValue").getValue(Double.class);
                    String bmiV =  String.format("%.2f", userBMI);
                    // Set the fetched details to EditTexts
                    name.setText(userName);
                    age.setText(String.valueOf(userAge));
                    height.setText(String.valueOf(userHeight));
                    weight.setText(String.valueOf(userWeight));
                    fitnessGoal.setText(userfitnessGoal);
                    bmiValue.setText(String.valueOf(bmiV));
                } else {
                    Snackbar.make(view, "User details not found", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(view, "Failed to load user details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    private void updateUserData(View view) {
        String newName = name.getText().toString();
        String newAge = age.getText().toString();
        String newHeight = height.getText().toString();
        String newWeight = weight.getText().toString();
        String newFitnessGoal = fitnessGoal.getText().toString();
        String newBMIValue = bmiValue.getText().toString();

        reference.child(userID).child("name").setValue(newName);
        reference.child(userID).child("age").setValue(Integer.parseInt(newAge));
        reference.child(userID).child("height").setValue(Double.parseDouble(newHeight));
        reference.child(userID).child("weight").setValue(Double.parseDouble(newWeight));
        reference.child(userID).child("fitnessGoal").setValue(newFitnessGoal);
        reference.child(userID).child("bmiValue").setValue(Float.parseFloat(newBMIValue))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Snackbar.make(view, "User details updated successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, "Failed to update user details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                });
    }
}
