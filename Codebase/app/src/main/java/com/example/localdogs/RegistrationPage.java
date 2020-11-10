package com.example.localdogs;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.localdogs.data.Dog;
import com.example.localdogs.data.User;
import com.example.localdogs.data.awsinterface.Authentication;
import com.example.localdogs.data.awsinterface.Image;
import com.example.localdogs.data.awsinterface.api.UserRequests;
import com.example.localdogs.ui.ThreadSafeToast;
import com.example.localdogs.ui.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class RegistrationPage extends AppCompatActivity {
    public final int LAUNCH_TERMS_ACTIVITY = 1;
    public static final int MULTIPLE_PERMISSIONS = 10;
    private ImageView imageView;
    private Bitmap bitmapImage;
    boolean flag = false;

    String[] permissions = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    EditText firstNameField;
    EditText lastNameField;
    EditText dobField;
    EditText passwordField;
    EditText password2Field;
    EditText emailField;

    EditText nameDogField;
    EditText breedDogField;
    EditText dobDogField;
    EditText weightDogField;
    CheckBox purebredBox;
    CheckBox vaccinatedBox;
    RatingBar energyBar;
    TextWatcher textWatcher;

    public void initFields() {
        firstNameField = findViewById(R.id.firstNameEditText);
        firstNameField.addTextChangedListener(textWatcher);
        lastNameField = findViewById(R.id.lastNameEditText);
        lastNameField.addTextChangedListener(textWatcher);
        dobField = findViewById(R.id.dobEditText);
        dobField.addTextChangedListener(textWatcher);
        passwordField = findViewById(R.id.passwordEditText);
        passwordField.addTextChangedListener(textWatcher);
        password2Field = findViewById(R.id.repeatPasswordEditText);
        password2Field.addTextChangedListener(textWatcher);
        emailField = findViewById(R.id.emailEditText);
        emailField.addTextChangedListener(textWatcher);

        nameDogField = findViewById(R.id.dogNameEditText);
        nameDogField.addTextChangedListener(textWatcher);
        breedDogField = findViewById(R.id.breedEditText);
        breedDogField.addTextChangedListener(textWatcher);
        dobDogField = findViewById(R.id.dogDobEditText);
        dobDogField.addTextChangedListener(textWatcher);
        weightDogField = findViewById(R.id.weightDogEditText);
        weightDogField.addTextChangedListener(textWatcher);

        purebredBox = findViewById(R.id.purebredCheckbox);
        vaccinatedBox = findViewById(R.id.vaccinatedCheckBox);
        energyBar = findViewById(R.id.energyRatingBar);
    }

    Boolean validatePasswords() {
        //check to make sure passwords match
        if (!(passwordField.getText().toString().equals(password2Field.getText().toString()))) {
            password2Field.setError("Passwords do not match");
            return false;
        }
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-zA-Z])"
                + "(?=.*[@#$%^&+=!])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (!p.matcher(passwordField.getText().toString()).matches()) {
            passwordField.setError("Password is too weak");
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        //*********begin picture stuff********************************

        //for getting user permission
        if(checkPermissions()) {
            //permission granted
        }

        imageView = (ImageView) findViewById(R.id.my_avatar);
        final Button uploadDogPictureButton = findViewById(R.id.uploadPictureButton);
        uploadDogPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(RegistrationPage.this);

            }
        });

        //***********end picture stuff****************



        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if ((passwordField.getText().toString() != "")
                        && (password2Field.getText().toString() != "")) {
                    validatePasswords();
                }*/
                checkBoxes();
            }
        };
        initFields();
        Log.i("RegistrationPage", "Finished initializing fields");

        //****************LINK Registration -> Terms of Use Page***********************
        final Button backButton = findViewById(R.id.goBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TermsOfUse.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //**************************BEGIN CHECK PERMISSIONS*******************************

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(RegistrationPage.this, p); //********
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;
                            Intent intent = new Intent(getApplicationContext(), TermsOfUse.class);
                            startActivity(intent);
                            finish();
                            ThreadSafeToast.makeText(getApplicationContext(), "Permission Denied. Reset app preferences in settings.", Toast.LENGTH_LONG).show();

                        }
                    }
                    //Show Permission Denied

                }
                return;
            }
        }
    }

    //************PICTURE STUFF*********************

    private void selectImage(Context context) {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    /*
     *TODO: Find a way to make Choosing a Photo From Gallery display properly on a physical Android device
     *
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                //Take Picture
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        bitmapImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(bitmapImage);
                        //flag is set to true, we have gotten the image via taking a picture, so now the user can complete registration once the other fields are complete
                        flag = true;
                    }
                    break;
                    //Choose From Gallery
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                                bitmapImage = drawable.getBitmap();
                                cursor.close();
                                //flag is set to true, we have gotten the image via gallery, so now the user can complete registration once the other fields are complete
                                flag = true;
                            }
                        }

                    }
                    break;
            }
        }
    }


    //************END PICTURE STUFF


    //***************************************************

    private boolean checkBoxes() {
        boolean retval = true;
        //all of these are alphabetical, 2 to 24 characters
        String regex = "(?=.*[a-zA-Z\\s])"
                + ".{2,24}$";
        Pattern p = Pattern.compile(regex);
        if (!p.matcher(firstNameField.getText().toString()).matches()) {
            if (!firstNameField.getText().toString().equals(""))
                firstNameField.setError("Invalid name");
            retval = false;
        }
        if (!p.matcher(lastNameField.getText().toString()).matches()) {
            if (!lastNameField.getText().toString().equals(""))
                lastNameField.setError("Invalid name");
            retval = false;
        }
        if (!p.matcher(nameDogField.getText().toString()).matches()) {
            if (!nameDogField.getText().toString().equals(""))
                nameDogField.setError("Invalid name");
            retval = false;
        }
        if (!p.matcher(breedDogField.getText().toString()).matches()) {
            if (!breedDogField.getText().toString().equals(""))
                breedDogField.setError("Invalid breed");
            retval = false;
        }
        if (!validatePasswords())
            retval = false;
        regex = "(?=.*[0-9])"
                + "(?=\\S+$).{1,3}$"; //a number between 1 and 3 characters
        p = Pattern.compile(regex);
        if (!p.matcher(weightDogField.getText().toString()).matches()) {
            if (!weightDogField.getText().toString().equals(""))
                weightDogField.setError("Please give weight in pounds, rounded to the nearest pound.");
            retval = false;
        }
        regex = "(?=.*[0-9a-zA-Z])"
                + "(?=.*[@])"
                + "(?=.*[.])"
                + "(?=\\S+$).{7,50}$";
        p = Pattern.compile(regex);
        if (!p.matcher(emailField.getText().toString()).matches()) {
            if (!emailField.getText().toString().equals(""))
                emailField.setError("Invalid email");
            retval = false;
        }

        regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        p = Pattern.compile(regex);
        if (!p.matcher(dobField.getText().toString()).matches()) {
            if (!dobField.getText().toString().equals(""))
                dobField.setError("Invalid date of birth");
            retval = false;
        }
        if (!p.matcher(dobDogField.getText().toString()).matches()) {
            if (!dobDogField.getText().toString().equals(""))
                dobDogField.setError("Invalid date of birth");
            retval = false;
        }
        //purebredBox
        //vaccinatedBox
        //energyBar
        return retval;
    }

    public void register(View view) {

        Button regButton = findViewById(R.id.registerButton);
        final View t = view;

        Loading l = new Loading();
        ProgressDialog p = l.showProgressDialog(RegistrationPage.this);
        /*
        ProgressDialog nDialog;
        nDialog = new ProgressDialog(RegistrationPage.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Please Wait");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();*/

        if (checkBoxes() && flag == true) {
            //Intent returnIntent = new Intent();
            //setResult(Activity.RESULT_OK, returnIntent);
            // changes here
            ArrayList<String> firstDogBreeds = new ArrayList<String>();
            firstDogBreeds.add(breedDogField.getText().toString());
            dob firstDogDob = new dob(dobDogField.getText().toString());
            Dog firstDog = new Dog(
                    emailField.getText().toString(),
                    nameDogField.getText().toString(),
                    firstDogBreeds,
                    firstDogDob,
                    Integer.parseInt(weightDogField.getText().toString()),
                    (int) energyBar.getRating(),
                    null
            );
            User stuff = new User(firstNameField.getText().toString(), lastNameField.getText().toString(), emailField.getText().toString(), dobField.getText().toString(), firstDog);
            Authentication.getInstance(getApplicationContext()).registerUser(stuff.getEmail(), passwordField.getText().toString(), stuff, (success) -> {
                Log.i("RegistrationPage", success.getMessage());
                try {
                    Image.uploadImage(RegistrationPage.this, nameDogField.getText().toString(), bitmapImage, (success2) ->{
                        //go to cardstack; successful
                                Log.i("Success Registration", "Woohoo!");
                                Intent intent = new Intent(t.getContext(), Cardstack.class);
                                //nDialog.dismiss();
                                Loading.hideProgressDialog(p);
                                startActivity(intent);
                                finish();
                                },
                            (error) -> {

                    });
                } catch (Exception e){
                    Log.i("Uh Oh", "I/O issues occurred :(");
                }
            },
            (error) -> {
                //nDialog.dismiss();
                Loading.hideProgressDialog(p);
                //duplicate email
                Log.e("Auth", error.getMessage());
                ThreadSafeToast.makeText(getApplicationContext(), "Email Already Exists!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
            });

            //finish();
        }
        else {
            //nDialog.dismiss();
            Loading.hideProgressDialog(p);
            Toast.makeText(getApplicationContext(), "Error with registration", Toast.LENGTH_SHORT).show();
        }
    }
}