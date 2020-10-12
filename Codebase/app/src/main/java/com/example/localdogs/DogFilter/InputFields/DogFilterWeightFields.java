package com.example.localdogs.DogFilter.InputFields;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.localdogs.R;

public class DogFilterWeightFields implements CompoundButton.OnCheckedChangeListener, TextWatcher {
    private EditText etMin;
    private EditText etMax;
    private Context context;
    private Switch swWeight;
    private boolean hasInputMin;
    private boolean hasInputMax;
    private TextWatcher textWatcher;

    public DogFilterWeightFields(Context context, EditText etMin, EditText etMax, Switch swWeight){
        this.etMin = etMin;
        this.etMax = etMax;
        this.context = context;
        this.swWeight = swWeight;
        this.hasInputMin = false;
        this.hasInputMax = false;
        this.etMin.addTextChangedListener(this);
        this.etMax.addTextChangedListener(this);
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

    @Override
    public void afterTextChanged(Editable editable) {
        if(etMin.getText().toString().trim().equals("")){
            Log.d("etMinAfterTextChange", "should be false");
            hasInputMin = false;
        }
        else{
            Log.d("etMinAfterTextChange", etMin.getText().toString());
            Log.d("etMinAfterTextChange", "should be true");
            hasInputMin = true;
        }
        if(etMax.getText().toString().trim().equals("")) hasInputMax = false;
        else hasInputMax = true;
    }

    public int getMinWeight(){
        if(hasInputMin) return Integer.parseInt(etMin.getText().toString());
        else return -1;
    }
    
    public int getMaxWeight(){
        if(hasInputMax) return Integer.parseInt(etMax.getText().toString());
        else return -1;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // unused
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // unused
    }
}
