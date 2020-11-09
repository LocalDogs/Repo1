package com.example.localdogs;
import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.StoragePlugin;
import com.example.localdogs.data.awsinterface.AmplifyHub;
import com.example.localdogs.data.awsinterface.Authentication;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.*;

public class StorageTester {
    @Test
    public void uploadImage() throws InterruptedException {
        final Object syncObject = new Object();

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AmplifyHub.launchAmplify(appContext);
        Authentication.getInstance(appContext).signInUser("rememberthis@remember.com", "remember!@#$1234", success -> {
            File exampleFile = new File(appContext.getFilesDir(), "ExampleKey");

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
                writer.append("Example file contents");
                writer.close();
            } catch (Exception exception) {
                Log.e("MyAmplifyApp", "Upload failed", exception);
            }

            Amplify.Storage.uploadFile(
                    "ExampleKey",
                    exampleFile,
                    result -> {

                        Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey());
                        synchronized (syncObject) {

                            syncObject.notify();

                        }
                    },
                    storageFailure -> {
                        Log.e("MyAmplifyApp", "Upload failed", storageFailure);
                        synchronized (syncObject) {

                            syncObject.notify();

                        }
                    }
            );
        }, error -> {
            Log.e("Upload", error.getMessage());
            synchronized (syncObject) {

                syncObject.notify();

            }
        });
        synchronized (syncObject) {

            syncObject.wait();

        }
    }
}
