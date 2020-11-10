package com.example.localdogs.data.awsinterface;

import android.content.Context;
import android.graphics.Bitmap;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class Image {

    public static void uploadImage(Context context, String dogName, Bitmap image, Consumer<StorageUploadFileResult> onSuccess, Consumer<StorageException> onFailure) throws IOException {
        upload(context, dogName, image, onSuccess, onFailure);
    }

    private static File convertImageToFile(String dogName, Context context, Bitmap image) throws IOException {
        File imageFile = new File(context.getApplicationContext().getCacheDir(), dogName);
        FileOutputStream outputStream = new FileOutputStream(imageFile);
        image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        outputStream.close();
        return imageFile;
    }

    private static void upload(Context context, String dogName, Bitmap image, Consumer<StorageUploadFileResult> onSuccess, Consumer<StorageException> onFailure) throws IOException {
        File imageFile = convertImageToFile(dogName, context.getApplicationContext(), image);
        User user = null;
        try {
            user = Authentication.getInstance().getCurrentSessionUser();
            Amplify.Storage.uploadFile(user.getDogs().get(dogName).getImgUrl(), imageFile, publicFileOptions(), success -> {

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
                .accessLevel(StorageAccessLevel.PUBLIC)
                .build();
        return options;
    }
}
