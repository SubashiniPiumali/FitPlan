package com.example.fitplan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitplan.R;
import com.example.fitplan.databinding.ActivityLoginBinding;
import com.example.fitplan.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private EditText logInpEmail, logInPassword;
    private Button loginButton;
    private TextView signUpRedirectText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        logInpEmail = binding.useremail.getEditText();
        logInPassword = binding.password.getEditText();
        loginButton = binding.loginButton;
        signUpRedirectText = binding.btnSignUp;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = logInpEmail.getText().toString();
                String pass = logInPassword.getText().toString();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(!pass.isEmpty()){
                        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Snackbar.make(v, "Login Successful", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                Intent intent = new Intent(LoginActivity.this, FragmentHandlerActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(v, "Please enter correct email and password", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            }
                        });
                    } else {
                        logInPassword.setError("Password cannot be empty");
                    }

                } else if(email.isEmpty()) {
                    logInpEmail.setError("Email cannot be empty");
                } else {
                    logInPassword.setError("Please enter valid email");
                }
            }
        });

        signUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }
}