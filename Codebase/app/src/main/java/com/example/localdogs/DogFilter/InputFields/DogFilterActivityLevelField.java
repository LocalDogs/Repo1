package com.example.localdogs.DogFilter.InputFields;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;

import com.example.localdogs.R;

public class DogFilterActivityLevelField implements CompoundButton.OnCheckedChangeListener {

    private RatingBar rbActivityLevel;
    private Switch swActivityLevel;
    private Context context;

    public DogFilterActivityLevelField(Context context, RatingBar rbActivityLevel, Switch swActivityLevel){
        this.rbActivityLevel = rbActivityLevel;
        this.swActivityLevel = swActivityLevel;
        this.context = context;
        swActivityLevel.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {

            swActivityLevel.setBackground(context.getResources().getDrawable(R.drawable.outlinedboxcg));
            rbActivityLevel.setVisibility(View.VISIBLE);

        } else {

            swActivityLevel.setBackground(context.getResources().getDrawable(R.drawable.outlinedboxlw));
            rbActivityLevel.setVisibility(View.GONE);

        }
    }

    public int getActivityLevel(){
        return rbActivityLevel.getProgress();
    }
}
