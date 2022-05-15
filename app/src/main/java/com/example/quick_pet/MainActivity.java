package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private TextView registerLink, forgotLink, emailReq, passwordReq;
    private EditText mEmail, mPassword;
    Button next_btn;
    FirebaseAuth fAuth;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--connecting variables to its source
        fAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.email_input);
        mPassword = findViewById(R.id.password_input);
        emailReq = findViewById(R.id.email_req);
        passwordReq = findViewById(R.id.pass_req);

        //--next button
        next_btn = findViewById(R.id.next_btn);


        forgotLink = findViewById(R.id.forgot_link);
        forgotLink.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Forgot_Password.class));
            finish();
        });

        registerLink = findViewById(R.id.register_link);
        registerLink.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Register.class));
            finish();
        });

        next_btn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(MainActivity.this, "Email is Required", Toast.LENGTH_LONG).show();
                emailReq.setText(R.string.this_field_is_required);
                mEmail.requestFocus();

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(MainActivity.this, "Valid email is Required", Toast.LENGTH_LONG).show();
                mEmail.setError("Valid email is required");
                mEmail.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Password is Required", Toast.LENGTH_LONG).show();
                passwordReq.setText(R.string.this_field_is_required);
                mPassword.requestFocus();

            } else {
                LoginUser(email, password);
                //authenticate the user

            }
        });
    }

    private void LoginUser(String email, String password) {
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                FirebaseUser user = fAuth.getCurrentUser();
                if (user.isEmailVerified()) {
//                    Toast.makeText(MainActivity.this, "Logged in SuccessFully", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(MainActivity.this, List__Pet.class));
                } else {
                    startActivity(new Intent(MainActivity.this, List__Pet.class));
//                    user.sendEmailVerification();
//                    fAuth.signOut();
//                    showAlertDialog();mary
                }

            } else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthInvalidUserException e) {
                    mPassword.setError("User does not exist");
                    mPassword.requestFocus();

                } catch (FirebaseAuthInvalidCredentialsException e) {
                    mPassword.setError("Invalid password try again");
                    mPassword.requestFocus();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(MainActivity.this, "Error !" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You cannot login");

        builder.setPositiveButton("Continue", (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fAuth.getCurrentUser() != null) {
            Toast.makeText(MainActivity.this, "You are already login", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, List__Pet.class));
            finish();
        } else {
            Toast.makeText(MainActivity.this, "You can login now", Toast.LENGTH_SHORT).show();
        }
    }
}