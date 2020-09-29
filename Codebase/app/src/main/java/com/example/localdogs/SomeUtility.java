package com.example.localdogs;

import android.content.Context;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

public class SomeUtility<T> extends CompoundButton {

    public SomeUtility(Context context, T view) {
        super(context);
    }

    @Override
    public void setOnCheckedChangeListener(@Nullable OnCheckedChangeListener listener) {
        super.setOnCheckedChangeListener(listener);
    }
}
