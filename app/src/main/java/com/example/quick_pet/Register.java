package com.example.quick_pet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Register extends AppCompatActivity {

    //-----variables
    EditText mEmail, mPassword, mConfirm;
    TextView mReg1, mReg2, mReg3;
    Button reg_btn;
    TextView mLogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //----connect variables to id on xml file
        mEmail = findViewById(R.id.email_input);
        mPassword = findViewById(R.id.password_input);
        mConfirm = findViewById(R.id.confirm_passw);
        reg_btn = findViewById(R.id.next_btn);
        mLogin = findViewById(R.id.singIn_link);

        mReg1 = findViewById(R.id.req1);
        mReg2 = findViewById(R.id.req2);
        mReg3 = findViewById(R.id.req3);

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() !=  null){
            startActivity(new Intent(Register.this, MainActivity.class));
            finish();
        }
        // click listener for the next button

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPass = mConfirm.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Email is Required", Toast.LENGTH_SHORT).show();
                    mReg1.setText("This field is required");
                    //mEmail.setBackground(getResources().getDrawable(R.drawable.req));
                }
                if(TextUtils.isEmpty(password)) {
                    mReg2.setText("This field is required");
                    //mPassword.setBackground(getResources().getDrawable(R.drawable.req));
                }
                if(password.length() < 6){
                    mReg2.setText("Password must contain 6 characters");
                    //mPassword.setBackground(getResources().getDrawable(R.drawable.req));
                }
                if(TextUtils.isEmpty(confirmPass)) {
                    mReg3.setText("This field is required");
                    //mConfirm.setBackground(getResources().getDrawable(R.drawable.req));
                }
                if(TextUtils.equals(password, confirmPass)){
                    mReg3.setText("Password does not match");
                    //mConfirm.setBackground(getResources().getDrawable(R.drawable.req));
                }
                //register the user
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                       Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(Register.this, MainActivity.class));
                    } else {
                        Toast.makeText(Register.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}