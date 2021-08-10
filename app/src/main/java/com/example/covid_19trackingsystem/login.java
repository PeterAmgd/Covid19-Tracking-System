package com.example.covid_19trackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText passwordTV1,emailTV;
    private Button SignInBtn;
    private TextView btnforg;
    private TextView btnSignup;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth

        progressBar = findViewById(R.id.progressBar22);

        mAuth = FirebaseAuth.getInstance();
        btnSignup=findViewById(R.id.signUp);

        SignInBtn = findViewById(R.id.butonlogin);
        passwordTV1 = findViewById(R.id.buttnpass);
        emailTV = findViewById(R.id.buttnemail);
        progressDialog = new ProgressDialog(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);




        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(login.this,UserRegistration.class));
                finish();

            }
        });





        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOGIN();
                startActivity(new Intent(login.this, COVID19_Statistics.class));
                finish();
            }
        });







    }

    private void LOGIN() {

        String  email = emailTV.getText().toString().trim();
        String  Password = passwordTV1.getText().toString().trim();


        if (TextUtils.isEmpty(email)){
            emailTV.setError("Enter your Email");

            return;
        }
        else if (TextUtils.isEmpty(Password)){
            passwordTV1.setError("Enter your Password");
            return;
        }
        //progressDialog.setMessage("Logging in , Please Wait ");
       // progressDialog.show();
        //progressDialog.setCanceledOnTouchOutside(false);


        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){


                    Toast.makeText(login.this ,"Logged In Success",Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent(login.this,fragment_home.class);
                    startActivity(intent);
                    finish();*/
                }
                else {
                    Toast.makeText(login.this ,"Logged In Failed",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}