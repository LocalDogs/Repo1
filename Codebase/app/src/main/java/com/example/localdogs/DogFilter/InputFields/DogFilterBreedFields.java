package com.example.localdogs.DogFilter.InputFields;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.localdogs.R;

public class DogFilterBreedFields implements CompoundButton.OnCheckedChangeListener {

    private EditText breed1;
    private EditText breed2;
    private Context context;
    private Switch swBreed;

    public DogFilterBreedFields(Context context, EditText breed1, EditText breed2, Switch swBreed){
        this.breed1 = breed1;
        this.breed2 = breed2;
        this.context = context;
        this.swBreed = swBreed;
        swBreed.setOnCheckedChangeListener(this);

    }
    
    @SuppressLint("NewApi")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {

            compoundButton.setBackground(context.getResources().getDrawable(R.drawable.outlinedboxlw));
            breed1.setVisibility(View.VISIBLE);
            breed2.setVisibility(View.VISIBLE);

        } else {

            compoundButton.setBackground(context.getDrawable(R.drawable.outlinedboxcg));
            breed1.setVisibility(View.GONE);
            breed2.setVisibility(View.GONE);

        }
    }
    public String firstBreed(){
        return breed1.getText().toString();
    }
    public String secondBreed(){
        return breed2.getText().toString();
    }

}
