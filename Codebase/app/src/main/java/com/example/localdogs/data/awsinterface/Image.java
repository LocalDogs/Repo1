package com.example.localdogs.data.awsinterface;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.storage.StorageAccessLevel;
import com.amplifyframework.storage.StorageException;
import com.amplifyframework.storage.options.StorageUploadFileOptions;
import com.amplifyframework.storage.result.StorageUploadFileResult;
import com.example.localdogs.data.Dog;
import com.example.localdogs.data.User;

import java.io.File;

public abstract class Image {

    public static void uploadImage(String dogName, File image, Consumer<StorageUploadFileResult> onSuccess, Consumer<StorageException> onFailure){
        User user = null;
        try {
            user = Authentication.getInstance().getCurrentSessionUser();
            Amplify.Storage.uploadFile(user.getDogImageStorageKey(dogName), image, publicFileOptions(), success -> {

                Log.i("uploadImage", success.toString());
                onSuccess.accept(success);

            }, failure -> {

                Log.e("uploadImage", failure.getMessage(), failure.getCause());
                onFailure.accept(failure);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static StorageUploadFileOptions publicFileOptions(){
        StorageUploadFileOptions options = StorageUploadFileOptions
                .builder()
                .accessLevel(StorageAccessLevel.PROTECTED)
                .build();
        return options;
    }
}
