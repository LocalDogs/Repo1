package com.example.localdogs.DogFilter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localdogs.DogFilter.InputFields.DogFilterActivityLevelField;
import com.example.localdogs.DogFilter.InputFields.DogFilterAgeFields;
import com.example.localdogs.DogFilter.InputFields.DogFilterBreedFields;
import com.example.localdogs.DogFilter.InputFields.DogFilterWeightFields;
import com.example.localdogs.R;

import java.util.ArrayList;
import java.util.Arrays;

public class DogFilterActivity extends AppCompatActivity{
    private DogFilter dogFilter;
    //private ArrayList<String> dogBreeds; ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*TODO: dogBreeds
            Populate with dog breeds. we can keep this list up to date in the database, or
            by some other means. However we do it, it probably shouldn't be locally stored on the
            phone, in case we want to push updates quicker and easier.
         */
        dogFilter = DogFilter.getInstance(getApplicationContext());
        setContentView(R.layout.activity_dog_filter);
        final DogFilterActivityLevelField activityLevel = new DogFilterActivityLevelField
                (
                        this,
                        (RatingBar) findViewById(R.id.rbActivityLevel),
                        (Switch) findViewById(R.id.swActivityLevel)
                );
        final DogFilterWeightFields weights = new DogFilterWeightFields
                (
                        this,
                        (EditText) findViewById(R.id.etMinWeight),
                        (EditText) findViewById(R.id.etMaxWeight),
                        (Switch) findViewById(R.id.swWeight)
                );
        final DogFilterAgeFields ages = new DogFilterAgeFields
                (
                        this,
                        (TextView) findViewById(R.id.tvMinAgeSliderLabel),
                        (TextView) findViewById(R.id.tvMaxAgeSliderLabel),
                        (SeekBar) findViewById(R.id.sbMinAge),
                        (SeekBar) findViewById(R.id.sbMaxAge),
                        (Switch) findViewById(R.id.swAge)
                );
        final DogFilterBreedFields breeds = new DogFilterBreedFields
                (
                        this,
                        (EditText) findViewById(R.id.etBreedName),
                        (EditText) findViewById(R.id.etBreedName2),
                        (Switch) findViewById(R.id.swBreed)
                );
        Button btnSaveFilter = findViewById(R.id.bttnSaveFilter);
        btnSaveFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDogFilter
                        (
                                0,
                                activityLevel.getActivityLevel(),
                                ages.getMinAge(),
                                ages.getMaxAge(),
                                weights.getMinWeight(),
                                weights.getMaxWeight(),
                                breeds.getFirstBreed(),
                                breeds.getSecondBreed()
                        );
            }
        });

    }

    private void updateDogFilter
            (
                    int minACL,
                    int maxACL,
                    int minAge,
                    int maxAge,
                    int minWeight,
                    int maxWeight,
                    String breed1,
                    String breed2
            )
    {
        dogFilter.setMinAcl(minACL);
        dogFilter.setMaxACL(maxACL);
        dogFilter.setBreed1(breed1);
        dogFilter.setBreed2(breed2);
        dogFilter.setMaxAge(maxAge);
        dogFilter.setMinAge(minAge);
        dogFilter.setMaxWeight(maxWeight);
        dogFilter.setMinWeight(minWeight);
        Log.d("PrintDogFilter", dogFilter.toString());
        Intent intent = new Intent();
        intent.putExtra("filter", this.dogFilter);
        setResult(RESULT_OK, intent);
        finish();
    }
}