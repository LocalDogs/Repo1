package com.example.localdogs.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.localdogs.Cardstack;
import com.example.localdogs.R;
import com.example.localdogs.RegistrationPage;
import com.example.localdogs.TermsOfUse;
import com.example.localdogs.data.User;
import com.example.localdogs.data.UserRequests;
import com.example.localdogs.ui.login.LoginViewModel;
import com.example.localdogs.ui.login.LoginViewModelFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    public final int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = (Button)findViewById(R.id.register);
        //final RegistrationPage rp = (RegistrationPage)findViewById(R.id.registion)

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                //loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    //showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    //updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            //THIS IS THE METHOD THAT HANDLES THE ENTER KEY ON KEYBOARD BEHAVIOR
            //ONCE THE USER PRESSES ENTER AFTER ENTERING LOGIN INFO, THIS WILL VALIDATE IT WITH THE CURRENT DATABASE ENTRIES
            @Override
            public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    UserRequests user = new UserRequests(getApplicationContext());
                    user.retrieveUserProfile(usernameEditText.getText().toString(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("LoginTest", "Succeeded");

                            try {
                                //200 = successful connection
                                if (response.getInt("statusCode") == 200 && response.getBoolean("found")) {
                                    User bob = User.toUser(response);
                                    Log.d("Login", bob.toString());
                                    Intent intent = new Intent(v.getContext(), Cardstack.class);
                                    startActivity(intent);
                                    //If there is a valid login after pressing keyboard enter key. End activity
                                    finish();
                                }
                                else if (response.getInt("statusCode") == 500)
                                    System.out.println("Error on statusCode, value is 500");
                                else {
                                    Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                                    System.out.println("No email found!");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("LoginTest", "Failed");
                        }
                    });

                }
                return false;
            }
        });

        //THIS IS THE WHERE THE LOGIN BUTTON IS HANDLED
        //ONCE THE USER CLICKS LOGIN AFTER ENTERING LOGIN INFO, THIS WILL VALIDATE IT WITH THE CURRENT DATABASE ENTRIES
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadingProgressBar.setVisibility(View.VISIBLE);
                /*loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());*/
                //Toast.makeText(getApplicationContext(), "Login!", Toast.LENGTH_SHORT).show();

                final View t = v;

                UserRequests user = new UserRequests(getApplicationContext());
                user.retrieveUserProfile(usernameEditText.getText().toString(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LoginTest", "Succeeded");

                        try {
                            //200 = successful connection
                            if (response.getInt("statusCode") == 200 && response.getBoolean("found")) {
                                User bob = User.toUser(response);
                                Log.d("Login", bob.toString());
                                Intent intent = new Intent(t.getContext(), Cardstack.class);
                                startActivity(intent);
                                //If there is a valid login after pressing login button. End activity
                                finish();
                            }
                            else if (response.getInt("statusCode") == 500)
                                System.out.println("Error on statusCode, value is 500");
                            else {
                                Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                                System.out.println("No email found!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LoginTest", "Failed");
                    }
                });
                //System.out.println(usernameEditText.getText().toString());
            }
        });
        //click the register button
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Log.i("LoginActivity", "Register button pressed");
                //Toast.makeText(getApplicationContext(), "Register!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), TermsOfUse.class);
                startActivity(intent);
                //startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY); //??????
            }
        });



            Log.i("LoginActivity","Listening for buttons!");
        /*registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("LoginActivity","Register button clicked");
                Intent myIntent = new Intent(view.getContext(), RegistrationPage.class);
                startActivity(myIntent);
            }
        });*/
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }//onActivityResult


    /*private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }*/

    /*public void registerClick(View view) {
        Log.i("LoginActivity", "Register button clicked");
        Toast.makeText(getApplicationContext(), "Register!", Toast.LENGTH_SHORT).show();
        System.out.println("[print] register button clicked");
        if(view.getId() == R.id.register) {
            Intent myIntent = new Intent(this, RegistrationPage.class);
            startActivity(myIntent);
        }
    }*/
}