package com.example.localdogs;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.localdogs.data.User;
import com.example.localdogs.data.awsinterface.Authentication;
import com.example.localdogs.data.awsinterface.api.UserRequests;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.*;

public class RegistrationPage extends AppCompatActivity {
    public final int LAUNCH_TERMS_ACTIVITY = 1;

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

        if (checkBoxes()) {
            //Intent returnIntent = new Intent();
            //setResult(Activity.RESULT_OK, returnIntent);
            // changes here
            User stuff = new User(firstNameField.getText().toString(), lastNameField.getText().toString(), emailField.getText().toString(), dobField.getText().toString());
            //UserRequests stuff2 = new UserRequests(getApplicationContext());
            Authentication.getInstance(getApplicationContext()).registerUser(stuff.getEmail(), passwordField.getText().toString(), stuff, (success) -> {
                        //go to cardstack; successful
                        Log.i("Success Registration", "Woohoo!");
                        Intent intent = new Intent(t.getContext(), Cardstack.class);
                        startActivity(intent);
                        finish();
            },
            (error) -> {
                //duplicate email
                Log.e("Auth", error.getMessage());
                //Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
            });

            //finish();
        }
        else
            Toast.makeText(getApplicationContext(), "Error with registration", Toast.LENGTH_SHORT).show();
    }
}