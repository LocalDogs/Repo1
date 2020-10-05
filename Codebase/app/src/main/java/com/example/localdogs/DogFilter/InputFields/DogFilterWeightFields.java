package com.example.localdogs.DogFilter.InputFields;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.localdogs.R;

public class DogFilterWeightFields implements CompoundButton.OnCheckedChangeListener {
    private EditText etMin;
    private EditText etMax;
    private Context context;
    private Switch swWeight;

    public DogFilterWeightFields(Context context, EditText etMin, EditText etMax, Switch swWeight){
        this.etMin = etMin;
        this.etMax = etMax;
        this.context = context;
        this.swWeight = swWeight;
        swWeight.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (b) {

            compoundButton.setBackground(context.getResources().getDrawable(R.drawable.outlinedboxlw));
            etMin.setVisibility(View.VISIBLE);
            etMax.setVisibility(View.VISIBLE);

        } else {

            compoundButton.setBackground(context.getResources().getDrawable(R.drawable.outlinedboxcg));
            etMin.setVisibility(View.GONE);
            etMax.setVisibility(View.GONE);

        }
    }
}
