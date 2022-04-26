package com.example.quick_pet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    private EditText editText;
    private Button btn_reset;

    FirebaseAuth auth;

    // quick_pet_app   = quick123   =  20/04/2004

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editText = (EditText) findViewById(R.id.email_forgot);
        btn_reset = (Button) findViewById(R.id.next_btn_forgot);

        btn_reset.setOnClickListener(view -> resetPassword());

    }

    private void resetPassword() {
        String email = editText.getText().toString().trim();

        if(email.isEmpty()){
            editText.setError("Email is required");
            editText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText.setError("Please insert a valid email");
            editText.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgot_Password.this, "Check your email inbox and follow the instructions", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Forgot_Password.this, "Try again!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}