package com.example.fitplan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitplan.Database.HelperClass;
import com.example.fitplan.Fragment.ProfileFragment;
import com.example.fitplan.R;
import com.example.fitplan.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    private FirebaseAuth auth;

    private EditText signupEmail, signupPassword, signupName, signupAge, signupHeight, signupWeight, fitnessGoal;
    private Spinner userTypeSpinner;
    private Button signupButton;
    private TextView loginRedirectText;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        signupEmail = binding.useremail.getEditText();
        signupPassword = binding.password.getEditText();
        signupButton = binding.btnSignUp;
        loginRedirectText = binding.btnBackToSignIn;
        signupName = binding.name.getEditText();
        signupAge = binding.age.getEditText();
        signupHeight = binding.height.getEditText();
        signupWeight = binding.weight.getEditText();
        userTypeSpinner = binding.userTypeSpinner;
        fitnessGoal = binding.goal.getEditText();
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String name = signupName.getText().toString().trim();
                int age = Integer.parseInt(signupAge.getText().toString().trim());
                int height = Integer.parseInt(signupHeight.getText().toString().trim());
                int weight = Integer.parseInt(signupWeight.getText().toString().trim());
                String gender = userTypeSpinner.getSelectedItem().toString();
                String goal = fitnessGoal.getText().toString().trim();

                if(user.isEmpty()){
                    signupEmail.setError("Email cannot be empty");
                    return;
                }

                if(pass.isEmpty()){
                    signupPassword.setError("Password cannot be empty");
                    return;
                }

                auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String userId = auth.getCurrentUser().getUid();
                            database = FirebaseDatabase.getInstance();
                            reference = database.getReference("users");

                            HelperClass helperClass = new HelperClass(name, gender, age, weight, height, user,goal, 0.00);
                            reference.child(userId).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(SignupActivity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignupActivity.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }
}
