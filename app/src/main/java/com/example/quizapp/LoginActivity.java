package com.example.quizapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginButton;
    private TextView forgotPassword, signupButton;
    private Dialog progressDialog;
    private TextView progressText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

         mAuth = FirebaseAuth.getInstance();

         email = findViewById(R.id.lgEmailAddress);
         password = findViewById(R.id.lgPassword);
         loginButton = findViewById(R.id.lgLoginButton);
         signupButton = findViewById(R.id.lgSignUp);
         forgotPassword = findViewById(R.id.lgForgotPassword);

        progressDialog = new Dialog(LoginActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        Objects.requireNonNull(progressDialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressText = progressDialog.findViewById(R.id.dialogText);
        progressText.setText("Signing up...");

         loginButton.setOnClickListener(view -> {
             if (validated()){
                 login();
             }else {
                 Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
             }
         });
        signupButton.setOnClickListener(view-> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
             startActivity(intent);

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    public boolean validated() {
        if (email.getText().toString().isEmpty()){
            email.setError("Please enter your email address");
            return false;
        }
        if (password.getText().toString().isEmpty()){
            password.setError("Please enter your password");
            return false;
        }

        return false;
    }

    public void login() {
        progressDialog.show();
        // Implement login logic here
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Sign in success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                           progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}