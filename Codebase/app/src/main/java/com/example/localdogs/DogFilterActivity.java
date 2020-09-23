package com.example.localdogs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DogFilterActivity extends AppCompatActivity {

    private ArrayList<String> dogBreeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*TODO: dogBreeds
            Populate with dog breeds. we can keep this list up to date in the database, or
            by some other means. However we do it, it probably shouldn't be locally stored on the
            phone, in case we want to update it.
         */
        this.dogBreeds = new ArrayList<String>();
        setContentView(R.layout.activity_dog_filter);
        Switch swActivityLevel = findViewById(R.id.swActivityLevel);
        swActivityLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RatingBar rbActivityLevel = findViewById(R.id.rbActivityLevel);
                if(b){
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.outlinedboxlw));
                    rbActivityLevel.setVisibility(View.VISIBLE);
                }
                else{
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.outlinedboxcg));
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
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.outlinedboxlw));
                    etMin.setVisibility(View.VISIBLE);
                    etMax.setVisibility(View.VISIBLE);
                }
                else{
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.outlinedboxcg));
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
                   compoundButton.setBackground(getResources().getDrawable(R.drawable.outlinedboxlw));
                   sbMin.setVisibility(View.VISIBLE);
                   sbMax.setVisibility(View.VISIBLE);
                }
                else{
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.outlinedboxcg));
                    sbMin.setVisibility(View.GONE);
                    sbMax.setVisibility(View.GONE);
                }
            }
        });
        Switch swBreed = findViewById(R.id.swBreed);
        swBreed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TextView twBreedsListLabel = findViewById(R.id.tvBreedsListLabel);
                if(b){
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.outlinedboxlw));
                    twBreedsListLabel.setVisibility(View.VISIBLE);
                }
                else{
                    compoundButton.setBackground(getResources().getDrawable(R.drawable.outlinedboxcg));
                    twBreedsListLabel.setVisibility(View.GONE);
                }
            }
        });
    }

    private void hideKeyboard(Context context, View view){

    }

}