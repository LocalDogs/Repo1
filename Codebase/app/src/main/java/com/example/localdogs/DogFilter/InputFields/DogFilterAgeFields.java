package com.example.localdogs.DogFilter.InputFields;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import com.example.localdogs.R;

public class DogFilterAgeFields implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener{

    private Context context;
    private TextView tvMin;
    private TextView tvMax;
    private SeekBar sbMin;
    private SeekBar sbMax;
    private Switch swAge;

    public DogFilterAgeFields
            (
                    Context context,
                    TextView tvMin,
                    TextView tvMax,
                    SeekBar sbMin,
                    SeekBar sbMax,
                    Switch swAge
            )
    {
        this.context = context;
        this.tvMin = tvMin;
        this.tvMax = tvMax;
        this.sbMin = sbMin;
        sbMin.setOnSeekBarChangeListener(this);
        sbMax.setOnSeekBarChangeListener(this);
        this.sbMax = sbMax;
        this.swAge = swAge;
        swAge.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {

                    compoundButton.setBackground(context.getResources().getDrawable(R.drawable.outlinedboxlw));
                    sbMin.setVisibility(View.VISIBLE);
                    sbMax.setVisibility(View.VISIBLE);
                    tvMin.setVisibility(View.VISIBLE);
                    tvMax.setVisibility(View.VISIBLE);

                } else {

                    compoundButton.setBackground(context.getResources().getDrawable(R.drawable.outlinedboxcg));
                    sbMin.setVisibility(View.GONE);
                    sbMax.setVisibility(View.GONE);
                    tvMin.setVisibility(View.GONE);
                    tvMax.setVisibility(View.GONE);

                }
    }
    public int getMinAge(){
        return sbMin.getProgress();
    }
    public int getMaxAge(){
        return sbMax.getProgress();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(getMaxAge() < getMinAge()) tvMax.setError("Maximum age must be greater than minimum age");
        else tvMax.setError(null);
        if(seekBar.equals(sbMin)) tvMin.setText("Minimum Age: " + seekBar.getProgress());
        else tvMax.setText("Maximum Age: " + seekBar.getProgress());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //unused
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //unused
    }
}
