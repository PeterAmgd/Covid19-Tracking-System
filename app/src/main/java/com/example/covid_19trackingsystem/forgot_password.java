package com.example.covid_19trackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity implements View.OnClickListener {

    //Intialize variables

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;
    private ImageView imageView;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_forgot_password);


        // inzialize activity buttons

        emailEditText = (EditText) findViewById(R.id.btnforgtPass);
        resetPasswordButton = (Button) findViewById(R.id.btnsubmit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        imageView = (ImageView) findViewById(R.id.butnback);
        imageView.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();


        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }


    private void resetPassword() {
        String email2 = emailEditText.getText().toString().trim();

        //if condition if email is empty or not and check email pattern is valid

        if (email2.isEmpty()) {
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
            emailEditText.setError("Please Provide valid email!");
            emailEditText.requestFocus();
            return;
        }

        //progress bar visibilty
        progressBar.setVisibility(View.VISIBLE);

        //send email reset
        auth.sendPasswordResetEmail(email2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                //check condition and set Toast message

                if (task.isSuccessful()) {
                    Toast.makeText(forgot_password.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(forgot_password.this, login.class));
                } else {
                    Toast.makeText(forgot_password.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butnback:
                startActivity(new Intent(this, login.class));
                break;


        }
    }

}