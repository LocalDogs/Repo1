package com.example.localdogs.data.awsinterface;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.storage.StorageException;
import com.amplifyframework.storage.result.StorageUploadFileResult;
import com.example.localdogs.data.Dog;
import com.example.localdogs.data.User;

import java.io.File;

public abstract class Image {

    public static void uploadImage(User user, Dog dog, File image, Consumer<StorageUploadFileResult> onSuccess, Consumer<StorageException> onFailure){
        Amplify.Storage.uploadFile(user.getId() + "/" + dog.getName() + "/" + image.getName(), image, success -> {
            Log.i("uploadImage", success.toString());
            onSuccess.accept(success);
        }, failure -> {
            Log.e("uploadImage", failure.getMessage(), failure.getCause());
            onFailure.accept(failure);
        });
    }
}
