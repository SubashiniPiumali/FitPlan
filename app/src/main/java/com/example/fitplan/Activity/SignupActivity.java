package com.example.fitplan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.android.material.snackbar.Snackbar;
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


                if(validateFields()){
                    String user = signupEmail.getText().toString().trim();
                    String pass = signupPassword.getText().toString().trim();
                    String name = signupName.getText().toString().trim();
                    int age = Integer.parseInt(signupAge.getText().toString().trim());
                    int height = Integer.parseInt(signupHeight.getText().toString().trim());
                    int weight = Integer.parseInt(signupWeight.getText().toString().trim());
                    String gender = userTypeSpinner.getSelectedItem().toString();
                    String goal = fitnessGoal.getText().toString().trim();
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
                                            Snackbar.make(v, "SignUp Successful ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                        } else {
                                            Snackbar.make(v, "Failed to save user data ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                        }
                                    }
                                });
                            } else {
                                Snackbar.make(v, "SignUp Failed ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            }
                        }
                    });
                }


            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }
    private boolean validateFields() {
        boolean isValid = true;

        if (TextUtils.isEmpty(signupEmail.getText())) {
            signupEmail.setError("Email is required");
            isValid = false;
        } else {
            signupEmail.setError(null);
        }

        if (TextUtils.isEmpty(signupPassword.getText())) {
            signupPassword.setError("Password is required");
            isValid = false;
        } else {
            signupPassword.setError(null);
        }

        if (TextUtils.isEmpty(signupName.getText())) {
            signupName.setError("Full Name is required");
            isValid = false;
        } else {
            signupName.setError(null);
        }

        if (TextUtils.isEmpty(signupAge.getText())) {
            signupAge.setError("Age is required");
            isValid = false;
        } else {
            signupAge.setError(null);
        }

        if (TextUtils.isEmpty(signupHeight.getText())) {
            signupHeight.setError("Height is required");
            isValid = false;
        } else {
            signupHeight.setError(null);
        }

        if (TextUtils.isEmpty(signupWeight.getText())) {
            signupWeight.setError("Weight is required");
            isValid = false;
        } else {
            signupWeight.setError(null);
        }

        return isValid;
    }
}
