package com.example.covid_19trackingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

    }
    public void Clickback_ins(View view) {

        startActivity(new Intent(InstructionActivity.this,COVID19_Statistics.class));
    }
}