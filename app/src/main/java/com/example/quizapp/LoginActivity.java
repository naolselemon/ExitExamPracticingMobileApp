package com.example.quizapp;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.credentials.Credential;
import android.credentials.CredentialManager;
import android.os.Bundle;
import android.service.credentials.GetCredentialRequest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginButton;
    private TextView forgotPassword, signupButton;
    private Dialog progressDialog;
    private TextView progressText;
    private FirebaseAuth mAuth;
    private RelativeLayout googleLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        DBQuery.get_firestore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.lgEmailAddress);
        password = findViewById(R.id.lgPassword);
        loginButton = findViewById(R.id.lgLoginButton);
        signupButton = findViewById(R.id.lgSignUp);
//        forgotPassword = findViewById(R.id.lgForgotPassword);


        progressDialog = new Dialog(LoginActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        Objects.requireNonNull(progressDialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressText = progressDialog.findViewById(R.id.dialogText);
        progressText.setText("Signing in...");

        loginButton.setOnClickListener(view -> {
            if (validated()){
                login();
            }else {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

        signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


//    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
//        progressDialog.show();
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, task -> {
//                    progressDialog.dismiss();
//                    if (task.isSuccessful()) {
//                        Toast.makeText(LoginActivity.this, "Sign in success", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    public boolean validated() {
        if (email.getText().toString().isEmpty()) {
            email.setError("Please enter your email address");
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            password.setError("Please enter your password");
            return false;
        }
        return true;
    }

    public void login() {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in success", Toast.LENGTH_SHORT).show();
                        DBQuery.loadData(new CompleteListener() {
                            @Override
                            public void onSuccess() {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure() {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Something wrong happened! Try again later", Toast.LENGTH_SHORT).show();

                            }
                        });
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}