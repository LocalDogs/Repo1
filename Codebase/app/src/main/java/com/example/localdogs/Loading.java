package com.example.localdogs;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class Loading extends AppCompatActivity {
    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading");
        pDialog.setTitle("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();
        return pDialog;
    }

    public static void hideProgressDialog(ProgressDialog pDialog) {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
