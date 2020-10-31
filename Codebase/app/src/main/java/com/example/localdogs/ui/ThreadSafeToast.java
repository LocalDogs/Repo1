package com.example.localdogs.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ThreadSafeToast{

    public static Toast makeText(Context context, String message, int duration){
        Looper.prepare();
        return Toast.makeText(context, message, duration);
    }


}
