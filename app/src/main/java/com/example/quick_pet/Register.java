package com.example.quick_pet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    //-----variables
    private EditText mEmail, mPassword, mConfirm;
    private TextView mLogin;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //----connect variables to this page
        mEmail = findViewById(R.id.email_input);
        mPassword = findViewById(R.id.password_input);
        mConfirm = findViewById(R.id.confirm_passw);

        mLogin = findViewById(R.id.singIn_link);
        mLogin.setOnClickListener(view -> startActivity(new Intent(Register.this, MainActivity.class)));

        // click listener for the next button
        Button reg_btn = findViewById(R.id.next_btn);
        reg_btn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String confirmPass = mConfirm.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Register.this, "Email is Required", Toast.LENGTH_SHORT).show();
                mEmail.setError("Please insert email");
                mEmail.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(Register.this, "Correct email is required", Toast.LENGTH_SHORT).show();
                mEmail.setError("Please insert a correct email");
                mEmail.requestFocus();

            } else if (TextUtils.isEmpty(password)) {
                mPassword.setError("Please insert password");
                mPassword.requestFocus();

            } else if (password.length() < 6) {
                mPassword.setError("This password is too short");
                mPassword.requestFocus();

            } else if (TextUtils.isEmpty(confirmPass)) {
                mConfirm.setError("Please confirm password");
                mConfirm.requestFocus();

            } else if (!password.equals(confirmPass)) {
                mConfirm.setError("Repite the same password");
                mConfirm.requestFocus();
                mPassword.clearComposingText();
                mConfirm.clearComposingText();

            } else {
                registerUser(email, password);
            }
        });
    }

    private void registerUser(String email, String password) {
//register the user
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

//                FirebaseUser firebaseUser = fAuth.getCurrentUser();
//                //to register the user in realtime database
//                C__User writeUserDetails = new C__User(email);
//
//                //Extracting user reference from database
//                DatabaseReference referenceProfile = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference("User");
//
//                referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(task1 -> {
//                    if (task1.isSuccessful()) {
////                        firebaseUser.sendEmailVerification();

                        Toast.makeText(Register.this, "User Created.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Register.this, MainActivity.class);
                        //To prevent the user to returning back to register activity with back button after registration
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // to close activity

//                    } else {
//                        Toast.makeText(Register.this, "User register failed. Please try again", Toast.LENGTH_LONG).show();
//                    }
//                });
            } else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthWeakPasswordException e) {
                    mPassword.setError("Your password is too weak. Please use a mix of alphabets, numbers and symbols");
                    mPassword.requestFocus();
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    mPassword.setError("Your email is invalid or already in use. Please re-enter");
                    mPassword.requestFocus();
                } catch (FirebaseAuthUserCollisionException e) {
                    mPassword.setError("User is already registered with this email. Use another email");
                    mPassword.requestFocus();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(Register.this, "Error ! " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
