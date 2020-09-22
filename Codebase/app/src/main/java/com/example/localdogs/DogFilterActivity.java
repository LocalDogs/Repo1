package com.example.localdogs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;

public class DogFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_filter);
        Switch swActivityLevel = findViewById(R.id.swActivityLevel);
        swActivityLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RatingBar rbActivityLevel = findViewById(R.id.rbActivityLevel);
                if(b){
                    rbActivityLevel.setVisibility(View.VISIBLE);
                }
                else{
                    rbActivityLevel.setVisibility(View.GONE);
                }
            }
        });
        Switch swWeight = findViewById(R.id.swWeight);
        swWeight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                EditText etMin = findViewById(R.id.etMinWeight);
                EditText etMax = findViewById(R.id.etMaxWeight);
                if(b){
                    etMin.setVisibility(View.VISIBLE);
                    etMax.setVisibility(View.VISIBLE);
                }
                else{
                    etMin.setVisibility(View.GONE);
                    etMax.setVisibility(View.GONE);
                }
            }
        });
        Switch swAge = findViewById(R.id.swAge);
        swAge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SeekBar sbMin = findViewById(R.id.sbMinAge);
                SeekBar sbMax = findViewById(R.id.sbMaxAge);
                if(b){
                   sbMin.setVisibility(View.VISIBLE);
                   sbMax.setVisibility(View.VISIBLE);
                }
                else{
                    sbMin.setVisibility(View.GONE);
                    sbMax.setVisibility(View.GONE);
                }
            }
        });


    }
}