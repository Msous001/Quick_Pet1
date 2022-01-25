package com.example.quick_pet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity {

    TextView registerLink, forgotLink, emailReq, passwordReq;
    EditText mEmail, mPassword;
    Button next_btn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.email_input);
        mPassword = findViewById(R.id.password_input);
        emailReq = findViewById(R.id.email_req);
        passwordReq = findViewById(R.id.pass_req);

        ////////////
        mEmail.setText("test1@email.com");
        mPassword.setText("Max123");



        ///////////

        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                Toast.makeText(MainActivity.this, "Email is Required", Toast.LENGTH_SHORT).show();
                emailReq.setText("This field is required");
                //mEmail.setBackground(getResources().getDrawable(R.drawable.req));
            }
            if(TextUtils.isEmpty(password)) {
                passwordReq.setText("This field is required");
                //mPassword.setBackground(getResources().getDrawable(R.drawable.req));
            }
            if(password.length() < 6){
                passwordReq.setText("Password must contain 6 characters");
                //mPassword.setBackground(getResources().getDrawable(R.drawable.req));
            }
            //authenticate the user
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Logged in SuccessFully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, FirstActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });



        forgotLink = findViewById(R.id.forgot_link);
        forgotLink.setOnClickListener(v -> {

        });

        registerLink = findViewById(R.id.register_link);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
    }
}