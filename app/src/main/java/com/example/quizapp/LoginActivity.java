package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginButton;
    private TextView forgotPassword, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
         email = findViewById(R.id.lgEmailAddress);
         password = findViewById(R.id.lgPassword);
         loginButton = findViewById(R.id.lgLoginButton);
         signupButton = findViewById(R.id.lgSignUp);
         forgotPassword = findViewById(R.id.lgForgotPassword);

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
        // Implement login logic here
    }
}