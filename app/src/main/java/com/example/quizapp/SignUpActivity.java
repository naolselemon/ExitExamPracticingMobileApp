package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullName, email, password, confirmPassword;
    private Button signUpButton;
    private ImageView backButton;
    private FirebaseAuth mAuth;
    private String nameStr, passwordStr, emailStr, confirmPasswordStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // mAuth Initialized
        mAuth = FirebaseAuth.getInstance();
        fullName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        signUpButton = findViewById(R.id.signUpButton);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        signUpButton.setOnClickListener(v -> {

            if (validated()){
                signUp();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean validated(){
        nameStr = fullName.getText().toString().trim();
        emailStr = email.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
        confirmPasswordStr = confirmPassword.getText().toString().trim();
        if (nameStr.isEmpty()){
            fullName.setError("Please enter your full name");
            return false;
        }
        if (emailStr.isEmpty()){
            email.setError("Please enter your email address");
            return false;
        }
        if (passwordStr.isEmpty()){
            password.setError("Please enter your password");
            return false;
        }
        if (passwordStr.length() < 6){
            password.setError("Password must be at least 6 characters long");
            return false;
        }
        if (!passwordStr.equals(confirmPasswordStr)){
            confirmPassword.setError("Passwords do not match");
            return false;
        }

        return true;
    }
    private void signUp(){
        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
//                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}